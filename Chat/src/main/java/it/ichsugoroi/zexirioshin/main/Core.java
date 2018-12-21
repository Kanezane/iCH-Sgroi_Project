package it.ichsugoroi.zexirioshin.main;

import it.ichsugoroi.zexirioshin.web.HttpSendRequest;
import it.ichsugoroi.zexirioshin.web.IPAddress;
import it.ichsugoroi.zexirioshin.web.ServerResponse;

import java.util.LinkedHashMap;
import java.util.Map;


public class Core {

    public static void main(String[] argv) {
        doMain();
    }

    private static void doMain() {
        HttpSendRequest request = new HttpSendRequest("http://shin9xspace2.altervista.org/Test.php", getParams());
        request.send();

        ServerResponse sr = new ServerResponse();
        sr.startServer();
    }

    private static Map<String, String> getParams() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("msg", "messaggio di prova");
        params.put("mittente", IPAddress.getMyIPAddress());
        params.put("destinatario", IPAddress.getMyIPAddress());
        return params;
    }
}
