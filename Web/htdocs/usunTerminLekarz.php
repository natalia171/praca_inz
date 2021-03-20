<?php
require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$ID_LEKARZA = mysqli_real_escape_string($conn, $_REQUEST['ID_LEKARZA']);
$ID= (mysqli_real_escape_string($conn, $_REQUEST['ID']));


$sql = "DELETE FROM `wizyty` WHERE `ID`='$ID' AND `ID_LEKARZA`= '$ID_LEKARZA'";

$result = $conn->query($sql);



if ($conn->query($sql) === TRUE) {
	echo "Usunięto termin";
	} else {
	echo "Coś poszło nie tak " . $sql . "<br>" . $conn->error;
	}

$conn->close();

?>
