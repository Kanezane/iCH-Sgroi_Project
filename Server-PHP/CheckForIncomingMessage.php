<?php session_start();?>
<html>

	<head>
		<meta http-equiv="Content-Language" content="it">
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<title>test</title>
	</head>

	<body>
		<?php
			if( isset($_GET['clientUsername'])) {
				$host = "localhost";
				$user = "dprssn@localhost";
				$password = "";
				$nomedb = "my_dprssn";

				$clientUsername = $_GET['clientUsername'];				

				$connesssione = mysql_connect($host, $user, $password) or die("Connessione non riuscita: ".mysql_error());
				mysql_select_db($nomedb) or die("Database non trovato: ".mysql_error());
				

				$testoquery="SELECT mittente, count(*) AS nMsg FROM messaggi WHERE destinatario='$clientUsername' GROUP BY mittente";
				$risultato=mysql_query($testoquery);
				$num=mysql_num_rows($risultato);
				
				for($i=0;$i<$num;$i++){
					$mittente=mysql_result($risultato,$i,'mittente');
					$nMsg=mysql_result($risultato, $i, 'nMsg');
					echo "$mittente;$nMsg<br>";
				}

				mysql_close($connessione);	
			} else {
				echo "Impossibile ricercare i messaggi verso una persona se non è specificata la persona stessa!";
			}
		?>
	</body>
	
</html>