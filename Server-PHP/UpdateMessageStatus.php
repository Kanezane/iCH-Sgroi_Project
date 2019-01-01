<?php session_start();?>
<html>

	<head>
		<meta http-equiv="Content-Language" content="it">
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<title>test</title>
	</head>

	<body>
		<?php
			$host = "localhost";
			$user = "dprssn@localhost";
			$password = "";
			$nomedb = "my_dprssn";

			if( isset($_GET['id_msg'])) {
				$id = $_GET['id_msg'];

				$connesssione = mysql_connect($host, $user, $password) or die("Connessione non riuscita: ".mysql_error());
				mysql_select_db($nomedb) or die("Database non trovato: ".mysql_error());
				
				$mysqli = new mysqli($host, $user, $password, $nomedb);
				if($mysqli===false) {
					die("ERROR: could not connect. ".$mysqli->connect_error);
				}

				$testoquery = "UPDATE messaggi SET status='RECEIVED' WHERE id='$id'";
				if($mysqli->query($testoquery)===true) {
					echo "Record updated successully!";
				} else {
					echo "ERROR: could not able to execute $testoquery ".$mysqli->error;
				}

				$mysqli->close();
			} else {
				echo "Impossibile fare l'update dei messaggi senza specificare un id!";
			}

						
		?>
		
	</body>
	

</html>

