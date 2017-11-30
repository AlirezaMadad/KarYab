package com.arsan.niloufar.karyab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdressProfileActivity extends AppCompatActivity {

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_adress_profile);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

        final FloatingActionButton fabGoToCellPhone = (FloatingActionButton) findViewById(R.id.fabGoToAddress);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.getCellPhoneListProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        DBHandler db = new DBHandler(AdressProfileActivity.this);
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        //List<IDS> ids = db.getAllIDSByName(IDNames.experienceID);
        List<ADRESS> addresses = HttpMadad.getObjects(ADRESS[].class, UrlStatic.UrlOfGetHumanAddressesApi + "?humanid=" + humanID);
        ScrollView scrollView = (ScrollView) findViewById(R.id.addressListScrollView);
        LinearLayout tempLayout = new LinearLayout(AdressProfileActivity.this);
        tempLayout.setOrientation(LinearLayout.VERTICAL);
        //for(IDS id : ids){
        for (final ADRESS address : addresses) {
            //if(id.getData() != 0){
            //EXPERIENCE experience = HttpMadad.getObject(EXPERIENCE.class,UrlStatic.UrlOfGetExperienceApi + "?ID=" + id.getData()+"&HumanID="+humanID+"");
            //if (phone.EDUCATIONDURATION != null && education.EDUCATIONLOCATION != null) {
            //RadioButton tempRadioButton = new RadioButton(AdressListActivity.this);
            //tempRadioButton.setText(String.valueOf(address.ADRESS1));
            //TextView tempTextView = new TextView(PhoneListActivity.this);
            //tempLayout.addView(tempRadioButton);
            TextView tempNameTextView = new TextView(AdressProfileActivity.this);
            tempNameTextView.setText(address.ADRESS1);
            tempLayout.addView(tempNameTextView);
            Button editButton = new Button(AdressProfileActivity.this);
            editButton.setText("ویرایش");
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(AdressProfileActivity.this, AdressModifyActivity.class);
                    Toast.makeText(AdressProfileActivity.this, "" + address.ID + "", Toast.LENGTH_LONG).show();
                    myIntent.putExtra("ID", address.ID);
                    startActivity(myIntent);
                }
            });
            tempLayout.addView(editButton);
            Button deleteButton = new Button(AdressProfileActivity.this);
            deleteButton.setText("پاک کردن");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHandler db = new DBHandler(AdressProfileActivity.this);
                    IDS humanID = db.getIDbyName(IDNames.humanID);
                    boolean finished = HttpMadad.postObjectAndGetBool(address, UrlStatic.UrlOfDeleteHumanAddressApi);
                    Toast.makeText(AdressProfileActivity.this, "HumanID: " + humanID.getData() + " Finished: " + finished, Toast.LENGTH_LONG).show();
                    //IDS.storeInDB(IDNames.skillID, ID, SkillActivity.this);
                    //progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(getApplicationContext(), AdressListActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            tempLayout.addView(deleteButton);
            //tempLayout.removeView(tempTextView);
            //tempLayout.removeView(tempRadioButton);
            //tempLayout.removeView(tempRatingBar);
//            } else {
//                TextView temp2TextView = new TextView(EducationListActivity.this);
//                temp2TextView.setText("اطلاعات در سرور پیدا نشد!");
//                tempLayout.addView(temp2TextView);
            //tempLayout.removeView(temp2TextView);
//            }

        }
        //}
        //((ViewGroup)scrollView.getParent()).removeView(scrollView);
        //scrollView.removeView(tempLayout);
        scrollView.addView(tempLayout);
        //tempLayout.removeView(tempLayout);
        progressBar.setVisibility(View.INVISIBLE);

        fabGoToCellPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), AdressActivity.class);
                startActivity(intent);
                finish();
                //progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

}
