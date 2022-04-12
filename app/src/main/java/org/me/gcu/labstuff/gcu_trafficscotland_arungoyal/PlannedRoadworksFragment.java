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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlannedRoadworksFragment extends Fragment {
    private List<ItemsRSS> itemsRSSList;
    private AdapterItem adapter;
    private ListView listView;
    private Date dateFrom;
    private Date dateTo;
    private RelativeLayout layout;
    private List<ItemsRSS> listUpload;
    public PlannedRoadworksFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        //enter url to be parsed
        FeedRunable feedRunable = new FeedRunable("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx");

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
        //listUpload = itemsRSSList;
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_planned_roadworks,
                container, false);
        this.layout = layout;

        // Button Ids
        Button buttonFrom = (Button) layout.findViewById(R.id.btnFromDatePlanned);
        buttonFrom.setOnClickListener((view) ->{ showFromDatePicker();});
        Button buttonTo = (Button) layout.findViewById(R.id.btnToDatePlanned);
        buttonTo.setOnClickListener((view) -> { showToDatePicker();});
        Button search = (Button) layout.findViewById(R.id.btnSearchPlanned);

        final EditText locationPl = (EditText) layout.findViewById(R.id.edtPlannedRoadworksLocation);

        locationPl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String currentText = String.valueOf(locationPl.getText());
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
        // search will not be enabled if date is not set
        if(dateTo==null || dateFrom==null){
            search.setEnabled(false);
        }else{
            search.setEnabled(true);
        }
        // search > set onClick listener
        search.setOnClickListener((view) -> {
            List<ItemsRSS> testList = new ArrayList<>();
            for (ItemsRSS currentItem: listUpload){
                Date startDate = currentItem.getPubDate();
                Date endDate = currentItem.getEndDate();

                    if (startDate.after(dateFrom) && endDate.before(dateTo)){
                        testList.add(currentItem);
                    }
            }
            listUpload.clear();
            listUpload.addAll(testList);
            adapter.notifyDataSetChanged();
            listView.invalidateViews();
            listView.refreshDrawableState();

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_planned_roadworks, container, false);
    });
    listView = (ListView) layout.findViewById(R.id.lstPlannedRoadworks);
        listUpload = itemsRSSList;
        adapter = new AdapterItem(layout.getContext(), R.layout.itemsdisplay, listUpload);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ClickListenerListItems(getActivity(), adapter));
        return layout;
    }
    Calendar calendar = Calendar.getInstance();

    public void showToDatePicker() {
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), toDate, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePicker.show();
    }
    DatePickerDialog.OnDateSetListener toDate = (view, year, monthOfYear, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateTo = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String formatDate = dateFormat.format(dateTo);
        Button button = (Button) layout.findViewById(R.id.btnToDatePlanned);
        button.setText(formatDate);
        Button search = (Button) layout.findViewById(R.id.btnSearchPlanned);
        if(dateTo==null || dateFrom==null){
            search.setEnabled(false);
        }else{
            search.setEnabled(true);
        }
    };

    public void showFromDatePicker() {
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), fromDate, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePicker.show();
    }
    DatePickerDialog.OnDateSetListener fromDate = (view, year, monthOfYear, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateFrom = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String formatDate = dateFormat.format(dateFrom);
        Button button = (Button) layout.findViewById(R.id.btnFromDatePlanned);
        button.setText(formatDate);
        Button search = (Button) layout.findViewById(R.id.btnSearchPlanned);
        if(dateTo==null || dateFrom==null){
            search.setEnabled(false);
        }else{
            search.setEnabled(true);
        }
    };

}