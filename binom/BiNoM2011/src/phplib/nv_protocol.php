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

function lockfile($id) {
  global $file_prefix;
  return $file_prefix . $id . ".lck";
}

function cmdfile($id) {
  global $file_prefix;
  return $file_prefix . $id . ".cmd";
}

function rspfile($id) {
  global $file_prefix;
  return $file_prefix . $id . ".rsp";
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
$block = get("block", "off");

$file_prefix = "/tmp/nv_";

if ($mode == "none") {
  if ($perform == "init") {

    $file = logfile($id);
    $fd = fopen($file, "w") or die("cannot create file " . $file);
    ftruncate($fd, 0);
    fclose($fd);

    $file = lockfile($id);
    $fd = fopen($file, "w") or die("cannot create file " . $file);
    ftruncate($fd, 0);
    fclose($fd);

    $file = cmdfile($id);
    $fd = fopen($file, "w") or die("cannot create file " . $file);
    ftruncate($fd, 0);
    fclose($fd);

    $file = rspfile($id);
    $fd = fopen($file, "w") or die("cannot create file " . $file);
    ftruncate($fd, 0);
    fclose($fd);

    logmsg($id, "init $id\n");
    return;
  }
  if ($perform == "reset") {
    $file = lockfile($id);
    unlink($file) or die("cannot delete file " . $file);
    $file = cmdfile($id);
    unlink($file) or die("cannot delete file " . $file);
    $file = rspfile($id);
    unlink($file) or die("cannot delete file " . $file);
    $file = logfile($id);
    unlink($file) or die("cannot delete file " . $file);
    return;
  }
} else if ($mode == "cli2srv") {
  if ($perform == "send" || $perform == "send_and_rcv") {
    $data = get_url_var("data");
    logmsg($id, "cli2srv: $perform $id $data\n");

    if ($data) {
      $file = cmdfile($id);
      for (;;) {
	$fdlck = lock($id);
	$contents = file_get_contents($file);
	if (!$contents) {
	  break;
	}
	unlock($fdlck);
	usleep(100000);
      }
      file_put_contents($file, $data);
      
      unlock($fdlck);

      if ($perform == "send_and_rcv") {
	for ($nn = 0; $nn < 1000; ++$nn) {
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
	  usleep(100000);
	}
      }
    }
    return;
  }
  if ($perform == "rcv") {
    for ($nn = 0; $nn < 1000; ++$nn) {
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
    return;
  }
} else if ($mode == "srv2cli") {
  if ($perform == "rsp") {
    $data = get_url_var("data");
    logmsg($id, "srv2cli: send response to client $id $data\n");
    if ($data) {
      $file = rspfile($id);
      for (;;) {
	$fdlck = lock($id);
	$contents = file_get_contents($file);
	if (!$contents) {
	  break;
	}
	unlock($fdlck);
	usleep(100000);
      }
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
