package com.example.saajidh.solutionseeker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Registration extends AppCompatActivity {

    public DatabaseReference dbRef;


    public Button btn_submit;
    public EditText txt_name;
    public String name;
    public CheckBox year1;
    public CheckBox year2;
    public String attending_year;
    public EditText email;
    public String email_address;
    public Integer userID = 0;
    public EditText password;
    public String myPassword;
    private Spinner mySpinner1;
    private Spinner mySpinner2;
    private Spinner mySpinner3;
    private Spinner mySpinner4;
    private Spinner mySpinner5;
    private Spinner mySpinner6;
    private Spinner mySpinner7;
    private Spinner mySpinner8;
    private String subject1;
    private String subject2;
    private String subject3;
    private String subject4;
    private String subject5;
    private String subject6;
    private String subject7;
    private String subject8;
    ArrayList<String> sids;
    DatabaseReference UTableRef;
    private Integer maxsid=0;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        dbRef = FirebaseDatabase.getInstance().getReference();
        UTableRef = dbRef.child("Users");

        btn_submit = (Button) findViewById(R.id.button6);
        txt_name = (EditText) findViewById(R.id.editText2);
        year1 = (CheckBox) findViewById(R.id.checkBox2);
        year2 = (CheckBox) findViewById(R.id.checkBox4);
        email = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText4);
        mySpinner1 = (Spinner) findViewById(R.id.spinner);
        mySpinner2 = (Spinner) findViewById(R.id.spinner2);
        mySpinner3 = (Spinner) findViewById(R.id.spinner3);
        mySpinner4 = (Spinner) findViewById(R.id.spinner4);
        mySpinner5 = (Spinner) findViewById(R.id.spinner5);
        mySpinner6 = (Spinner) findViewById(R.id.spinner6);
        mySpinner7 = (Spinner) findViewById(R.id.spinner7);
        mySpinner8 = (Spinner) findViewById(R.id.spinner8);

        UTableRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {

                        sids = new ArrayList<>();

                        for (DataSnapshot dsp : dataSnapshot.getChildren()){
                            Map singleUser = (Map) dsp.getValue();
                            sids.add((String) singleUser.get("SID"));
                        }

                        for(int i=0;i<sids.size();i++)
                        {
                            String sid = sids.get(i);
                            int val = Integer.parseInt(sid);
                            if(val>maxsid)
                            {
                                maxsid = val;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        //if the 2nd year check box is selected, it will get unchecked.
        year1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                year2.setChecked(false);
                year1.setChecked(b);
            }
        });

        //if the 1st year check box is selected, it will get unchecked.
        year2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                year1.setChecked(false);
                year2.setChecked(b);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v) {

               userID = maxsid + 1;
               name = txt_name.getEditableText().toString();
               if(year1.isChecked())
               {
                   attending_year = "1st Year";
               }
               if (year2.isChecked())
               {
                   attending_year = "2nd Year";
               }
               email_address = email.getEditableText().toString();
               myPassword = password.getEditableText().toString();
               subject1 = mySpinner1.getSelectedItem().toString();
               subject2 = mySpinner2.getSelectedItem().toString();
               subject3 = mySpinner3.getSelectedItem().toString();
               subject4 = mySpinner4.getSelectedItem().toString();
               subject5 = mySpinner5.getSelectedItem().toString();
               subject6 = mySpinner6.getSelectedItem().toString();
               subject7 = mySpinner7.getSelectedItem().toString();
               subject8 = mySpinner8.getSelectedItem().toString();


               dbRef.child("Users");
               DatabaseReference dbChild1 = dbRef.child("Users");
               dbChild1.child(userID.toString());
               key = userID.toString();
               DatabaseReference dbchild_child = dbChild1.child(key);
               dbchild_child.child("SID").setValue(key);
               dbchild_child.child("Name").setValue(name);
               dbchild_child.child("Attending Year").setValue(attending_year);
               dbchild_child.child("Email Address").setValue(email_address);
               dbchild_child.child("Password").setValue(myPassword);
               dbchild_child.child("DCCN-I").setValue(subject1);
               dbchild_child.child("IPE").setValue(subject2);
               dbchild_child.child("MIT").setValue(subject3);
               dbchild_child.child("CF").setValue(subject4);
               dbchild_child.child("ST-I").setValue(subject5);
               dbchild_child.child("DBMS-I").setValue(subject6);
               dbchild_child.child("ITA").setValue(subject7);
               dbchild_child.child("FCS").setValue(subject8);

               Intent i = new Intent(Registration.this, MainActivity.class);
               i.putExtra("stdid", key);
               Registration.this.startActivity(i);
               Toast.makeText(Registration.this, "Profile is successfully created.", Toast.LENGTH_SHORT).show();

           }
        });


        Spinner subjectSpinner1 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner1.setAdapter(myAdapter1);

        Spinner subjectSpinner2 = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner2 .setAdapter(myAdapter2);

        Spinner subjectSpinner3 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner3 .setAdapter(myAdapter3);

        Spinner subjectSpinner4 = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner4 .setAdapter(myAdapter4);

        Spinner subjectSpinner5 = (Spinner) findViewById(R.id.spinner5);
        ArrayAdapter<String> myAdapter5 = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner5 .setAdapter(myAdapter5);

        Spinner subjectSpinner6 = (Spinner) findViewById(R.id.spinner6);
        ArrayAdapter<String> myAdapter6 = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner6 .setAdapter(myAdapter6);

        Spinner subjectSpinner7 = (Spinner) findViewById(R.id.spinner7);
        ArrayAdapter<String> myAdapter7 = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner7 .setAdapter(myAdapter7);

        Spinner subjectSpinner8 = (Spinner) findViewById(R.id.spinner8);
        ArrayAdapter<String> myAdapter8 = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.subjects));
        myAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner8 .setAdapter(myAdapter8);
    }

}
