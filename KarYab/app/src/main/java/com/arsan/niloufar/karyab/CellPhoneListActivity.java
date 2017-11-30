package com.arsan.niloufar.karyab;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.adad.client.Adad;

public class CellPhoneListActivity extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cell_phone_list, null);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Adad.initialize(getActivity());
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabGoToAddress);
        //final FloatingActionButton fabGoToCellPhone = (FloatingActionButton) view.findViewById(R.id.fabGoToCellPhone);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.getCellPhoneListProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        DBHandler db = new DBHandler(getActivity());
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        //List<IDS> ids = db.getAllIDSByName(IDNames.experienceID);
        List<CELLPHONE> phones = HttpMadad.getObjects(CELLPHONE[].class, UrlStatic.UrlOfGetHumanCellPhonesApi + "?humanid=" + humanID);
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.cellPhoneListScrollView);
        LinearLayout tempLayout = new LinearLayout(getActivity());
        tempLayout.setOrientation(LinearLayout.VERTICAL);
        //for(IDS id : ids){
        for (final CELLPHONE cellphone : phones) {
            //if(id.getData() != 0){
            //EXPERIENCE experience = HttpMadad.getObject(EXPERIENCE.class,UrlStatic.UrlOfGetExperienceApi + "?ID=" + id.getData()+"&HumanID="+humanID+"");
            //if (phone.EDUCATIONDURATION != null && education.EDUCATIONLOCATION != null) {
            //RadioButton tempRadioButton = new RadioButton(getActivity());
            //tempRadioButton.setText(String.valueOf(cellphone.CELLPHONENUMBER));
            //TextView tempTextView = new TextView(PhoneListActivity.this);
            //tempRadioButton.addView(tempRadioButton);
            TextView tempNameTextView = new TextView(getActivity());
            tempNameTextView.setText("شماره همراه: "+String.valueOf(cellphone.CELLPHONENUMBER));
            tempLayout.addView(tempNameTextView);
            Button editButton = new Button(getActivity());
            editButton.setText("ویرایش");
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent myIntent = new Intent(getActivity(), CellPhoneModifyActivity.class);
                    //getActivity().startActivity(myIntent);
                    Intent intent = new Intent(getActivity(),CellPhoneModifyActivity.class);
                    intent.putExtra("ID", cellphone.ID);
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
                    boolean finished = HttpMadad.postObjectAndGetBool(cellphone, UrlStatic.UrlOfDeleteHumanCellPhoneApi);
                    Toast.makeText(getActivity(), "حذف شد!", Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().detach(CellPhoneListActivity.this).attach(CellPhoneListActivity.this).commit();
                    //IDS.storeInDB(IDNames.skillID, ID, SkillActivity.this);
                    //progressBar.setVisibility(View.INVISIBLE);
                    //Intent intent = new Intent(getApplicationContext(), CellPhoneListActivity.class);
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
        progressBar.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getActivity(),CellPhoneActivity.class);
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
