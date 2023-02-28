package threads2;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimpleLinearSearchUsingThreads implements Runnable {

    private int[] arrayOfNr;
    private int nrToFind;

    private  static List<Integer> positions;

   private int start;
   private int end;



    public SimpleLinearSearchUsingThreads(int[] arrayOfNr, int nrToFind, int start, int end) {
        this.arrayOfNr = arrayOfNr;
        this.nrToFind = nrToFind;
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) {
        int[] arrayOfNr = {13,3,3,11,2,5,9,7,23,4,4,3,14};
        System.out.println(arrayOfNr.length);
        int nrToFind=3;

        SimpleLinearSearchUsingThreads searchThreads1 = new SimpleLinearSearchUsingThreads(arrayOfNr,nrToFind, 0,arrayOfNr.length/2);
        SimpleLinearSearchUsingThreads searchThreads2 = new SimpleLinearSearchUsingThreads(arrayOfNr,nrToFind, arrayOfNr.length/2,arrayOfNr.length);
        Thread threads1 = new Thread(searchThreads1);
        Thread threads2 = new Thread(searchThreads2);
        threads1.start(); //run
        threads2.start(); //run

//        final int NRTH=2; // cate threaduri, respectiv subarrays
//        Thread[] threads = new Thread[NRTH]; // threads
//        SimpleLinearSearchUsingThreads[] searchThreads = new SimpleLinearSearchUsingThreads[NRTH]; // runnable
//        int sizeOfEachArray = arrayOfNr.length/NRTH;
//
//        for(int i = 0; i<searchThreads.length;i++) {
//            int start = i*sizeOfEachArray;
//            int end;
//
//           if(i== searchThreads.length-1) {
//               end = arrayOfNr.length;
//           }
//           else {
//               end = (i+1) *sizeOfEachArray;
//           }
////            System.out.println("start"+start);
////            System.out.println("end"+end);
//
//                searchThreads[i] = new SimpleLinearSearchUsingThreads(arrayOfNr,nrToFind, start,end);
//            threads[i] = new Thread(searchThreads[i]);
//            threads[i].start(); //run
//
//
//
//
//        }


        // sa ma opresc pana se gata threadurile de cautat, si apoi sa afisez
        try {
            threads1.join(); //run
            threads2.join(); //run
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        //am terminat

        System.out.println("afisare rezultat program la "+ LocalTime.now());
        if(positions!=null) {

            for(int i: positions) {
                System.out.println("found on index:"+i);
            }
        }
        else
            System.out.println("n-am gasit");




    }


    private int searchLast(int[] arrayOfNr, int nrToFind) {
        int position=-1;
        for(int i = 0; i<arrayOfNr.length;i++) {
            if (nrToFind==arrayOfNr[i])
                position=i;
        }
        return position;
    }

    private List<Integer> searchAll(int[] arrayOfNr, int nrToFind) {
        List<Integer> positions = null;
        for(int i = 0; i<arrayOfNr.length;i++) {
            if (nrToFind==arrayOfNr[i]) {
                if(positions==null)
                    positions = new ArrayList<>(); // o singura data
                System.out.println("add on i "+i);
                positions.add(i);
            }
        }
       return positions;
    }

    @Override
    public void run() {




        try {
            Thread.currentThread().sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i = start; i<end;i++) {
            if (nrToFind==arrayOfNr[i]) {
                if(positions==null)
                    positions = new ArrayList<>(); // o singura data
                System.out.println("add on i "+i);
                positions.add(i);
            }
        }

        System.out.println("gata thread la "+ LocalTime.now());
    }
}
