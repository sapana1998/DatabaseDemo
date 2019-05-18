package com.e.databasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name,marks,rollno;
    Button add,modify,delete,view,viewall,about;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connettoobject();
        operateingoodmood();
        db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,marks VARCHAR);");
    }

    private void operateingoodmood()
    {
        add.setOnClickListener(this);
        modify.setOnClickListener(this);
        delete.setOnClickListener(this);
        view.setOnClickListener(this);
        viewall.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    private void connettoobject() {
        name=findViewById(R.id.neditText);
        marks=findViewById(R.id.meditText);
        rollno=findViewById(R.id.reditText);
        add=findViewById(R.id.Add);
        modify=findViewById(R.id.Modify);
        delete=findViewById(R.id.Delete);
        view=findViewById(R.id.View);
        viewall=findViewById(R.id.ViewAll);
        about=findViewById(R.id.About);
        }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.Add:
                if(rollno.getText().toString().trim().length()==0||
                        name.getText().toString().trim().length()==0||
                        marks.getText().toString().trim().length()==0)
                {
                    Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                    showMessage("Error", "Please enter all values");
                    return;
                }
                db.execSQL("INSERT INTO student VALUES('"+rollno.getText()+"','"+name.getText()+
                        "','"+marks.getText()+"');");
                showMessage("Success", "Record added");
                clearText();

                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Modify:

                if(rollno.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+rollno.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("UPDATE student SET name='"+name.getText()+"',marks='"+marks.getText()+
                            "' WHERE rollno='"+rollno.getText()+"'");
                    showMessage("Success", "Record Modified");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
                Toast.makeText(this, "Modify", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Delete:
                if(rollno.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c2=db.rawQuery("SELECT * FROM student WHERE rollno='"+rollno.getText()+"'", null);
                if(c2.moveToFirst())
                {
                    db.execSQL("DELETE FROM student WHERE rollno='"+rollno.getText()+"'");
                    showMessage("Success", "Record Deleted");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                break;

            case R.id.View:
                if(rollno.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c3=db.rawQuery("SELECT * FROM student WHERE rollno='"+rollno.getText()+"'", null);
                if(c3.moveToFirst())
                {
                    name.setText(c3.getString(1));
                    marks.setText(c3.getString(2));
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }
                Toast.makeText(this, "View", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ViewAll:
                Cursor c4=db.rawQuery("SELECT * FROM student", null);
                if(c4.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c4.moveToNext())
                {
                    buffer.append("Rollno: "+c4.getString(0)+"\n");
                    buffer.append("Name: "+c4.getString(1)+"\n");
                    buffer.append("Marks: "+c4.getString(2)+"\n\n");
                }
                showMessage("Student Details", buffer.toString());
                Toast.makeText(this, "View All", Toast.LENGTH_SHORT).show();
                break;


            case R.id.About:
                showMessage("Student Record Application","Developed By Sapana Chandrakant Wabale");
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void clearText() {
        name.setText("");
        marks.setText("");
        rollno.setText("");
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
