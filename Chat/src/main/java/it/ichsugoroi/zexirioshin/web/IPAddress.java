package it.ichsugoroi.zexirioshin.web;

import it.ichsugoroi.zexirioshin.utils.ApplicationException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class IPAddress {

    public static String getMyIPAddress() {
        return getMyAddress();
    }

    private static String getMyAddress() {
        String res;
        try {
            URL url = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc = new BufferedReader(new InputStreamReader(url.openStream()));
            res = sc.readLine().trim();
            sc.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Cannot Execute Properly");
        }
        return res;
    }
}
