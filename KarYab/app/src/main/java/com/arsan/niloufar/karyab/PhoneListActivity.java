package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.adad.client.Adad;

public class PhoneListActivity extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_phone_list, null);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Adad.initialize(getActivity());
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabGoToCellPhone);
        //final FloatingActionButton fabGoToPhone = (FloatingActionButton) view.findViewById(R.id.fabGoToPhone);
        //final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.getPhoneListProgressBar);
        //progressBar.setVisibility(View.VISIBLE);
        DBHandler db = new DBHandler(getActivity());
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        //List<IDS> ids = db.getAllIDSByName(IDNames.experienceID);
        List<PHONE> phones = HttpMadad.getObjects(PHONE[].class, UrlStatic.UrlOfGetHumanPhonesApi + "?humanid=" + humanID);
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.phoneListScrollView);
        LinearLayout tempLayout = new LinearLayout(getActivity());
        tempLayout.setOrientation(LinearLayout.VERTICAL);
        //for(IDS id : ids){
        for (final PHONE phone : phones) {
            //if(id.getData() != 0){
            //EXPERIENCE experience = HttpMadad.getObject(EXPERIENCE.class,UrlStatic.UrlOfGetExperienceApi + "?ID=" + id.getData()+"&HumanID="+humanID+"");
            //if (phone.EDUCATIONDURATION != null && education.EDUCATIONLOCATION != null) {
                //RadioButton tempRadioButton = new RadioButton(getActivity());
                //tempRadioButton.setText(String.valueOf(phone.PHONENUMBER));
                //TextView tempTextView = new TextView(getActivity());
                //tempLayout.addView(tempRadioButton);
            TextView tempNameTextView = new TextView(getActivity());
            tempNameTextView.setText("تلفن: "+String.valueOf(phone.PHONENUMBER));
            tempLayout.addView(tempNameTextView);
            Button editButton = new Button(getActivity());
            editButton.setText("ویرایش");
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent myIntent = new Intent(getActivity(), PhoneModifyActivity.class);
                    Toast.makeText(getActivity(), "" + phone.ID + "", Toast.LENGTH_LONG).show();
                    //myIntent.putExtra("ID", phone.ID);
                    //getActivity().startActivity(myIntent);
                    Intent intent = new Intent(getActivity(),PhoneModifyActivity.class);
                    intent.putExtra("ID", phone.ID);
                    startActivityForResult(intent, 1);
                }
            });
            tempLayout.addView(editButton);
            Button deleteButton = new Button(getActivity());
            deleteButton.setText("پاک کردن");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHandler db = new DBHandler(getActivity());
                    IDS humanID = db.getIDbyName(IDNames.humanID);
                    boolean finished = HttpMadad.postObjectAndGetBool(phone, UrlStatic.UrlOfDeleteHumanMailPhoneApi);
                    Toast.makeText(getActivity(), "حذف شد!", Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().detach(PhoneListActivity.this).attach(PhoneListActivity.this).commit();
                    //IDS.storeInDB(IDNames.skillID, ID, SkillActivity.this);
                    //progressBar.setVisibility(View.INVISIBLE);
                    //Intent intent = new Intent(getApplicationContext(), PhoneListActivity.class);
                    //startActivity(intent);
                    //finish();
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
        //progressBar.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                //Intent intent = new Intent(getActivity(), PhoneActivity.class);
                //startActivity(intent);
                Intent intent = new Intent(getActivity(),PhoneActivity.class);
                startActivityForResult(intent, 1);
                //finish();
                //progressBar.setVisibility(View.INVISIBLE);

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                //Toast.makeText(getContext(),"fu swipe",Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onAc

}
