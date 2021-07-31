package com.example.dobcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    TextView alreadyhaveaccount;
    EditText inputEmail,inputPassword,inputConfirmPassword;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    DBHelper myDB;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        alreadyhaveaccount =findViewById(R.id.alreadyhaveaccount);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword =findViewById(R.id.inputpass);
        inputConfirmPassword=findViewById(R.id.inputconfirmpass);
        btnRegister=findViewById(R.id.btnreg);


        myDB = new DBHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String confirmpassword = inputConfirmPassword.getText().toString();

                if(!email.matches(emailPattern)){
                    inputEmail.setError("Enter valid Email");
                }else if(password.isEmpty()|| password.length()<6){
                    inputPassword.setError("Enter correct Password");
                }else if(!password.equals(confirmpassword)){
                    inputConfirmPassword.setError("Entered password should match");
                }else{
                    boolean usercheckresult=  myDB.checkusername(email);
                    if(usercheckresult==false){
                        Boolean regResult=myDB.insertData(email,password);
                        if(regResult==true){
                            Toast.makeText(RegisterActivity.this,"Registration Successful.",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this,"Registration Unsuccessful.",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"User already exists\n Please Login",Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });
        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });


        getSupportActionBar().hide();
    }


}