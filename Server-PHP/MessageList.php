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

			if( isset($_GET['mittente'])
		     && isset($_GET['destinatario'])) {
				$mittente = $_GET['mittente'];
				$destinatario = $_GET['destinatario'];

				$connesssione = mysql_connect($host, $user, $password) or die("Connessione non riuscita: ".mysql_error());
				mysql_select_db($nomedb) or die("Database non trovato: ".mysql_error());
				

				$testoquery="SELECT * FROM messaggi WHERE mittente='$mittente' AND destinatario='$destinatario' AND status='NEW'";
				$risultato=mysql_query($testoquery);
				$num=mysql_num_rows($risultato);
				
				for($i=0;$i<$num;$i++){
					$id=mysql_result($risultato,$i,'id');
					$contenuto=mysql_result($risultato,$i,'contenuto');
					$mittente=mysql_result($risultato,$i,'mittente');
					$destinatario=mysql_result($risultato,$i,'destinatario');
					$data_invio=mysql_result($risultato,$i, 'data_invio');
					$ora_invio=mysql_result($risultato,$i,'ora_invio');

					echo "$id;$contenuto;$mittente;$destinatario;$data_invio;$ora_invio<br>";
				}

				mysql_close($connessione);	
			} else {
				echo "Impossibile ricercare i messaggi senza specificare un mittente ed un destinatario!";
			}

						
		?>
		
	</body>
	

</html>

