package thdemosynch;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class DownloadManagerVers2UsingSemaphores {

        private Semaphore semaphore;
        private static DownloadManagerVers2UsingSemaphores instance = null;

        private DownloadManagerVers2UsingSemaphores() {
            semaphore = new Semaphore(5);
        }
        public static DownloadManagerVers2UsingSemaphores getInstance() {
            if (instance == null) {
                instance = new DownloadManagerVers2UsingSemaphores();
            }
            return instance;
        }

        public void download(String fileName) throws InterruptedException {
            semaphore.acquire();
            new MyFileDownloader1(fileName).start();
        }


        public void downloadFinished() {
            semaphore.release();
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }




    class MyFileDownloader1 extends Thread {
        private String fileName;

        public MyFileDownloader1(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void run() {
            System.out.println("Downloading second version  " + fileName);
            int downloadTime = 0;
            try {
                // Simulate the download process
                downloadTime = new Random().nextInt(3) + 2000;
                Thread.sleep(downloadTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(fileName + " has been downloaded in " + downloadTime + "ms");
            DownloadManagerVers2UsingSemaphores.getInstance().downloadFinished();
        }
    }





