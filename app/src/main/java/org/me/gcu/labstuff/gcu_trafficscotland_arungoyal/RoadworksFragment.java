package org.me.gcu.labstuff.gcu_trafficscotland_arungoyal;
/*Name: "Arun Goyal"
Student # S2130744*/
import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RoadworksFragment extends Fragment {
    private List<ItemsRSS> itemsRSSList;
    private AdapterItem adapter;
    private ListView listView;
    private Date date;
    private RelativeLayout layout;
    private List<ItemsRSS> listUpload;

    public RoadworksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        //enter url to be parsed
        FeedRunable feedRunable = new FeedRunable("https://trafficscotland.org/rss/feeds/roadworks.aspx");

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
       // listUpload = itemsRSSList;
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_roadworks,
                container, false);
        this.layout = layout;

        listView = (ListView) layout.findViewById(R.id.lstRoadworks);

        final EditText location = (EditText) layout.findViewById(R.id.edtRoadworksLocation);

        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {
            String currentText = String.valueOf(location.getText());
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

        Button button = (Button) layout.findViewById(R.id.btnToDate);
        button.setOnClickListener(view -> showToDatePicker());
        Button search = (Button) layout.findViewById(R.id.btnSearch);
        // search will not be enabled if date is not set
        search.setEnabled(date != null && !date.before(new Date()));
        // search > set onClick listener
        search.setOnClickListener((view) -> {
            List<ItemsRSS> testList = new ArrayList<>();
            for (ItemsRSS currentItem: listUpload){
                try{
                    Date endDate = currentItem.getEndDate();
                    Log.e("MyTag", endDate.toString());
                    if (endDate.before(date)){
                        testList.add(currentItem);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            listUpload.clear();
            listUpload.addAll(testList);
            adapter.notifyDataSetChanged();
            listView.invalidateViews();
            listView.refreshDrawableState();
        });
        listUpload = itemsRSSList;
        adapter = new AdapterItem(layout.getContext(), R.layout.itemsdisplay, listUpload);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ClickListenerListItems(getActivity(), adapter));
        return layout;

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_roadworks, container, false);
    }
    public void showToDatePicker() {
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), dateSet, calendar.get(Calendar.YEAR)
        , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePicker.show();
    }
    Calendar calendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener dateSet = (view, year, monthOfYear, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String formatDate = dateFormat.format(date);
        Button button = (Button) layout.findViewById(R.id.btnToDate);
        button.setText(formatDate);
        Button search = (Button) layout.findViewById(R.id.btnSearch);
        search.setEnabled(date != null && !date.before(new Date()));
    };

}