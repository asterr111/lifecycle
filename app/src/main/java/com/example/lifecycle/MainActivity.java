package com.example.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {

    // class variables
    private Context context;
    private int duration = Toast.LENGTH_SHORT;

    // Matching GUI controls to Java objects
    private Button btnExit;
    private EditText txtColorSelected;
    private TextView txtSpyBox;
    private LinearLayout myScreen;
    private String PREFNAME = "myPrefFile1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // display the main screen
        setContentView(R.layout.activity_main);

        // wiring GUI controls and matching Java objects
        txtColorSelected = findViewById(R.id.editText1);
        btnExit = findViewById(R.id.button1);
        txtSpyBox = findViewById(R.id.textView1);
        myScreen = findViewById(R.id.myScreen1);

        // EXIT button
        btnExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // observe text changes made to EditText
        txtColorSelected.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String chosenColor = s.toString().toLowerCase(Locale.US);

                txtSpyBox.setText(chosenColor);
                setBackgroundColor(chosenColor, myScreen);
            }
        });

        context = getApplicationContext();

        Toast.makeText(context, "onCreate", duration).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        updateMeUsingSavedStateData();

        Toast.makeText(context, "onStart", duration).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(context, "onResume", duration).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Toast.makeText(context, "onSaveInstanceState", duration).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        String chosenColor = txtSpyBox.getText().toString();
        saveStateData(chosenColor);

        Toast.makeText(context, "onPause", duration).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Toast.makeText(context, "onStop", duration).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Toast.makeText(context, "onRestart", duration).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Toast.makeText(context, "onDestroy", duration).show();
    }

    // Set background color
    private void setBackgroundColor(String chosenColor, LinearLayout myScreen) {

        if (chosenColor.contains("red")) {
            myScreen.setBackgroundColor(0xffff0000);
        }

        if (chosenColor.contains("green")) {
            myScreen.setBackgroundColor(0xff00ff00);
        }

        if (chosenColor.contains("blue")) {
            myScreen.setBackgroundColor(0xff0000ff);
        }

        if (chosenColor.contains("white")) {
            myScreen.setBackgroundColor(0xffffffff);
        }
    }

    // Save selected color
    private void saveStateData(String chosenColor) {

        SharedPreferences myPrefContainer =
                getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);

        SharedPreferences.Editor myPrefEditor =
                myPrefContainer.edit();

        String key = "chosenBackgroundColor";

        myPrefEditor.putString(key, chosenColor);
        myPrefEditor.apply();
    }

    // Restore selected color
    private void updateMeUsingSavedStateData() {

        SharedPreferences myPrefContainer =
                getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);

        String key = "chosenBackgroundColor";
        String defaultValue = "white";

        if (myPrefContainer.contains(key)) {

            String color =
                    myPrefContainer.getString(key, defaultValue);

            setBackgroundColor(color, myScreen);

            txtSpyBox.setText(color);
            txtColorSelected.setText(color);

        } else {

            setBackgroundColor(defaultValue, myScreen);
        }
    }
}
