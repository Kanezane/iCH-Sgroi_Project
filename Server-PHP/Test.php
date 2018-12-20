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
				echo $_GET['msg'];
			} else {
				echo "Non ho ricevuto nulla";
			}
		
		?>
		
	</body>
	

</html>

