<html>
<body>

Selectuje <?php 

$servername = "localhost";
$username = "natalka";
$password = "natalka";
$dbname = "praca_inz";




$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$email = mysqli_real_escape_string($conn, $_REQUEST['email']);
$haslo = mysqli_real_escape_string($conn, $_REQUEST['haslo']);


$sql = "select nazwisko from pacjenci where email='$email' and haslo='$haslo'";

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
    echo " - Nazwisko: " . $row["nazwisko"]. "<br>";
  }
} else {
  echo "0 results";
}

$conn->close();

 ?>

</body>
</html>