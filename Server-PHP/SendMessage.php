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
		     && isset($_GET['contenuto'])
		     && isset($_GET['mittente'])
		     && isset($_GET['destinatario'])
		     && isset($_GET['data_invio'])
		     && isset($_GET['ora_invio']) ) {
				$host = "localhost";
				$user = "dprssn@localhost";
				$password = "";
				$nomedb = "my_dprssn";

				$id = $_GET['id'];
				$contenuto = $_GET['contenuto'];
				$mittente = $_GET['mittente'];
				$destinatario = $_GET['destinatario'];
				$data_invio = $_GET['data_invio'];
				$ora_invio = $_GET['ora_invio'];

				$mysqli = new mysqli($host, $user, $password, $nomedb);
				if($mysqli===false) {
					die("ERROR: could not connect. ".$mysqli->connect_error);
				}

				$testoquery="INSERT INTO messaggi(id, contenuto, mittente, destinatario, data_invio, ora_invio) VALUES('$id', '$contenuto', '$mittente', '$destinatario', '$data_invio', '$ora_invio')";
				if($mysqli->query($testoquery)===true) {
					echo "Records inserted successully";
				} else {
					echo "ERROR: could not able to execute $testoquery ".$mysqli->error;
				}

				$mysqli->close();
			} else {
				echo "Alcuni parametri mancanti, impossibile mandare un messaggio così.";
			}

			
			
		?>
	</body>
	

</html>

