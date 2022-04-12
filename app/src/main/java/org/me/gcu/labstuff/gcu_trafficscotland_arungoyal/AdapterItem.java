package org.me.gcu.labstuff.gcu_trafficscotland_arungoyal;
/*Name: "Arun Goyal"
Student # S2130744*/
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class AdapterItem extends ArrayAdapter<ItemsRSS> {
//variables
    private Context context;
    private int resource;
    private int lastPosition = -1;
    List<ItemsRSS> itemsRSSList;
// constructor
    public AdapterItem(@NonNull Context context, int resource, @NonNull List<ItemsRSS> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.itemsRSSList = objects;
    }
// return the postion of the item
    public ItemsRSS getItem(int position){
        return itemsRSSList.get(position);
    }

    private static class ViewHolder{
        TextView title;
        TextView description;
        TextView location;
        TextView link;
        TextView date;
        TextView color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // pull the information need to be displayed
        String title = getItem(position).getTitle();
        String description = getItem(position).getDescription();
        String location = "Location: " + getItem(position).getLocation();
        String link = "Link: " + getItem(position).getLink();
        Date date = getItem(position).getPubDate();

        ItemsRSS itemsRSS = new ItemsRSS(title, description, location, link, date);

        final View result;
        ViewHolder holder;

        // store objects information
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        // update the viewholder and store information in layout
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.txtRssTitle);
            holder.description = convertView.findViewById(R.id.txtRssDescription);
            holder.location = convertView.findViewById(R.id.txtRssLocation);
            holder.link = convertView.findViewById(R.id.txtRssLink);
            holder.date = convertView.findViewById(R.id.txtRssPubDate);
            holder.color = convertView.findViewById(R.id.layoutColor);

            result = convertView;
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        // check if current position is greater than the last position used using animation
        Animation animation = AnimationUtils.loadAnimation(context, (position>lastPosition)?
                R.anim.loading_down:R.anim.loading_up);
        result.startAnimation(animation);
        lastPosition = position;

        holder.title.setText(title);
        holder.description.setText(description);
        holder.location.setText(location);
        holder.link.setText(link);
        holder.date.setText(date.toString());
        holder.color.setTextColor(Color.WHITE);

        // color guide
        // Green - less than week
        // Yellow - less than month
        // Red - More than month
        try{
            Date startDate = itemsRSS.getStartDate();
            Date endDate = itemsRSS.getEndDate();
            long dateDifference = itemsRSS.getDateDifference(endDate);
            if(dateDifference<=7){
                holder.color.setBackgroundColor(Color.GREEN);
                holder.color.setTextColor(Color.GREEN);
            }else if(dateDifference >7 && dateDifference<30){
                holder.color.setBackgroundColor(Color.YELLOW);
                holder.color.setTextColor(Color.YELLOW);
            }else{
                holder.color.setBackgroundColor(Color.RED);
                holder.color.setTextColor(Color.RED);
            }
        }catch(Exception e)
        {
            Log.e("MyTag", "Invalid Date Difference");
        }
        return convertView;
    }
}
