<?php
/*
 * nv_protocol.php
 *
 * Eric Viara copyright(c) Institut Curie
 * August-September 2014
 *
 */

function logfile($id) {
  return "/tmp/nv_" . $id . ".log";
}

function logmsg($id, $msg) {
  $file = logfile($id);
  //$fdlck = lock($id);
  $fd = fopen($file, "a") or die("cannot append to file " . $file);
  fwrite($fd, $msg);
  //unlock($fdlck);
  fclose($fd);
}

function get_post_var($param) {
  if (isset($_POST[$param])) {
    return $_POST[$param];
  }
  return "";
}

function get_url_var($param) {
  if (isset($_GET[$param])) {
    return $_GET[$param];
  }
  if (isset($_POST[$param])) {
    return $_POST[$param];
  }
  return "";
}

function get($param, $defval) {
  $value = get_url_var($param);
  if (!$value) {
    if ($defval) {
      $value = $defval;
    } else {
      echo "$param parameter is missing<br>";
      exit(1);
    }
  }	  
  return $value;
}

function waitandlock($id, $file) {
  for (;;) {
    checkfile($file);
    $fdlck = lock($id);
    $contents = file_get_contents($file);
    if (!$contents) {
      return $fdlck;
    }
    unlock($fdlck);
    usleep(10000);
  }
}

function waitfordata($id, $file) {
  for (;;) {
    checkfile($file);
    $fdlck = lock($id);
    $contents = file_get_contents($file);
    if ($contents) {
      file_put_contents($file, "");
      unlock($fdlck);
      return $contents;
    }
    unlock($fdlck);
    usleep(10000);
  }
  return '';
}

function creatfile($file) {
  $fd = fopen($file, "w") or die("cannot create file " . $file);
  ftruncate($fd, 0);
  fclose($fd);
}

function delfile($file) {
  unlink($file) or die("cannot delete file " . $file);
}

function mkfile($id, $ext) {
  global $file_prefix;
  return $file_prefix . $id . $ext;
}

function lockfile($id) {
  return mkfile($id, ".lck");
}

function cmdfile($id) {
  return mkfile($id, ".cmd");
}

function rspfile($id) {
  return mkfile($id, ".rsp");
}

function datafile($id) {
  return mkfile($id, ".dat");
}

function packnumfile($id) {
  return mkfile($id, ".pkn");
}

function checkfile($file) {
  $fd = fopen($file, "r") or die("cannot open file " . $file . " for reading");
  fclose($fd);
}

function lock($id) {
  $file = lockfile($id);
  $fdlck = fopen($file, "r") or die("cannot open file " . $file . " for reading");
  $wouldblock = 1;
  if (!flock($fdlck, LOCK_EX, $wouldblock)) {
    die("cannot lock file " . $file);
  }
  return $fdlck;
}

function unlock($fdlck) {
  flock($fdlck, LOCK_UN);
  fclose($fdlck);
}

$mode = get("mode", "none");
$perform = get("perform", "");
$id = get("id", "");
$msg_id = get("msg_id", "UNKNOWN");
$file_prefix = "/tmp/nv_";

if ($mode == "none") {
  if ($perform == "init") {

    creatfile(logfile($id));

    creatfile(lockfile($id));

    creatfile(cmdfile($id));

    creatfile(rspfile($id));

    logmsg($id, "init $id\n");

    return;
  }
  if ($perform == "reset") {

    delfile(logfile($id));

    delfile(lockfile($id));

    delfile(cmdfile($id));

    delfile(rspfile($id));

    delfile(datafile($id));

    delfile(packnumfile($id));

    return;
  }
} else if ($mode == "cli2srv") {
  if ($perform == "filling") {
    logmsg($id, "cli2srv: $perform $id $msg_id\n");

    $packnum = get_url_var("packnum");
    $packnumfile = packnumfile($id);
    $datafile = datafile($id);
    for (;;) {
      checkfile($packnumfile);
      $fdlck = lock($id);
      $packstr = file_get_contents($packnumfile);
      if (!$packstr) {
	$packstr = "0";
      }
      if (intval($packnum) == intval($packstr)+1) {
	$fd = fopen($datafile, "a") or die("cannot append to file " . $file);
	fwrite($fd, get_post_var("data"));
	fclose($fd);
	file_put_contents($packnumfile, $packnum);
	unlock($fdlck);
	logmsg($id, "cli2srv: found packet $packnum\n");
	break;
      }
      unlock($fdlck);
      usleep(10000);
    }
    return;
  } else if ($perform == "send" || $perform == "send_and_rcv") {
    $data = get_post_var("data");
    logmsg($id, "cli2srv: $perform $id $msg_id [$data]\n");

    if ($data) {
      $file = cmdfile($id);
      $fdlck = waitandlock($id, $file);

      $packcount = get_url_var("packcount");
      if ($packcount) {
	//	logmsg($id, "cli2srv: multipart $packcount\n");
	$packnumfile = packnumfile($id);
	$datafile = datafile($id);
	creatfile($datafile);
	creatfile($packnumfile);
	for (;;) {
	  checkfile($packnumfile);
	  $packstr = file_get_contents($packnumfile);
	  if ($packstr && intval($packstr) == intval($packcount)) {
	    //	    logmsg($id, "cli2srv: multipart find packet $packstr");
	    $data = file_get_contents($datafile);
	    break;
	  }
	  unlock($fdlck);
	  usleep(10000);
	}

      }
      file_put_contents($file, $data);
      
      unlock($fdlck);
      
      if (true || $perform == "send_and_rcv") {
	$file = rspfile($id);
	$contents = waitfordata($id, $file);
	logmsg($id, "cli2srv: client HAS received response from server $id $msg_id [$contents]\n\n");
	print $contents;
      }
    } else {
      logmsg($id, "cli2srv: DIE no data\n");
    }
    return;
  }
  if ($perform == "rcv") {
    $file = cmdfile($id);
    $contents = waitfordata($id, $file);
    logmsg($id, "cli2srv: server has received command from client $id $msg_id [$contents]\n");
    print $contents;
    return;
  }
} else if ($mode == "srv2cli") {
  if ($perform == "rsp") {
    $data = get_post_var("data");
    logmsg($id, "srv2cli: send response to client $id $msg_id [$data]\n");
    if ($data) {
      $file = rspfile($id);
      $fdlck = waitandlock($id, $file);
      file_put_contents($file, $data);
      unlock($fdlck);
    } else {
      logmsg($id, "srv2cli: DIE not data\n");
    }
    return;
  }
  if ($perform == "rcv") {
    return;
  }
}

echo "invalid parameters<br/>";
?>
