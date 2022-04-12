package org.me.gcu.labstuff.gcu_trafficscotland_arungoyal;
/*Name: "Arun Goyal"
Student # S2130744*/
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    // on create, set layout to activity_main and enable on item selected listner
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.roadworks_menu);
        bottomNavigationView.setOnItemSelectedListener(navListner);
        // Start app with Current Incidents
        if(savedInstanceState == null){
            CurrentIncidentsFragment currentIncidentsFragment = new CurrentIncidentsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentIncidentsFragment, "savedFragment").commit();
        }else{
            CurrentIncidentsFragment currentIncidentsFragment = (CurrentIncidentsFragment) getSupportFragmentManager()
                    .findFragmentByTag("savedFragments");
        }
    }
    // listener to create a new fragment when an item is selected from the navigation menu
    private BottomNavigationView.OnItemSelectedListener navListner = (menuItem)-> {
        Fragment selectedFragment = null;
        //switch used to match item selected
        switch (menuItem.getItemId()){
            case R.id.roadworks:
                selectedFragment = new RoadworksFragment();
                break;
            case R.id.plannedRoadworks:
                selectedFragment = new PlannedRoadworksFragment();
                break;
            case R.id.currentIncidents:
                selectedFragment = new CurrentIncidentsFragment();
                break;
        }
        // update the fragment manager to the selected fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };
}