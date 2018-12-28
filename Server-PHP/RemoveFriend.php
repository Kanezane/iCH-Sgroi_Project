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
		     && isset($_GET['friendUsername']) ) {
				$host = "localhost";
				$user = "dprssn@localhost";
				$password = "";
				$nomedb = "my_dprssn";

				$clientUsername = $_GET['clientUsername'];
				$friendUsername = $_GET['friendUsername'];
				
				$mysqli = new mysqli($host, $user, $password, $nomedb);
				if($mysqli===false) {
					die("ERROR: could not connect. ".$mysqli->connect_error);
				}

				$testoquery="DELETE FROM Amici WHERE username='$clientUsername' AND friend='$friendUsername'";
				if($mysqli->query($testoquery)===true) {
					echo "Friend deleted successully";
				} else {
					echo "ERROR: could not able to execute $testoquery ".$mysqli->error;
				}

				$mysqli->close();
			} else {
				echo "Alcuni parametri mancanti, impossibile eliminare un amico così.";
			}
		?>
	</body>
	
</html>