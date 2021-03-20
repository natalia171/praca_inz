<?php

require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}


$ID_LEKARZA = mysqli_real_escape_string($conn, $_REQUEST['ID_LEKARZA']);
$CZAS = mysqli_real_escape_string($conn, $_REQUEST['CZAS']);

$data = array();

$sql = "select CZAS_START, ID from wizyty where ID_LEKARZA='$ID_LEKARZA' and CZAS_START>'$CZAS' order by `CZAS_START` ASC";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
 
  while($row = $result->fetch_assoc()) {
	$data[] = $row;
  }
} 

echo json_encode($data);

$conn->close();
?>