
<html>
<body>

<form action="insert_pacjent.php" method="post">
email: <input type="text" name="email"><br>
Imie: <input type="text" name="imie"><br>
Nazwisko: <input type="text" name="nazwisko"><br>
telefon: <input type="text" name="telefon"><br>
PESEL: <input type="text" name="pesel"><br>
Data urodzin: <input type="text" name="data_urodzin"><br>
Haslo: <input type="text" name="haslo"><br>
Powtorz haslo: <input type="text" name="haslo1"><br>

<input type="submit">
</form>

</body>
</html>





<?php
$servername = "localhost";
$username = "natalka";
$password = "natalka";
$dbname = "praca_inz";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
$sql = "SELECT id, imie, nazwisko FROM pacjenci";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
    echo "id: " . $row["id"]. " - Imie: " . $row["imie"].  " - Nazwisko: " . $row["nazwisko"]. "<br>";
  }
} else {
  echo "0 results";
}


$conn->close();

?>
