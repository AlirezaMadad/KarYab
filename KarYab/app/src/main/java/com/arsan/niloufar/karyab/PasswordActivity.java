package com.arsan.niloufar.karyab;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText Email = (EditText) findViewById(R.id.editTextEmail);
        final EditText Password = (EditText) findViewById(R.id.editTextPassword);
        final EditText ConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        final EditText Answer = (EditText) findViewById(R.id.editTextAnswer);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Email.getText().toString().equals("") &&
                        !Password.getText().toString().equals("") &&
                        !ConfirmPassword.getText().toString().equals("") &&
                        !Answer.getText().toString().equals("") &&
                        Password.getText().toString().equals(ConfirmPassword.getText().toString())){
                    PASSWORD password = new PASSWORD();
                    password.EMAIL = Email.getText().toString();
                    password.PASSWORD1 = Password.getText().toString();
                    password.CONFIRMPASSWORD = ConfirmPassword.getText().toString();
                    password.ANSWER = Answer.getText().toString();
                    DBHandler db = new DBHandler(PasswordActivity.this);
                    IDS humanID = db.getIDbyName(IDNames.humanID);
                    password.HUMANID = humanID.getData();
                    //String temp = UrlStatic.UrlOfSetPasswordApi+
                    //        "?HumanID="+password.HUMANID+
                    //       "&password="+password.PASSWORD1+
                    //        "&confirmPassword="+password.CONFIRMPASSWORD+
                    //        "&email="+password.EMAIL+
                    //        "&answer="+password.ANSWER;
                    long ID = HttpMadad.postObjectAndGetID(password, UrlStatic.UrlOfSetPasswordApi//+
                                    //"?HumanID="+password.HUMANID+
                                    //"&password="+password.PASSWORD1+
                                    //"&confirmPassword="+password.CONFIRMPASSWORD+
                                    //"&email="+password.EMAIL+
                                    //"&answer="+password.ANSWER
                    );
                    if(ID != 0 && ID != -1 && ID != -2){
                        IDS.storeInDB(IDNames.passwordID, ID, PasswordActivity.this);
                        Toast.makeText(PasswordActivity.this, "HumanID: " + humanID.getData() + " ID: " + ID, Toast.LENGTH_LONG).show();
                        //IDS.storeInDB(IDNames.AddressID, ID, AdressModifyActivity.this);
                        //progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(PasswordActivity.this, "خطا در اطلاعات ورودی", Toast.LENGTH_LONG).show();
                    }


                }
                else{
                    Toast.makeText(PasswordActivity.this, "خطا در اطلاعات ورودی", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
