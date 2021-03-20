<?php
require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$ID_LEKARZA = mysqli_real_escape_string($conn, $_REQUEST['ID_LEKARZA']);
$ID_PACJENTA = mysqli_real_escape_string($conn, $_REQUEST['ID_PACJENTA']);
$TRESC_OPINII = mysqli_real_escape_string($conn, $_REQUEST['TRESC_OPINII']);


$sql = "INSERT INTO `opinie`(`ID_LEKARZA`, `ID_PACJENTA`, `TRESC_OPINII`) VALUES ('$ID_LEKARZA', '$ID_PACJENTA', '$TRESC_OPINII')";




if ($conn->query($sql) === TRUE) {
	echo "Opinia dodana";
	} else {
	echo "Coś poszło nie tak " . $sql . "<br>" . $conn->error;
	}

$conn->close();

?>

