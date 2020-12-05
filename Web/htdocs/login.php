<?php

require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}


$encrypted_password = md5(mysqli_real_escape_string($conn, $_REQUEST['haslo']));
$email = mysqli_real_escape_string($conn, $_REQUEST['email']);
$haslo = mysqli_real_escape_string($conn, $_REQUEST['haslo']);


$sql = "select count(*) as IL from pacjenci where email='$email' and haslo='$encrypted_password'";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
  // output data of each row
  
  while($row = $result->fetch_assoc()) {
    echo $row["IL"] ;
  }
} else {
  echo "0 results";
}

$conn->close();
?>