<?php
/*
 * nv_protocol.php
 *
 * Eric Viara copyright(c) Institut Curie
 * 2014-08-27
 *
 */

function logfile($id) {
  return "/tmp/nv_" . $id . ".log";
}

function logmsg($id, $msg) {
  $file = logfile($id);
  $fd = fopen($file, "a") or die("cannot append to file " . $file);
  fwrite($fd, $msg);
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
  //echo "$param: " . $value . "<br/>";
  return $value;
}

function waitandlock($id, $file) {
  for (;;) {
    $fdlck = lock($id);
    $contents = file_get_contents($file);
    if (!$contents) {
      return $fdlck;
    }
    unlock($fdlck);
    usleep(10000);
  }
}

function waitfordata($id, $file, $block) {
  for (;;) {
    $fdlck = lock($id);
    $contents = file_get_contents($file);
    if ($contents || $block != "on") {
      /*      logmsg($id, "cli2srv: client HAS received response from server $id [$contents]\n");*/
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

function lock($id) {
  $file = lockfile($id);
  $fdlck = fopen($file, "r") or die("cannot open file " . $file . " for reading");
  $wouldblock = 1;
  flock($fdlck, LOCK_EX, $wouldblock);
  return $fdlck;
}

function unlock($fdlck) {
  flock($fdlck, LOCK_UN);
  fclose($fdlck);
}

$mode = get("mode", "none");
$perform = get("perform", "");
$id = get("id", "");
$block = get("block", "on");
$data = get_post_var("data");
logmsg($id, "DATA LEN " . strlen($data) . "\n");
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
    logmsg($id, "cli2srv: $perform $id\n");

    $packnum = get_url_var("packnum");
    $packnumfile = packnumfile($id);
    $datafile = datafile($id);
    for (;;) {
	$fdlck = lock($id);
	$packstr = file_get_contents($packnumfile);
	if (!$packstr) {
	  $packstr = "0";
	}
	if (intval($packnum) == intval($packstr)+1) {
	  logmsg($id, "cli2srv: found packet $packnum\n");
	  $fd = fopen($datafile, "a") or die("cannot append to file " . $file);
	  fwrite($fd, get_post_var("data"));
	  fclose($fd);
	  file_put_contents($packnumfile, $packnum);
	  unlock($fdlck);
	  break;
	}
	// check for packnum num
	unlock($fdlck);
	usleep(10000);
    }
    return;
  } else if ($perform == "send" || $perform == "send_and_rcv") {
    $data = get_post_var("data");
    logmsg($id, "cli2srv: $perform $id $data\n");

    if ($data) {
      $file = cmdfile($id);
      $fdlck = waitandlock($id, $file);
      /*
      for (;;) {
	$fdlck = lock($id);
	$contents = file_get_contents($file);
	if (!$contents) {
	  break;
	}
	unlock($fdlck);
	usleep(10000);
      }
      */

      $packcount = get_url_var("packcount");
      if ($packcount) {
	logmsg($id, "cli2srv: multipart $packcount\n");
	$packnumfile = packnumfile($id);
	$datafile = datafile($id);
	creatfile($datafile);
	creatfile($packnumfile);
	for (;;) {
	  $packstr = file_get_contents($packnumfile);
	  if ($packstr && intval($packstr) == intval($packcount)) {
	    logmsg($id, "cli2srv: multipart find packet $packstr");
	    $data = file_get_contents($datafile);
	    break;
	  }
	  unlock($fdlck);
	  usleep(10000);
	}
      } 

      file_put_contents($file, $data);
      
      unlock($fdlck);
      logmsg($id, "CLI has unlocked file\n");

      if ($perform == "send_and_rcv") {
	$file = rspfile($id);
	$contents = waitfordata($id, $file, $block);
	logmsg($id, "cli2srv: client HAS received response from server $id [$contents]\n");
	print $contents;
	/*
	for (;;) {
	  $fdlck = lock($id);
	  $file = rspfile($id);
	  $contents = file_get_contents($file);
	  if ($contents || $block != "on") {
	    logmsg($id, "cli2srv: client HAS received response from server $id [$contents]\n");
	    file_put_contents($file, "");
	    unlock($fdlck);
	    print $contents;
	    break;
	  }
	  unlock($fdlck);
	  usleep(10000);
	}
	*/
      }
    }
    return;
  }
  if ($perform == "rcv") {
    $file = cmdfile($id);
    logmsg($id, "SRV is waiting for cmd\n");
    $contents = waitfordata($id, $file, $block);
    logmsg($id, "cli2srv: server has received command from client $id $contents\n");
    print $contents;
    /*
    for (;;) {
      $fdlck = lock($id);
      $file = cmdfile($id);
      $contents = file_get_contents($file);
      if ($contents || $block != "on") {
	logmsg($id, "cli2srv: server has received command from client $id $contents\n");
	file_put_contents($file, "");
	unlock($fdlck);
	print $contents;
	break;
      }
      unlock($fdlck);
      usleep(100000);
    }
    */
    return;
  }
} else if ($mode == "srv2cli") {
  if ($perform == "rsp") {
    $data = get_post_var("data");
    logmsg($id, "srv2cli: send response to client $id $data\n");
    if ($data) {
      $file = rspfile($id);
      $fdlck = waitandlock($id, $file);
      /*
      for (;;) {
	$fdlck = lock($id);
	$contents = file_get_contents($file);
	if (!$contents) {
	  break;
	}
	unlock($fdlck);
	usleep(100000);
      }
      */
      file_put_contents($file, $data);
      unlock($fdlck);
    }
    return;
  }
  if ($perform == "rcv") {
    return;
  }
}

echo "invalid parameters<br/>";
?>
