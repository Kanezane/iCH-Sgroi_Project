<?php session_start();?>
<html>

	<head>
		<meta http-equiv="Content-Language" content="it">
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<title>test</title>
	</head>

	<body>
		<?php
			if( isset($_GET['id'])
		     && isset($_GET['contenuto']) ) {
				$host = "localhost";
				$user = "dprssn@localhost";
				$password = "";
				$nomedb = "my_dprssn";

				$id = $_GET['id'];
				$contenuto = $_GET['contenuto'];

				$mysqli = new mysqli($host, $user, $password, $nomedb);
				if($mysqli===false) {
					die("ERROR: could not connect. ".$mysqli->connect_error);
				}

				$testoquery="DELETE FROM my_dprssn.messaggi WHERE messaggi.id='$id'";
				if($mysqli->query($testoquery)===true) {
					echo "Record deleted successully";
				} else {
					echo "ERROR: could not able to execute $testoquery ".$mysqli->error;
				}

				$mysqli->close();
			} else {
				echo "Alcuni parametri mancanti, impossibile eliminare un messaggio così.";
			}
		?>
	</body>
	
</html>