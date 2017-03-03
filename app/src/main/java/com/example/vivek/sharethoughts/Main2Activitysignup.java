package com.example.vivek.sharethoughts;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activitysignup extends AppCompatActivity {
    DataBaseHelper helper = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_activitysignup);
    }

    public void onclick(View v) {
        if (v.getId() == R.id.Rsignup) {
            EditText name = (EditText) findViewById(R.id.Rnme);
            EditText email = (EditText) findViewById(R.id.Remail);
            EditText uname = (EditText) findViewById(R.id.Runme);
            EditText pass = (EditText) findViewById(R.id.Rpass);
            EditText cnfpass = (EditText) findViewById(R.id.Rcnfpass);

            String namestr = name.getText().toString();
            String emailstr = email.getText().toString();
            String unamestr = uname.getText().toString();
            String passstr = pass.getText().toString();
            String cnfpassstr = cnfpass.getText().toString();


            if (pass.getText().toString().length() == 0 || name.getText().toString().length() == 0 ||
                    email.getText().toString().length() == 0 || uname.getText().toString().length() == 0 ||
                    cnfpass.getText().toString().length() == 0 || !passstr.equals(cnfpassstr))
            {
                pass.setError("Enter details ");
                name.setError("Enter details ");
                email.setError("Enter details ");
                uname.setError("Enter details ");
                cnfpass.setError("Enter details ");
                pass.setError("password does not match");

            } else {
                //insert details db
                contact c = new contact();
                c.setEmail(emailstr);
                c.setName(namestr);
                c.setUname(unamestr);
                c.setPass(passstr);
                helper.insertcontact(c);
                Toast.makeText(Main2Activitysignup.this, "Sucess", Toast.LENGTH_LONG).show();
                Intent inte = new Intent(Main2Activitysignup.this, MainActivity.class);
                startActivity(inte);

            }

        }
    }
}


