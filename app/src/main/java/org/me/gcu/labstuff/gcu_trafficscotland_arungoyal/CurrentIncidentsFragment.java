package org.me.gcu.labstuff.gcu_trafficscotland_arungoyal;
/*Name: "Arun Goyal"
Student # S2130744*/
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CurrentIncidentsFragment extends Fragment {
    private List<ItemsRSS> itemsRSSList;
    private AdapterItem adapter;
    private ListView listView;
    private List<ItemsRSS> listUpload;

    public CurrentIncidentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        //enter url to be parsed
        FeedRunable feedRunable = new FeedRunable("https://trafficscotland.org/rss/feeds/currentIncidents.aspx");

        // start a new thread to parse the feed
        try{
            Thread thread = new Thread(feedRunable);
            thread.start();
            thread.join();
        }
        catch(InterruptedException exception){
            exception.printStackTrace();
        }
        // get items from the feed
        itemsRSSList = feedRunable.getItems();
        listUpload = itemsRSSList;

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_current_incidents, container, false);
        listView = (ListView) layout.findViewById(R.id.lstCurrentIncidents);
        adapter = new AdapterItem(layout.getContext(), R.layout.itemsdisplay, itemsRSSList);
        listView.setAdapter(adapter);
        final EditText locationCI = (EditText) layout.findViewById(R.id.location);
        locationCI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String currentText = String.valueOf(locationCI.getText());
                Log.e("MyTag", currentText);

                List<ItemsRSS> testList = new ArrayList<>();
                for(ItemsRSS currentItem : itemsRSSList)
                {
                    String title = currentItem.getTitle();
                    if(title.toLowerCase().contains(currentText.toLowerCase())){
                        testList.add(currentItem);
                    }
                }
                listUpload.clear();
                listUpload.addAll(testList);
                adapter.notifyDataSetChanged();
                listView.invalidateViews();
                listView.refreshDrawableState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // check for the number of incidents and update
        TextView incident = (TextView) layout.findViewById(R.id.incidentNumber);
        if(itemsRSSList.size() > 0){
            incident.setTextSize(16);
            incident.setText("Number of Current Incidents: " + String.valueOf(itemsRSSList.size()));
        }else
        {
            incident.setTextSize(24);
            incident.setText("No Current Incidents");
        }
        listView.setOnItemClickListener(new ClickListenerListItems(getActivity(), adapter));

        return layout;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_current_incidents, container, false);
    }
}