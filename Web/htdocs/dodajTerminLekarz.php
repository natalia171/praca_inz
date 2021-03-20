<?php 

require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);


if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$CZAS_START = mysqli_real_escape_string($conn, $_REQUEST['CZAS_START']);
$CZAS_STOP = mysqli_real_escape_string($conn, $_REQUEST['CZAS_STOP']);
$ID_LEKARZA = mysqli_real_escape_string($conn, $_REQUEST['ID_LEKARZA']);


$sql = "INSERT INTO wizyty (ID_PACJENTA, ID_LEKARZA, CZAS_START, CZAS_STOP)
VALUES ('0','$ID_LEKARZA','$CZAS_START','$CZAS_STOP')";
 

	
	if ($conn->query($sql) === TRUE) {
	echo "Dodano termin!";
	} else {
	echo "Error: " . $sql . "<br>" . $conn->error;
	}
	

$conn->close();

 ?>




