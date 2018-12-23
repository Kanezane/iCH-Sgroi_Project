<?php session_start();?>
<html>

	<head>
		<meta http-equiv="Content-Language" content="it">
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<title>test</title>
	</head>

	<body>
		<?php
			if( isset($_GET['username'])
		     && isset($_GET['status']) ) {
				$host = "localhost";
				$user = "dprssn@localhost";
				$password = "";
				$nomedb = "my_dprssn";

				$username = $_GET['username'];
				$status = $_GET['status'];

				$mysqli = new mysqli($host, $user, $password, $nomedb);
				if($mysqli===false) {
					die("ERROR: could not connect. ".$mysqli->connect_error);
				}

				$testoquery="UPDATE my_dprssn.userStatus SET status='$status' WHERE username='$username'";
				if($mysqli->query($testoquery)===true) {
					echo "Record updated successully!";
				} else {
					echo "ERROR: could not able to execute $testoquery ".$mysqli->error;
				}

				$mysqli->close();
			} else {
				echo "Alcuni parametri mancanti, impossibile settare uno status così.";
			}
		?>
	</body>
	
</html>