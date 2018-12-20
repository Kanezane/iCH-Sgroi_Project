package it.ichsugoroi.zexirioshin.main;

import it.ichsugoroi.zexirioshin.web.HttpRequest;


public class Core {

    public static void main(String[] argv) {
        System.out.println("Questa è la classe principale! :D");
        HttpRequest request = new HttpRequest("http://shin9xspace2.altervista.org/Test.php", "messaggio di prova!");

        request.sendPost();
    }
}
