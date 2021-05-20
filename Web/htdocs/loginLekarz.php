<?php
require_once 'config.php';
$conn = new mysqli($hostname, $username, $password, $db_name);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

		$encrypted_password = md5(mysqli_real_escape_string($conn, $_REQUEST['haslo']));
		$email = mysqli_real_escape_string($conn, $_REQUEST['email']);
		$haslo = mysqli_real_escape_string($conn, $_REQUEST['haslo']);


		$sql = "select czy_aktywne_konto as IL from lekarze
		where email='$email' and haslo='$encrypted_password' and czy_aktywne_konto=1";
		
		$sql2 = "select id from lekarze where email='$email' and haslo='$encrypted_password'";


		$result = $conn->query($sql);
		$result2= $conn->query($sql2);

		if ($result->num_rows > 0) {

		  while($row = $result->fetch_assoc()) {
			echo "tak" ;
			echo "\n";
		  }
		} else {
		  echo "Brak wyniku";
		}

		if ($result2->num_rows > 0) {
			
		  while($row = $result2->fetch_assoc()) {
			echo $row["id"] ;
		  }
		} else {
		  echo "Brak wyniku";
		}

$conn->close();
?>