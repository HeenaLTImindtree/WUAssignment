

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
    public class GetAllLinks {
static WebDriver driver;
        public static void main(String[] args) {
            driver = new ChromeDriver();
            GetAllLinks links = new GetAllLinks();

            //Launching sample website
            driver.get("https://www.flipkart.com/");
            driver.manage().window().maximize();

            //Get list of web-elements with tagName  - a
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            List <String>foEach= links.byForEach(allLinks);
            List <String>lambda= links.byLambda(allLinks);
        }
            public List<String> byForEach(List<WebElement> allLinks)
            {
            List<String> listofURL= new ArrayList<>() ;
                //Traversing through the list and printing its text along with link address
              for(WebElement link: allLinks) {
                System.out.println(link.getText() + " - " + link.getAttribute("href"));

                listofURL.add(link.getText());

            }
              return listofURL;
            }

        public List<String> byLambda(List<WebElement> allLinks)
        {
            List<String> listofURL= new ArrayList<>() ;
            //Traversing through the list and printing its text along with link address
            allLinks.forEach(link->System.out.println(link.getText()));
            return listofURL;
        }

        public List<String> byStream(List<WebElement> allLinks)
        {
            List<String> listofURL= new ArrayList<>() ;
            //Traversing through the list and printing its text along with link address
            long startTime =System.currentTimeMillis();
            listofURL.parallelStream().forEach(e-> checkBroken(e));

          return listofURL;
        }
public void checkBroken(String link) {
    try {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() >= 400) {
            System.out.println(link + "" + httpURLConnection.getResponseMessage() + "is a broken link");

        } else
            System.out.println(link + "" + httpURLConnection.getResponseMessage());

}catch(Exception e)
        {}

}

    }


