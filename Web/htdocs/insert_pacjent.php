<html>
<body>

Insertuje <?php 

$servername = "localhost";
$username = "natalka";
$password = "natalka";
$dbname = "praca_inz";




$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$email = mysqli_real_escape_string($conn, $_REQUEST['email']);
$imie = mysqli_real_escape_string($conn, $_REQUEST['imie']);
$nazwisko = mysqli_real_escape_string($conn, $_REQUEST['nazwisko']);
$telefon = mysqli_real_escape_string($conn, $_REQUEST['telefon']);
$pesel = mysqli_real_escape_string($conn, $_REQUEST['pesel']);
$data_urodzenia = mysqli_real_escape_string($conn, $_REQUEST['data_urodzenia']);
$haslo = mysqli_real_escape_string($conn, $_REQUEST['haslo']);
$haslo1 = mysqli_real_escape_string($conn, $_REQUEST['haslo1']);


$sql = "INSERT INTO pacjenci (email, imie, nazwisko, telefon, pesel, data_urodzenia, haslo)
VALUES ('$email','$imie','$nazwisko','$telefon','$pesel','$data_urodzenia','$haslo')";

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

</body>
</html>