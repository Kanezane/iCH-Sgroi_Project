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

			/*$sock=fsockopen("udp://" . $address, $port, $errno, $errstr, 50);
			if(!$sock){
				echo ' error: ' . $errno . ' errstr: ' . $errstr;
				die;
			}else{
				fwrite($sock, $msg);		
				fclose($sock);
			}*/


			$complete_address = 'udp://' . $address . ':' . $port;
			$socket = stream_socket_client($complete_address);
			if($socket) {
				$sent = stream_socket_sendto($socket, $msg);
				if ($sent > 0) {
					//vabbe tanto non manda niente indietro per ora java
~					/*$server_response = fread($socket, $port);
					echo $server_response;*/
				}
			} else { echo 'Unable to connect to server'; }
			
			stream_socket_shutdown($socket, STREAM_SHUT_RDWR);
		?>
		
	</body>
	

</html>

