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
$encrypted_password = md5(mysqli_real_escape_string($conn, $_REQUEST['haslo']));
$email = mysqli_real_escape_string($conn, $_REQUEST['email']);
$haslo = mysqli_real_escape_string($conn, $_REQUEST['haslo']);
//$id = mysqli_real_escape_string($conn, $_REQUEST['id']);


//polecenie sql - zlicz rekordy o loginie i hasle takim jak otrzymanym z androida
$sql = "select count(*) as IL from lekarze where email='$email' and haslo='$encrypted_password'";

$sql2 = "select id from lekarze where email='$email' and haslo='$encrypted_password'";


// tworzenie wyniku po wyslaniu zapytania sql
$result = $conn->query($sql);

$result2= $conn->query($sql2);

//pobiera liczbe wierszy w wyniku i sprawdza czy jest wieksza od 0
if ($result->num_rows > 0) {
	
  // output data of each row
  //wyciaga dane z tabeli
  while($row = $result->fetch_assoc()) {
    echo $row["IL"] ;
	echo "\n";
  }
} else {
  echo "0 results";
}

if ($result2->num_rows > 0) {
	
  // output data of each row
  //wyciaga dane z tabeli
  while($row = $result2->fetch_assoc()) {
    echo $row["id"] ;
  }
} else {
  echo "0 results";
}

$conn->close();
?>