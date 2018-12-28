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

			if( isset($_GET['username'])) {
				$username = $_GET['username'];

				$connesssione = mysql_connect($host, $user, $password) or die("Connessione non riuscita: ".mysql_error());
				mysql_select_db($nomedb) or die("Database non trovato: ".mysql_error());
				

				$testoquery="SELECT friend FROM Amici WHERE username='$username' AND friendship_status='ACCEPTED'";
				$risultato=mysql_query($testoquery);
				$num=mysql_num_rows($risultato);
				
				for($i=0;$i<$num;$i++){
					$friend=mysql_result($risultato,$i,'friend');
					echo "$friend<br>";
				}

				mysql_close($connessione);	
			} else {
				echo "Impossibile ricercare gli amici di una persona se non è specificata la persona stessa!";
			}

						
		?>
		
	</body>
	

</html>