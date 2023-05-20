import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class WebCrawler {
    private Set<String> links;
    private List<String> articleTitles;

    public WebCrawler(){
        links = new HashSet<>();
        articleTitles = new ArrayList<>();
    }
    public void getPageLinks(String URL) {
        //4. Check if you have already crawled the URLs
        //(we are intentionally not checking for duplicate content in this example)
        if (links.size() >= 20) {
            return;
        }

        if (!links.contains(URL)) {
            try {
                //4. (i) If not add it to the index
                if (links.add(URL)) {
                    System.out.println(URL);
                }

                //2. Fetch the HTML code
                Document document = Jsoup.connect(URL).get();//jsoup jar to extract web data
                //3. Parse the HTML to extract links to other URLs
                String title = document.title();
                System.out.println("Title: "+ title);
                articleTitles.add(title);
                Elements linksOnPage = document.select("a[href]");

                //5. For each extracted URL... go back to Step 4.
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"));
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public List<String> getTitles(){
        return articleTitles;
    }
}