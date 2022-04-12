package org.me.gcu.labstuff.gcu_trafficscotland_arungoyal;
/*Name: "Arun Goyal"
Student # S2130744*/
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class ClickListenerLocation implements DialogInterface.OnClickListener {

    private String location;
    private Activity activity;
    private String title;

    public ClickListenerLocation(String location, Activity activity, String title) {
        this.location = location;
        this.activity = activity;
        this.title = title;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        // split the string and store them in array
        String[] split = location.split("\\s+");
        String latitude = split[0];
        String longitude = split[1];

        // use the string to enter co-ordinates on the google maps to open the location
        String linkgoogle = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + "(" + title + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkgoogle));
        intent.setPackage("com.google.android.apps.maps");

        activity.startActivity(intent);
    }
}
