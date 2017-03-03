package com.example.vivek.sharethoughts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper helper = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showme(View v) {
        if (v.getId() == R.id.Login) {
            EditText et1 = (EditText) findViewById(R.id.Uname);
            String str = et1.getText().toString();

            EditText et2 = (EditText) findViewById(R.id.Pass);
            String pass = et2.getText().toString();

            String password = helper.searchpass(str);

            //modification validation


            if (et1.getText().toString().length() == 0 || et2.getText().toString().length() == 0) {

                et1.setError("Enter details");
                et2.setError("Enter details");

            }
            if (!password.equals(pass))

            {
                Toast.makeText(MainActivity.this, "Not valid login", Toast.LENGTH_LONG).show();
            } else {
                Intent inte = new Intent(MainActivity.this, Feed.class);
                startActivity(inte);

            }


        }
        if (v.getId() == R.id.Lsignup) {
            Intent inte = new Intent(MainActivity.this, Main2Activitysignup.class);
            startActivity(inte);

        }
    }
}
