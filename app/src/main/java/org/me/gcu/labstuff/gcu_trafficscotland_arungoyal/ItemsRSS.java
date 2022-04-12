package org.me.gcu.labstuff.gcu_trafficscotland_arungoyal;
/*Name: "Arun Goyal"
Student # S2130744*/
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ItemsRSS {

    private String title = "";
    private String description = "";
    private String location = "";
    private String link = "";
    private Date pubDate;

    public ItemsRSS() {
    }//Empty Constructor

    public ItemsRSS(String title, String description, String location, String link, Date pubDate) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.link = link;
        this.pubDate = pubDate;
    }
    //getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    // Get date from the description

    public Date getStartDate() {
        if(description.indexOf("Start Date") != -1){
            int firstIndex = description.indexOf("End Date: ") + 12;
            String edt = description.substring(firstIndex);
            if (edt.indexOf("\n") != -1){
                edt = edt.substring(edt.indexOf(",") + 2, edt.indexOf("\n") - 8);
                DateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
                Date startDate;
                try{
                    startDate = format.parse(edt);
                    return startDate;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Date getEndDate() {
        if(description.indexOf("End Date") != -1){
            int firstIndex = description.indexOf("End Date: ") + 10;
            String edt = description.substring(firstIndex);
            if (edt.indexOf("\n") != -1){
                edt = edt.substring(edt.indexOf(",") + 2, edt.indexOf("\n") - 8);
                DateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
                Date endDate;
                try{
                    endDate = format.parse(edt);
                    return endDate;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public long getDateDifference(Date date){
        TimeUnit timeUnit = TimeUnit.DAYS;
        long difference = date.getTime() - new Date().getTime();
        return Math.abs(timeUnit.convert(difference, TimeUnit.MILLISECONDS));
    }
}
