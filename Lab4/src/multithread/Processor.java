package multithread;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Processor extends Thread{
    private Spider spider;
    public boolean status;

    public Processor(Spider spider){
        this.spider = spider;
        status = true;
    }

    public void run() {
        while(spider.hashSetSize()<200 || spider.remainingSize() == 0) {
            URL u = null;
            try {
                u = spider.get();
            } catch (InterruptedException e) {
            }
            parse(u);
        }

    }

    private void parse(URL url)  {
        Document doc = null;
        try {
            doc = Jsoup.parse(url, 0);
        } catch (HttpStatusException e) {
        } catch (IOException e) {
        }
        if (doc != null) {
            Elements links = doc.getElementsByTag("a");
            for (Element link : links) {
                String linkAbsHref = link.attr("abs:href");

                if (!linkAbsHref.isEmpty()) {

                    if (linkAbsHref.contains("mailto:")) {
                        spider.addMail(linkAbsHref);
                    } else {
                        URL tempURL = null;
                        try {
                            tempURL = new URL(linkAbsHref);
                        } catch (MalformedURLException e) {
                        }
                        URLConnection tempCon = null;
                        try {
                            tempCon = tempURL.openConnection();
                        } catch (IOException e) {
                        }
                        String contentType = tempCon.getContentType();

                        if (contentType == null) {
                            try {
                                contentType = URLConnection.guessContentTypeFromStream(tempCon.getInputStream());
                            } catch (UnknownHostException e) {
                            } catch (IOException e) {
                            }
                        }

                        if (contentType != null) {
                            if (contentType.contains("text/html")) {
                                spider.add(tempURL);
                            }
                        }

                    }
                }
            }
        }
    }

}


