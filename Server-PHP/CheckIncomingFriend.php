<?php session_start();?>
<html>

	<head>
		<meta http-equiv="Content-Language" content="it">
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<title>test</title>
	</head>

	<body>
		<?php
			if(isset($_GET['username'])) {
				$host = "localhost";
				$user = "dprssn@localhost";
				$password = "";
				$nomedb = "my_dprssn";

				$username = $_GET['username'];

				$connessione = mysql_connect($host, $user, $password) or die("ERROR: could not connect. ".mysql_error());
				mysql_select_db($nomedb) or die("ERROR: could not find database. ".mysql_error());

				$testoquery="SELECT username FROM Amici WHERE friend='$username'";
				$risultato=mysql_query($testoquery);
				$num=mysql_num_rows($risultato);
				
				for($i=0;$i<$num;$i++){
					$result=mysql_result($risultato,$i,'username');
					echo "$result";
				}

				mysql_close($connessione);
			} else {
				echo "Alcuni parametri mancanti, verificare nuove amicizie così.";
			}
		?>
	</body>
	
</html>