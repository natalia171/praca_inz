<?php

require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

//date_default_timezone_set('Africa/Nairobi');
//$date = date('Y-m-d h:i:s a', time());
$ID_PACJENTA = mysqli_real_escape_string($conn, $_REQUEST['ID_PACJENTA']);
$CZAS_START = mysqli_real_escape_string($conn, $_REQUEST['CZAS_START']);

$data = array();

$sql = "select l.imie,l.nazwisko, l.specjalizacja, w.CZAS_START,
DATE_FORMAT(w.czas_start, '%Y-%m-%d %H:%i') AS CZAS_START_FORMATED,
 w.ID from wizyty as w INNER JOIN lekarze as l ON w.ID_LEKARZA=l.ID where ID_PACJENTA='$ID_PACJENTA' and CZAS_START<'$CZAS_START' order by `CZAS_START` DESC";
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