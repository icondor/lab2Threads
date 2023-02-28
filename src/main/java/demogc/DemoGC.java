package demogc;

import java.util.ArrayList;
import java.util.List;

public class DemoGC {


    public static void main(String[] args) {

        List<byte[]> byteList = new ArrayList<>();

        Thread gcDaemonThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Runtime runtime = Runtime.getRuntime();
                long usedMemory = (runtime.totalMemory() - runtime.freeMemory());
                long maxMemory = runtime.maxMemory();
                long fm = runtime.freeMemory();
                System.out.printf("Used Memory: %.2f , Max Memory: %.2f , Free Memory: %.2f %n", (double)usedMemory/(1024*1024), (double)maxMemory/(1024*1024),(double)fm/(1024*1024) );

            }
        });

        gcDaemonThread.setDaemon(true);
        gcDaemonThread.start();

        long l = 0;
        while (true) {
            byte[] bytes = new byte[1000000];
            byteList.add(bytes);
            bytes=null;


        }
    }

}
