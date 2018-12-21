package it.ichsugoroi.zexirioshin.web;

import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerResponse {

    public void startServer() {
        boh();
        /*Thread t = new Thread(() -> boh());
        t.start();*/
    }

    protected void boh() {
        ServerSocket listener = null;
        Socket socket = null;
        BufferedReader in = null;
        try {
            listener = new ServerSocket(4284);
            System.out.println("Server " + listener.getLocalSocketAddress() + " waiting for client on port " + listener.getLocalPort());
            while (true) {
                System.out.println("a");
                socket = listener.accept();
                System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        } finally {
            CloseableUtils.close(listener, socket, in);
        }
    }
}
