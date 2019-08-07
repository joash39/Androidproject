package com.example.mysqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText myName, myEmail ,myID;
    Button saveBtn, viewBtn, deleteBtn;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myName = findViewById(R.id.edtname);
        myEmail = findViewById(R.id.edtemail);
        myID = findViewById(R.id.edtnumber);
        saveBtn = findViewById(R.id.btnsave);
        viewBtn = findViewById(R.id.btnview);
        deleteBtn = findViewById(R.id.btndelete);

//        Create the database
        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

//        Create a table in your db
        db.execSQL("CREATE TABLE IF NOT EXISTS majina(name VARCHAR, email VARCHAR,id_number VARCHAR)");

//        Proceed to save data after the user has clicked on the button save
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Receive data input from the user
                String jina = myName.getText().toString();
                String arafa = myEmail.getText().toString();
                String kitambulisho = myID.getText().toString();

//                Check if the user has submitted empty fields
                if (jina.isEmpty() || arafa.isEmpty() || kitambulisho.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fill in all inputs", Toast.LENGTH_SHORT).show();
                }else {
//                    Proceed to save data
                    db.execSQL("INSERT INTO majina VALUES('"+jina+"','"+arafa+"','"+kitambulisho+"')");
                    Toast.makeText(MainActivity.this, "User Saved Successfully", Toast.LENGTH_SHORT).show();

//                    clear the fields for the next entry
                    myName.setText("");
                    myEmail.setText("");
                    myID.setText("");

                }

            }
        });
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.rawQuery("SELECT * FROM majina",null);

//                Check if there is any record
                if (cursor.getCount()==0){
                    messages("No RECORDS","Sorry, No records");
                }
//                Use Buffer to apend the records
                StringBuffer buffer = new StringBuffer();

                while (cursor.moveToNext()){
                    buffer.append("Name is:"+cursor.getString(0));
                    buffer.append("\n");
                    buffer.append("Email is:"+cursor.getString(1));
                    buffer.append("\n");
                    buffer.append("ID is:"+cursor.getString(2));
                    buffer.append("\n");
                }
                messages("DATABASE RECORDS ", buffer.toString());
            }
        });
        //Deleting
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myID.getText().toString().trim().isEmpty()){
                    messages("Empty ID","Kindly type in an ID..");
                }
                Cursor cursor = db.rawQuery("SELECT * FROM majina WHERE id_number = '"+myID.getText().toString().trim()+"'", null);
                if (cursor.moveToFirst()){
                    db.execSQL("DELETE FROM majina WHERE id_number = '"+myID.getText().toString()+"'");

                    messages("DELETED","Record Deleted Succesfully");
                    myID.setText("");

                }            }
        });
    }


    public void messages(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.create().show();


    }
}
