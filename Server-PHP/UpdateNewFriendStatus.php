<?php session_start();?>
<html>

	<head>
		<meta http-equiv="Content-Language" content="it">
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<title>test</title>
	</head>

	<body>
		<?php
			if( isset($_GET['clientUsername'])
		     && isset($_GET['newFriend'])
		     && isset($_GET['friendship_status']) ) {
				$host = "localhost";
				$user = "dprssn@localhost";
				$password = "";
				$nomedb = "my_dprssn";

				$clientUsername = $_GET['clientUsername'];
				$newFriend = $_GET['newFriend'];
				$friendship_status = $_GET['friendship_status'];

				$mysqli = new mysqli($host, $user, $password, $nomedb);
				if($mysqli===false) {
					die("ERROR: could not connect. ".$mysqli->connect_error);
				}

				$testoquery="UPDATE my_dprssn.Amici SET friendship_status='$friendship_status' WHERE username='$clientUsername' AND friend='$newFriend'";
				if($mysqli->query($testoquery)===true) {
					echo "Record updated successully!";
				} else {
					echo "ERROR: could not able to execute $testoquery ".$mysqli->error;
				}

				$mysqli->close();
			} else {
				echo "Alcuni parametri mancanti, impossibile settare uno status amico così.";
			}
		?>
	</body>
	
</html>