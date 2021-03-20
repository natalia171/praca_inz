<?php
// wyslij zadanie do pliku config.php
require_once 'config.php';
//otwiera polaczenie z serwerem mysql ????skad wie jakie sa wartosci
$conn = new mysqli($hostname, $username, $password, $db_name);

//pobiera ostatni blad i wyswietla
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

//tworzenie zmiennych o wartosciach pobranych z androida przez request
$ID_PACJENTA = mysqli_real_escape_string($conn, $_REQUEST['ID_PACJENTA']);
//ID WIZYTY
$ID= (mysqli_real_escape_string($conn, $_REQUEST['ID']));


//polecenie sql 
$sql = "UPDATE `wizyty` SET `ID_PACJENTA`= '$ID_PACJENTA' WHERE `ID`='$ID'";

// tworzenie wyniku po wyslaniu zapytania sql
$result = $conn->query($sql);



if ($conn->query($sql) === TRUE) {
	echo "Wizyta potwierdzona";
	} else {
	echo "Coś poszło nie tak " . $sql . "<br>" . $conn->error;
	}

$conn->close();

?>

