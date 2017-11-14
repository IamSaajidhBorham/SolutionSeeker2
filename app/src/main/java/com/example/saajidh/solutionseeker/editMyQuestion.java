package com.example.saajidh.solutionseeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editMyQuestion extends AppCompatActivity {

    public DatabaseReference dbRef;
    public DatabaseReference QTableRef;
    public DatabaseReference Question;

    String myQuestion;
    String myQID;

    EditText txtMyQuestion;
    Button btnEditMyQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_question);

        myQuestion = getIntent().getExtras().getString("thequestion");
        myQID = getIntent().getExtras().getString("theqid");

        txtMyQuestion = (EditText) findViewById(R.id.myQuestion);
        txtMyQuestion.setText(myQuestion);

        btnEditMyQuestion = (Button) findViewById(R.id.btnDoneEditingMyQuestion);

        dbRef = FirebaseDatabase.getInstance().getReference();
        QTableRef = dbRef.child("Question");
        Question = QTableRef.child(myQID);
        btnEditMyQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question.child("Question").setValue(txtMyQuestion.getText());
                finish();
            }
        });


    }
}
