<?php session_start();?>
<html>

	<head>
		<meta http-equiv="Content-Language" content="it">
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<title>test</title>
	</head>

	<body>
		<?php
			if( isset($_GET['usernameAdder'])
		     && isset($_GET['usernameAdded'])
		     && isset($_GET['friendship_status']) ) {
				$host = "localhost";
				$user = "dprssn@localhost";
				$password = "";
				$nomedb = "my_dprssn";

				$usernameAdder = $_GET['usernameAdder'];
				$usernameAdded = $_GET['usernameAdded'];
				$friendship_status = $_GET['friendship_status'];
				
				$mysqli = new mysqli($host, $user, $password, $nomedb);
				if($mysqli===false) {
					die("ERROR: could not connect. ".$mysqli->connect_error);
				}

				$testoquery="INSERT INTO Amici(username, friend, friendship_status) VALUES('$usernameAdder', '$usernameAdded', '$friendship_status')";
				if($mysqli->query($testoquery)===true) {
					echo "Records inserted successully";
				} else {
					echo "ERROR: could not able to execute $testoquery ".$mysqli->error;
				}

				$mysqli->close();
			} else {
				echo "Alcuni parametri mancanti, impossibile aggiungere un amico così.";
			}
		?>
	</body>
	
</html>