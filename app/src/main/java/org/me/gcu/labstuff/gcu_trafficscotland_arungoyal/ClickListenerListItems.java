package org.me.gcu.labstuff.gcu_trafficscotland_arungoyal;
/*Name: "Arun Goyal"
Student # S2130744*/
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

public class ClickListenerListItems implements AdapterView.OnItemClickListener {

    private Activity activity;
    private AdapterItem adapter;

    public ClickListenerListItems(Activity activity, AdapterItem adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        // get details of the item click on list
        ItemsRSS item = adapter.getItem(i);

        //get title, url and location
        String title = item.getTitle();
        String url = item.getLink();
        String location = item.getLocation();

        // Create Dialog box, to open location in google maps
        AlertDialog dialog = new AlertDialog.Builder(activity).create();

        dialog.setCancelable(true);
        dialog.setTitle(item.getTitle());
        DialogInterface.OnClickListener clickListener = new ClickListenerLink(url, activity);

        // Create Buttons to open browser, google maps and cancel
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Open Link", clickListener);
        DialogInterface.OnClickListener maps = new ClickListenerLocation(location, activity, title);
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "View Location in Google Maps", maps);
        dialog.setButton(Dialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){

            }
        });
        dialog.show();

    }
}
