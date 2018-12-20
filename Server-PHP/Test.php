<?php session_start();?>
<html>

	<head>
		<meta http-equiv="Content-Language" content="it">
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<title>test</title>
	</head>

	<body>
		<p>WE BELLA SONO VIVO</p>
		<?php
			if(isset($_GET['msg'])) {
				echo "<br>";
				echo $_GET['msg'];
			} else {
				echo "<br>";
				echo "Non ho ricevuto nessun messaggio da mostrare!";
			}
			
			if(isset($_GET['mittente'])) {
				echo "<br>";
				echo $_GET['mittente'];
			} else {
				echo "<br>";
				echo "Non ho ricevuto nessun mittente da mostrare!";
			}
			
			if(isset($_GET['destinatario'])) {
				echo "<br>";
				echo $_GET['destinatario'];
			} else {
				echo "<br>";
				echo "Non ho ricevuto nessun destinatario da mostrare!";
			}
			
		
			$address=$_GET['destinatario'];
			$port="4242";
			$msg=$_GET['msg'];

			$sock=socket_create(AF_INET,SOCK_STREAM,0) or die("Cannot create a socket");
			socket_connect($sock,$address,$port) or die("Could not connect to the socket");
			socket_write($sock,$msg);
		
		?>
		
	</body>
	

</html>

