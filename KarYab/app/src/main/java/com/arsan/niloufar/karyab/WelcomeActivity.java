package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Niloufar on 4/13/2016.
 */
public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText fullNameEditText = (EditText) findViewById(R.id.fullNameEditText);
        final RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        RadioButton maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);
        RadioButton femaleRadioButton = (RadioButton) findViewById(R.id.femaleRadioButton);
        final ToggleButton usageToggleButton = (ToggleButton) findViewById(R.id.UsageToggleButton);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSendHuman);
        final TextView humanID = (TextView) findViewById(R.id.humanIDtextView);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.asdprogressBar);
        DBHandler db = new DBHandler(this);
        db.deleteALLIDS();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                HUMAN human = new HUMAN();
                human.NAME = fullNameEditText.getText().toString();
                if (genderRadioGroup.getCheckedRadioButtonId() == R.id.maleRadioButton) {
                    human.GENDER = 1;
                } else {
                    human.GENDER = 0;
                }
                if (usageToggleButton.getText().equals("کارجو")) {
                    human.USAGE = 0;
                } else {
                    human.USAGE = 1;
                }
                Long result = HttpMadad.postObjectAndGetID(human,UrlStatic.UrlOfSettHumanApi);
                IDS.storeInDB(IDNames.humanID, result, WelcomeActivity.this);
                //Toast.makeText(WelcomeActivity.this,"HumanID: "+result,Toast.LENGTH_LONG).show();
                //progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(),PasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
