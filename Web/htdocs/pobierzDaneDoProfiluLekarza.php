<?php

require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// id lekarza
$ID = mysqli_real_escape_string($conn, $_REQUEST['ID']);


$data = array();

$sql = "select specjalizacja, imie, nazwisko from lekarze 
 where ( ID='$ID' ) ";
 
$result = $conn->query($sql);
if ($result->num_rows > 0) {
  // output data of each row
  
  while($row = $result->fetch_assoc()) {
	$data[] = $row;
  }
} 

echo json_encode($data);

$conn->close();
?>