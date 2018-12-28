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
		     && isset($_GET['password']) ) {
				$host = "localhost";
				$user = "dprssn@localhost";
				$password = "";
				$nomedb = "my_dprssn";

				$username = $_GET['username'];
				$password = $_GET['password'];

				$connesssione = mysql_connect($host, $user, $password) or die("Connessione non riuscita: ".mysql_error());
				mysql_select_db($nomedb) or die("Database non trovato: ".mysql_error());
				

				$risultato=mysql_query("SELECT username FROM utenti WHERE username='$username'");
				if(!$risultato) {
					mysql_close($connessione);
					die('Invalid query '.mysql_error());
				} else {
					$num=mysql_num_rows($risultato);
					for($i=0;$i<$num;$i++){
						$usernameFromResult = mysql_result($risultato, $i, 'username');
					}

					if(strcmp($usernameFromResult, $username) == 0) {
						echo "Username già in uso!";
					} else {
						$res = mysql_query("INSERT INTO utenti(username, password) VALUES ('$username', '$password')");
						$res1 = mysql_query("INSERT INTO userStatus(username, status) VALUES ('$username', 'OFFLINE')");
						if(!$res || !$res1) { 
							mysql_close($connessione);
							die('Invalid query '.mysql_error());
						} else {
							echo "User created successfully";
						}
					}	
				}
				
				mysql_close($connessione);	
			} else {
				echo "Alcuni parametri mancanti, impossibile registrare così.";
			}
		?>
	</body>
	
</html>