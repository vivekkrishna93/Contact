package com.example.vivek.sharethoughts;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Feed extends AppCompatActivity implements View.OnClickListener {
    EditText editPhone,editName,editCompany,editRole;
    Button btnAdd,btnDelete,btnModify,btnView,btnViewAll;
    TextView tv1,tv2;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Bundle bundle=getIntent().getExtras();
        String messaag=bundle.getString("username");
        tv1 =(TextView)findViewById(R.id.tv2);
        tv1.setText(messaag);

        editRole=(EditText)findViewById(R.id.editRole);
        editPhone=(EditText)findViewById(R.id.editPhone);
        editName=(EditText)findViewById(R.id.editName);
        editCompany=(EditText)findViewById(R.id.editCompany);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnModify=(Button)findViewById(R.id.btnModify);
        btnView=(Button)findViewById(R.id.btnView);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);

        db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS cont(phone VARCHAR,name VARCHAR,company VARCHAR,role VARCHAR,admin VARCHAR);");

    }

    public void onClick(View view)
    {
       tv2 =(TextView)findViewById(R.id.tv2);

        if(view==btnAdd)
        {
            if(editPhone.getText().toString().trim().length()==0||
                    editName.getText().toString().trim().length()==0||
                    editCompany.getText().toString().trim().length()==0||
                    editRole.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter all values");
                return;
            }
            //insert admin value here
            db.execSQL("INSERT INTO cont VALUES('"+editPhone.getText()+"','"+editName.getText()+
                    "','"+editCompany.getText()+" ',' " +editRole.getText()+"',' "+tv2.getText()+"');");
            showMessage("Success", "Contact added");
            clearText();
        }
        if(view==btnDelete)
        {
            if(editName.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Name");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM cont WHERE name='"+editName.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM cont WHERE name='"+editName.getText()+"'");
                showMessage("Success", "Record Deleted");
            }
            else
            {
                showMessage("Error", "Invalid Name");
            }
            clearText();
        }
        if(view==btnModify)
        {
            if(editName.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Name");
                return;
            }

            Cursor c=db.rawQuery("SELECT * FROM cont WHERE name='"+editName.getText()+"' \"' OR admin =' \"+tv2.getText()+\"' ", null);
            if(c.moveToLast())
            {
                db.execSQL("UPDATE cont SET phone='"+editPhone.getText()+"',company ='"+editCompany.getText()+"',role='"+ editRole.getText()+"'WHERE name ='"+editName.getText()+"'");
                showMessage("Success", "Record Modified");
            }
            else
            {
                showMessage("Error", "Invalid Name");
            }
            clearText();
        }
        if(view==btnView)
        {
            if(editName.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Name");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM cont WHERE name='"+editName.getText()+"' AND admin =' "+tv2.getText()+"' ", null);

            if(c.moveToLast())
            {
                editPhone.setText(c.getString(0));
                editName.setText(c.getString(1));
                editCompany.setText(c.getString(2));
                editRole.setText(c.getString(3));
            }
            else
            {
                showMessage("Error", "Invalid Name");
                clearText();
            }
        }
        if(view==btnViewAll)
        {
            Cursor c=db.rawQuery("SELECT * FROM cont WHERE admin =' "+tv2.getText()+"' ", null);
            if(c.getCount()==0)
            {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("Phone: "+c.getString(0)+"\n");
                buffer.append("Name: "+c.getString(1)+"\n");
                buffer.append("Company: "+c.getString(2)+"\n");
                buffer.append("Role: "+c.getString(3)+"\n\n");


            }
            showMessage("Contact Info", buffer.toString());
        }

    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        editPhone.setText("");
        editName.setText("");
        editCompany.setText("");
        editRole.setText("");
        editPhone.requestFocus();
    }
}
