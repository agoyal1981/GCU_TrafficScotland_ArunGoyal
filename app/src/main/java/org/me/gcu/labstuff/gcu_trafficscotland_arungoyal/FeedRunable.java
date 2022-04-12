package org.me.gcu.labstuff.gcu_trafficscotland_arungoyal;
/*Name: "Arun Goyal"
Student # S2130744*/
import java.util.List;

public class FeedRunable implements Runnable{
    // url feed
    private String url;

    // use getItems to get items in the list
    public List<ItemsRSS> getItems(){return items;}

    // list to store items from the feed
    private List<ItemsRSS> items;


    public FeedRunable(String url){this.url = url;}
    // parse the feed for the url provided and store it in items
    @Override
    public synchronized void run(){
        XmlParser xml = new XmlParser(url);
        items = xml.parseFeed();
    }
}
