package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import java.util.List;

import ir.adad.client.Adad;

public class AdressListActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_adress_list, null);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        //final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.getCellPhoneListProgressBar);
        //progressBar.setVisibility(View.VISIBLE);
        Adad.initialize(getActivity());
        DBHandler db = new DBHandler(getActivity());
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        List<ADRESS> addresses = HttpMadad.getObjects(ADRESS[].class, UrlStatic.UrlOfGetHumanAddressesApi + "?humanid=" + humanID);
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.addressListScrollView);
        LinearLayout tempLayout = new LinearLayout(getActivity());
        tempLayout.setOrientation(LinearLayout.VERTICAL);
        for (final ADRESS address : addresses) {
            TextView tempNameTextView = new TextView(getActivity());
            tempNameTextView.setText("نام شهر: "+address.ADRESS1);
            tempLayout.addView(tempNameTextView);
            Button editButton = new Button(getActivity());
            editButton.setText("ویرایش");
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(getActivity(),AdressModifyActivity.class);
                    intent.putExtra("ID", address.ID);
                    startActivityForResult(intent,1);

                    //Intent myIntent = new Intent(getActivity(), AdressModifyActivity.class);
                    //Toast.makeText(getActivity(),""+address.ID+"" , Toast.LENGTH_LONG).show();

                    //getActivity().startActivity(myIntent);
                }
            });
            tempLayout.addView(editButton);
            Button deleteButton = new Button(getActivity());
            deleteButton.setText("پاک کردن");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //progressBar.setVisibility(View.INVISIBLE);
                    DBHandler db = new DBHandler(getActivity());
                    IDS humanID = db.getIDbyName(IDNames.humanID);
                    boolean finished = HttpMadad.postObjectAndGetBool(address, UrlStatic.UrlOfDeleteHumanAddressApi);
                    Toast.makeText(getActivity(), "حذف شد!", Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().detach(AdressListActivity.this).attach(AdressListActivity.this).commit();

                }
            });
            tempLayout.addView(deleteButton);
        }
        scrollView.addView(tempLayout);
        //progressBar.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.INVISIBLE);
                //Intent intent = new Intent(getActivity(),AdressActivity.class);
                //startActivity(intent);
                Intent intent = new Intent(getActivity(),AdressActivity.class);
                startActivityForResult(intent, 1);

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
