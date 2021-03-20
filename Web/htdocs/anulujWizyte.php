<?php
require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$ID_PACJENTA = mysqli_real_escape_string($conn, $_REQUEST['ID_PACJENTA']);
$ID= (mysqli_real_escape_string($conn, $_REQUEST['ID']));


$sql = "UPDATE `wizyty` SET `ID_PACJENTA`= '0' WHERE `ID`='$ID' ";

$result = $conn->query($sql);



if ($conn->query($sql) === TRUE) {
	echo "Wizyta anulowana";
	} else {
	echo "Coś poszło nie tak " . $sql . "<br>" . $conn->error;
	}

$conn->close();

?>

