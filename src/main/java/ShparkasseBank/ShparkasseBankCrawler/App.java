package ShparkasseBank.ShparkasseBankCrawler;

import java.io.IOException;

import org.jsoup.Jsoup;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    	DB database = mongoClient.getDB("prvaDB");
    	DBCollection collection = database.getCollection("Valuti");
    	        
    	
    	 WebClient webClient= new WebClient(BrowserVersion.CHROME);
       	 webClient.getOptions().setJavaScriptEnabled(true);
      	 HtmlPage page=webClient.getPage("https://en.sparkasse.mk/kursna-lista-shparkase-banka.nspx");
     	 String pageContent=page.asXml();
      	 System.out.println(pageContent);
     	 
      	 org.jsoup.nodes.Document doc = Jsoup.parse(pageContent);
      	 org.jsoup.select.Elements rows = doc.select("tr");
      	 
         String jsonMsg = "[";

         for(org.jsoup.nodes.Element row :rows)
        
         {

             org.jsoup.select.Elements columns = row.select("td");
             
             int i = 1;
             
             jsonMsg = jsonMsg + "{";
          	
             for (org.jsoup.nodes.Element column:columns)

             {
            	 
                
                 if(i==1)
                 {
                 	jsonMsg = jsonMsg + "\"Земја\":";
                 	
                 	jsonMsg = jsonMsg + "\""+ column.text()+"\"";
                     
                 	jsonMsg = jsonMsg + ",";
                 	
                 	System.out.print(column.text());
                 }
                 if(i==2)
                 {
                 	jsonMsg = jsonMsg + "\"Шифра\":";
                 	
                 	jsonMsg = jsonMsg + "\""+ column.text()+"\"";
                     
                 	jsonMsg = jsonMsg + ",";
                 	
                 	System.out.print(column.text());
                 }
                 if(i==3)
                 {
                 	jsonMsg = jsonMsg + "\"Ознака\":";
                 	
                 	jsonMsg = jsonMsg + "\""+ column.text()+"\"";
                     
                 	jsonMsg = jsonMsg + ",";
                 	
                 	System.out.print(column.text());
                 }
                 if(i==4)
                 {
                 	jsonMsg = jsonMsg + "\"Паритет\":";
                 	
                 	jsonMsg = jsonMsg + "\""+ column.text()+"\"";
                     
                 	jsonMsg = jsonMsg + ",";
                 	
                 	System.out.print(column.text());
                 }
                 if(i==5)
                 {
                 	jsonMsg = jsonMsg + "\"Куповен\":";
                 	
                 	jsonMsg = jsonMsg + "\""+ column.text()+"\"";
                     
                 	jsonMsg = jsonMsg + ",";
                 	
                 	System.out.print(column.text());
                 }
                 if(i==6)
                 {
                 	jsonMsg = jsonMsg + "\"Продажен\":";
                 	
                 	jsonMsg = jsonMsg + "\""+ column.text()+"\"";
                     
                 	
                 	System.out.print(column.text());
                 }

              i++;

             }
             
            
             
             jsonMsg = jsonMsg + "},";
             
             System.out.println();
              
         }
         jsonMsg = jsonMsg.substring(0, jsonMsg.length()-1);
         jsonMsg = jsonMsg + "]";

         System.out.print(jsonMsg);
    	 
     }
}
