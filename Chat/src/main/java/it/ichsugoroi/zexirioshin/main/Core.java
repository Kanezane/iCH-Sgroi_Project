package it.ichsugoroi.zexirioshin.main;

import it.ichsugoroi.zexirioshin.web.HttpRequest;

import java.util.LinkedHashMap;
import java.util.Map;


public class Core {

    public static void main(String[] argv) {
        doMain();
    }

    private static void doMain() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("msg", "messaggio di prova");
        System.out.println("Questa è la classe principale! :D");
        HttpRequest request = new HttpRequest("http://shin9xspace2.altervista.org/Test.php", params);
        request.sendPost();
    }
}
