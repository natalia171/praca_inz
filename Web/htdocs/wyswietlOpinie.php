<?php
require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$ID_LEKARZA = mysqli_real_escape_string($conn, $_REQUEST['ID_LEKARZA']);

$data = array();

$sql = "SELECT pacjenci.imie, pacjenci.nazwisko, opinie.TRESC_OPINII FROM pacjenci JOIN opinie ON pacjenci.id = opinie.ID_PACJENTA WHERE ID_LEKARZA='$ID_LEKARZA'";

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