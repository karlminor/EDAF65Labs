package multithread;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


public class Main {
    static String inputURL;
    private Spider spider;
    private List<Processor> processList;

    public static void main(String[] args) {
        //inputURL = args[0];
        inputURL = "http://cs.lth.se/pierre/";
        //inputURL = "https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_frame_cols";
        new Main().run();
    }

    private void run() {
        spider = new Spider();
        processList = new LinkedList<>();
        try {
            URL u = new URL(inputURL);
            spider.add(u);
        } catch (MalformedURLException e) {
            System.out.println("Your URL is not correct");
            System.exit(0);
        }
        for(int i = 0; i < 10; i++){
            new Processor(spider).start();
        }
        while(spider.hashSetSize()<200 || spider.remainingSize() == 0){
        }
        for(Processor p : processList){
            p.interrupt();
        }
        spider.print();
        System.exit(0);
    }

}
