package monothread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    static String inputURL;
    Set<URL> urlSet;
    Set<String> mailSet;
    Queue<URL> urlQueue;

    public static void main(String[] args) {
        //inputURL = args[0];
        inputURL = "http://cs.lth.se/pierre/";
        //inputURL = "https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_frame_cols";
        new Main().run();
    }

    private void run() {
        urlSet = new HashSet<>();
        mailSet = new HashSet<>();
        urlQueue = new LinkedList<>();
        try {
            URL u = new URL(inputURL);
            parse(u);
            while(!urlQueue.isEmpty() && urlSet.size() < 1000){
                parse(urlQueue.poll());
            }
        } catch (MalformedURLException e) {
            System.out.println("Your URL is not correct");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error when parsing");
            e.printStackTrace();
        }
        print();

    }

    void parse(URL url) throws IOException {
        Document doc = null;
        try {
            doc = Jsoup.parse(url, 0);
        } catch (HttpStatusException e){

        }
        if (doc != null) {
            Elements links = doc.getElementsByTag("a");
            for (Element link : links) {
                String linkAbsHref = link.attr("abs:href");

                if (!linkAbsHref.isEmpty()) {

                    if (linkAbsHref.contains("mailto:")) {
                        mailSet.add(linkAbsHref);
                    } else {
                        URL tempURL = new URL(linkAbsHref);
                        URLConnection tempCon = tempURL.openConnection();
                        String contentType = tempCon.getContentType();

                        if (contentType == null) {
                            try {
                                contentType = URLConnection.guessContentTypeFromStream(tempCon.getInputStream());
                            } catch (UnknownHostException e) {
                            }
                        }

                        if (contentType != null) {
                            if (contentType.contains("text/html")) {
                                if (urlSet.add(tempURL)) {
                                    urlQueue.add(tempURL);
                                    System.out.println("Current size: " + urlSet.size());
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private void print(){
        System.out.println("List of URLs:");
        for (URL u : urlSet) {
            System.out.println(u.toString());
        }
        System.out.println("\nList of addresses:");
        for (String s : mailSet) {
            System.out.println(s.toString().toString());
        }
    }
}
