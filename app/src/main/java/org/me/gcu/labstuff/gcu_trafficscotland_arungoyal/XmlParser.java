package org.me.gcu.labstuff.gcu_trafficscotland_arungoyal;
/*Name: "Arun Goyal"
Student # S2130744*/
import android.text.Html;
import android.widget.Switch;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlParser {

    private String url;
    private List<ItemsRSS> items;
    private ItemsRSS itemsRSS;
    private String text;
    private InputStream stream;

    // this start parser in another class
    public XmlParser(String url){
        this.url = url;
        items = new ArrayList<>();
        itemsRSS = new ItemsRSS();
        this.initializeStream();
    }

    // established connection with url
    private void initializeStream(){
        URL aurl;
        URLConnection urlConnection;
        try{
            aurl = new URL(url);
            urlConnection = aurl.openConnection();
            this.stream = urlConnection.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<ItemsRSS> getItems() {
        return items;
    }

    //parse the RSS feed
    public List<ItemsRSS> parseFeed() {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(stream, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("item")) {
                            itemsRSS = new ItemsRSS();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    // Add Items to the list

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("item")) {
                            items.add(itemsRSS);
                        } else if (tagName.equalsIgnoreCase("title")) {
                            itemsRSS.setTitle(text);
                        } else if (tagName.equalsIgnoreCase("description")) {
                            String des = Html.fromHtml(text).toString();
                            itemsRSS.setDescription(des);
                        } else if (tagName.equalsIgnoreCase("location")) {
                            itemsRSS.setLocation(text);
                        } else if (tagName.equalsIgnoreCase("link")) {
                            itemsRSS.setLink(text);
                        } else if (tagName.equalsIgnoreCase("pubDate")) {
                            itemsRSS.setPubDate(new Date(text));
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
