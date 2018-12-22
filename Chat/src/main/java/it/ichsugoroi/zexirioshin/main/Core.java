package it.ichsugoroi.zexirioshin.main;

import it.ichsugoroi.zexirioshin.utils.ApplicationUtils;
import it.ichsugoroi.zexirioshin.web.HttpMessage;
import it.ichsugoroi.zexirioshin.web.IPAddress;

import java.util.LinkedHashMap;
import java.util.Map;

public class Core {

    public static void main(String[] argv) {
        doMain();
    }

    private static void doMain() {
        HttpMessage httpMessage = new HttpMessage();
//      httpMessage.send("http://dprssn.altervista.org/SendMessage.php", getParams());

        httpMessage.search("http://dprssn.altervista.org/MessageList.php");

    }

    private static Map<String, String> getParams() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("id", ApplicationUtils.getUUID());
        params.put("contenuto", "messaggio di prova");
        params.put("mittente", IPAddress.getMyIPAddress());
        params.put("destinatario", IPAddress.getMyIPAddress());
        params.put("data_invio", ApplicationUtils.getCurrentDate());
        params.put("ora_invio", ApplicationUtils.getCurrentTime());
        return params;
    }
}
