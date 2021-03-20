
<?php 

require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);


if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$specjalizacja = mysqli_real_escape_string($conn, $_REQUEST['specjalizacja']);
$imie = mysqli_real_escape_string($conn, $_REQUEST['imie']);
$nazwisko = mysqli_real_escape_string($conn, $_REQUEST['nazwisko']);
$email = mysqli_real_escape_string($conn, $_REQUEST['email']);
$haslo = mysqli_real_escape_string($conn, $_REQUEST['haslo']);
$haslo1 = mysqli_real_escape_string($conn, $_REQUEST['haslo1']);
$encrypted_password = md5(mysqli_real_escape_string($conn, $_REQUEST['haslo']));
$pesel = mysqli_real_escape_string($conn, $_REQUEST['pesel']);
$telefon = mysqli_real_escape_string($conn, $_REQUEST['telefon']);

$sql = "INSERT INTO lekarze (specjalizacja, imie, nazwisko, email, haslo, pesel, telefon)
VALUES ('$specjalizacja','$imie','$nazwisko','$email','$encrypted_password','$pesel','$telefon')";
 

if ($haslo== $haslo1){
	echo "hasla takie same";
	
	if ($conn->query($sql) === TRUE) {
	echo "New record created successfully";
	} else {
	echo "Error: " . $sql . "<br>" . $conn->error;
	}
	
}else {echo "hasla rozne";}
$conn->close();

 ?>

