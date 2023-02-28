package thdemosynch;

public class Principala {

    public static void main(String[] args) {

        DownloadManager dminitial = DownloadManager.getInstance();

       DownloadManagerVers2UsingSemaphores dm = DownloadManagerVers2UsingSemaphores.getInstance();

        String[] urls= {"url1", "url2", "url3", "url4", "url5", "url6"};

        int currentDownloads = 0;
        for(String u: urls) {

                //dminitial.download(u); // initial impl
            try {
                dm.download(u); // the one that groups them 2 by 2
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
