package it.ichsugoroi.zexirioshin.utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseableUtils {

    public static void close(Closeable... toClose) {
        for(Closeable c : toClose) {
            try {
                c.close();
            } catch (IOException e) {
                //non mi importa
            }
        }
    }
}
