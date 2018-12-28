package it.ichsugoroi.zexirioshin.utils;

public class ThreadableUtils {

    public static void killThread(Thread... toKill) {
        for(Thread t : toKill) {
            t.interrupt();
        }
    }
}
