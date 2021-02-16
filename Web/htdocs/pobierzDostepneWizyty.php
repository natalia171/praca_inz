<?php

require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}


$specjalizacja = mysqli_real_escape_string($conn, $_REQUEST['specjalizacja']);

$data = array();
$sql = "select l.imie,l.nazwisko,w.CZAS_START,w.CZAS_STOP from wizyty as w INNER JOIN lekarze as l ON w.ID_LEKARZA=l.ID where specjalizacja='$specjalizacja' and ID_PACJENTA='0' order by `CZAS_START` ASC";
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