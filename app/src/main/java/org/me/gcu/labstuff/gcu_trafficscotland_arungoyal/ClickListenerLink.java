package org.me.gcu.labstuff.gcu_trafficscotland_arungoyal;
/*Name: "Arun Goyal"
Student # S2130744*/
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class ClickListenerLink implements DialogInterface.OnClickListener {

    private String url;
    Activity activity;

    //Empty Constructor
    public ClickListenerLink() {
    }

    // constructor for url and activity
    public ClickListenerLink(String url, Activity activity) {
        this.url = url;
        this.activity = activity;
    }
    // on click of link dialog button, it sends intent with url to browser
    @Override
    public void onClick(DialogInterface dialogInterface, int i){
        Log.e("MyTag", url);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.url));
        activity.startActivity(intent);
    }
}
