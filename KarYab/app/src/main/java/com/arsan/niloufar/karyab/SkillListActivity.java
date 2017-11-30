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
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import ir.adad.client.Adad;

/**
 * Created by Niloufar on 4/19/2016.
 */
public class SkillListActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_skilllist, null);
        Adad.initialize(getActivity());
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Adad.initialize(getActivity());
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddSkill);
        DBHandler db = new DBHandler(getActivity());
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        List<SKILL> skills = HttpMadad.getObjects(SKILL[].class, UrlStatic.UrlOfGetHumanSkillsApi + "?humanid=" + humanID);
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.skillListScrollView);
        final LinearLayout tempLayout = new LinearLayout(getActivity());
        tempLayout.setOrientation(LinearLayout.VERTICAL);
        for (final SKILL skill : skills) {
            if (skill.SKILLNAME != null && skill.SKILLEFFICENYID != null && skill.SKILLDESCRIPTION != null) {
                TextView tempNameTextView = new TextView(getActivity());
                tempNameTextView.setText("نام مهارت: "+skill.SKILLNAME);
                RatingBar tempRatingBar = new RatingBar(getActivity());
                tempRatingBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                tempRatingBar.setRating((float) skill.SKILLEFFICENYID);
                tempRatingBar.setStepSize((float) 1);
                tempRatingBar.setNumStars(3);
                tempRatingBar.setMax(3);
                tempRatingBar.setIsIndicator(true);
                TextView tempTextView = new TextView(getActivity());
                tempTextView.setText("توضیحات مهارت: "+skill.SKILLDESCRIPTION);
                tempLayout.addView(tempNameTextView);
                tempLayout.addView(tempRatingBar);
                tempLayout.addView(tempTextView);
                Button editButton = new Button(getActivity());
                editButton.setText("ویرایش");
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(getActivity(), SkillModifyActivity.class);
                        myIntent.putExtra("ID", skill.ID);
                        getActivity().startActivity(myIntent);
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
                        boolean finished = HttpMadad.postObjectAndGetBool(skill, UrlStatic.UrlOfDeleteHumanSkillApi);
                        Toast.makeText(getActivity(), "حذف شد!", Toast.LENGTH_LONG).show();
                        getFragmentManager().beginTransaction().detach(SkillListActivity.this).attach(SkillListActivity.this).commit();
                    }
                });

                tempLayout.addView(deleteButton);

            } else {
                TextView temp2TextView = new TextView(getActivity());
                temp2TextView.setText("اطلاعات در سرور پیدا نشد!");
                tempLayout.addView(temp2TextView);
            }

        }
        scrollView.addView(tempLayout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SkillActivity.class);
                startActivityForResult(intent,1);

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
