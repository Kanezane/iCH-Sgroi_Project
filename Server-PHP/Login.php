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
				

				$risultato=mysql_query("SELECT * FROM utenti WHERE username='$username' AND password='$password'");
				if(!$risultato) {
					mysql_close($connessione);
					die('Invalid query '.mysql_error());
				} else {
					$num=mysql_num_rows($risultato);
					if($num==0) {
						echo "Nome utente o password non corretti!";
					} else {
						echo "Login riuscito con successo!";
					}	
				}
				
				mysql_close($connessione);	
			} else {
				echo "Alcuni parametri mancanti, impossibile effettuare un login così.";
			}
		?>
	</body>
	
</html>