package com.example.seekbartut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    //key to the seekbar preference in the settings activity
    //settingsactivity will store seekbar value to disk using this key
    private static final String SEEK_BAR_KEY = "seek_bar_key";

    //need these to track changes
    private SharedPreferences myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //manage the FAB
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(myIntent);
            }
        });
        //get a reference to the switch
        SwitchCompat s=findViewById(R.id.switch1);

        //you want to know if it's state has changed, it derives from CompoundButton
        //use it's OnCheckedChangeListener to get notified when it changes and its state
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                togglePreferenceChangeListener(b);
            }
        });

        // lets get a handle to default shared prefs
        myPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //set the background color according to defaultsharedprefs saved value
        setBackgroundColor(SEEK_BAR_KEY);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener listener = null;
    private void togglePreferenceChangeListener(boolean enablePreferenceListener) {
        // this is the bit that listens for any preference changes to defaultsharedpreferences
        // (the prefs that the pref activity accesses)
        //you can also implements OnSharedPreferenceChangeListener for the mainactivity and then
        //register to have have the mainactivity listen for changes like this
        //myPreference.registerOnSharedPreferenceChangeListener(this);
        //and forgo whats below
        if (listener == null) {
            listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    Toast.makeText(MainActivity.this, "Handle change of Key=" + key, Toast.LENGTH_SHORT).show();
                    if(key.equals(SEEK_BAR_KEY))
                        setBackgroundColor(key);
                }
            };
        }

        if(enablePreferenceListener)
            // register the listener (turn it on)
            myPreference.registerOnSharedPreferenceChangeListener(listener);
        else
            //or unregister it (turn it off)
            myPreference.unregisterOnSharedPreferenceChangeListener(listener);
    }

    private void setBackgroundColor(String key) {
        //get the new value of the slider and convert it to a fraction between .5 and 1.0
        double fract= (MainActivity.this.myPreference.getInt(key,50)/100.0) + .5;

        //get the original white
        int color = ContextCompat.getColor(MainActivity.this, R.color.white);

        //scale the white
        color=colorutil.mult(color,fract);

        //set the background color for this viewgroup
        ConstraintLayout cl=findViewById(R.id.cl);
        cl.setBackgroundColor(color);
    }

}