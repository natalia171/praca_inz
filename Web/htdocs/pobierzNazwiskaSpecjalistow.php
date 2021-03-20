<?php

require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}


$specjalizacja = mysqli_real_escape_string($conn, $_REQUEST['specjalizacja']);


$data = array();

$sql = "select ID, imie, nazwisko from lekarze 
 where ( specjalizacja='$specjalizacja' ) 
 order by `nazwisko` ASC";
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