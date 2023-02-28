package thdemosynch;

import java.util.Random;

public class DownloadManager {

    private static DownloadManager uniqueinstanceOfDownloadManager=null;
    private static final Object lock = new Object();


    /* design pattern de tip singleton */
    public static DownloadManager getInstance() {
        if(uniqueinstanceOfDownloadManager==null)
        {
            uniqueinstanceOfDownloadManager = new DownloadManager();
        }
        return  uniqueinstanceOfDownloadManager;
    }


    public final int MAX_DOWNLOADS=2;
    private int currentDownloads=0;

    public int getCurrentDownloads() {
        synchronized (lock) {
            return currentDownloads;
        }
    }


    public void download(String url) {

        synchronized (lock) {

                   //temporizarea cate x
                    while (currentDownloads >= MAX_DOWNLOADS) {
                        try {
                            lock.wait(); // th curent asteapta
                            Thread.currentThread().sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

            // System.out.println("cd prev to inc: "+currentDownloads);
                    // pornire thread de download pe acest url daca nu sunt la maxim
                    currentDownloads++;
         // System.out.println("cd post  inc: "+currentDownloads);

            MyFileDownloader mf = new MyFileDownloader(url);
            mf.start();// ar porni un thread de download
        }




    }

    public void downloadFinished() {
        synchronized (lock) {
         //   System.out.println("down prev to inc: "+currentDownloads);
            currentDownloads--;
        //    System.out.println("down post  inc: "+currentDownloads);
            lock.notify();
        }
    }

}

class MyFileDownloader extends Thread {
    private String url;

    public MyFileDownloader( String url) {
        this.url = url;
    }



    @Override
    public void run() {
        System.out.println("Downloading initial version  " + url);
        int downloadTime = 0;
        try {
            // Simulate the download process
            downloadTime = new Random().nextInt(1) + 1000;
            Thread.sleep(downloadTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
  //System.out.println(url + " has been downloaded in " + downloadTime + "ms");

        // DownloadManager, decrementez nr de downloads

        DownloadManager dm = DownloadManager.getInstance();

        dm.downloadFinished();
    }
}
