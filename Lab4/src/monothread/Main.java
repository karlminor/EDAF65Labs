package monothread;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	static String inputURL;

	public static void main(String[] args) {
		//inputURL = args[0];
		inputURL = "http://cs.lth.se/pierre/";
		new Main().run();
	}
	
	private void run(){
		try {
			URL u = new URL(inputURL);
			parse(u);
		} catch (MalformedURLException e) {
			System.out.println("Your URL is not correct");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Error when parsing");
			e.printStackTrace();
		}
		
	}
	
	void parse(URL url) throws IOException {
		InputStream is = url.openStream();
		Document doc = Jsoup.parse(is, "UTF-8", "http://cs.lth.se/");
		Elements base = doc.getElementsByTag("base");
		System.out.println("Base : " + base);
		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
			String linkAbsHref = link.attr("abs:href");
			if(!linkAbsHref.isEmpty()){
				if(linkAbsHref.contains("mailto:")){
					System.out.println("Mailto: " + linkAbsHref);
				} else {
					System.out.println("abshref: "
						+ linkAbsHref);
				}
			}
		}
		is.close();
	}
}
