package com.arsan.niloufar.karyab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText Email = (EditText) findViewById(R.id.editTextEmail);
        final EditText Password = (EditText) findViewById(R.id.editTextPassword);
        Button Register = (Button) findViewById(R.id.registerButton);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Email.getText().toString().equals("") &&
                        !Password.getText().toString().equals("")){
                    PASSWORD password = new PASSWORD();
                    password.EMAIL = Email.getText().toString();
                    password.PASSWORD1 = Password.getText().toString();
                    //password.CONFIRMPASSWORD = ConfirmPassword.getText().toString();
                    //password.ANSWER = Answer.getText().toString();
                    //DBHandler db = new DBHandler(PasswordActivity.this);
                    //IDS humanID = db.getIDbyName(IDNames.humanID);
                    //password.HUMANID = humanID.getData();
                    long ID = HttpMadad.postObjectAndGetID(password, UrlStatic.UrlOfLoginApi+
                                    //"?HumanID="+password.HUMANID+
                                    "?Email="+password.EMAIL+
                                    "&Password="+password.PASSWORD1
                            //"&confirmPassword="+password.CONFIRMPASSWORD+

                            //"&answer="+password.ANSWER
                    );
                    if(ID != 0 && ID != -1 && ID != -2 && ID != -3){
                        IDS.storeInDB(IDNames.humanID, ID, LoginActivity.this);
                        DBHandler db = new DBHandler(LoginActivity.this);
                        IDS humanID = db.getIDbyName(IDNames.humanID);
                        //Toast.makeText(LoginActivity.this, "HumanID: " + humanID.getData() + " ID: " + ID, Toast.LENGTH_LONG).show();
                        //IDS.storeInDB(IDNames.AddressID, ID, AdressModifyActivity.this);
                        //progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"خطا در گرفتن اطلاعات از سرور", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "خطا در اطلاعات ورودی", Toast.LENGTH_LONG).show();
                }
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                startActivity(intent);
            }
        });


    }

}
