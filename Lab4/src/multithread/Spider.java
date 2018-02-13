package multithread;

import java.net.URL;
import java.util.*;

public class Spider{
    private List<URL> traversedURLs;
    private Queue<URL> remainingURLs;
    private List<String> mailList;
    private Set<URL> URLSet;


    public Spider(){
        traversedURLs = new LinkedList<>();
        remainingURLs = new LinkedList<>();
        mailList = new LinkedList<>();
        URLSet = new HashSet<>();

    }

    public synchronized void add(URL u){
        if(URLSet.add(u)){
            remainingURLs.add(u);
            notifyAll();
        }
    }

    public synchronized URL get() throws InterruptedException {
        while(remainingURLs.isEmpty()){
            wait();
        }
        URL tempURL = remainingURLs.poll();
        traversedURLs.add(tempURL);
        return tempURL;
    }

    public synchronized int hashSetSize(){
        return URLSet.size();
    }

    public synchronized int remainingSize(){
        return remainingURLs.size();
    }

    public synchronized void addMail(String s){
        if(!mailList.contains(s)) {
            mailList.add(s);
        }
    }


    public void print(){
        System.out.println("List of URLs:");
        for (URL u : URLSet) {
            System.out.println(u.toString());
        }
        System.out.println("\nList of addresses:");
        for (String s : mailList) {
            System.out.println(s);
        }
    }
}