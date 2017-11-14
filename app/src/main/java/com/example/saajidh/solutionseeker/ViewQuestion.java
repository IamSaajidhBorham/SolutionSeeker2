package com.example.saajidh.solutionseeker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ViewQuestion extends AppCompatActivity {

    String qid;

    DatabaseReference dbRef;
    DatabaseReference QTableRef;
    DatabaseReference QRef;
    DatabaseReference RTableRef;

    String Question;
    ArrayList<String> RepliedQuestionIDs;
    ArrayList<String> Replies;
    ArrayList<String> ReplyRates;
    ArrayList<String> Answers;
    ArrayList<String> Ratings;
    ArrayAdapter adapter;
    AlertDialog.Builder popDialog;
    RatingBar TheRate;

    TextView TheQuestioin;
    ListView AnswerList;

    public void ShowDialog(String rate)
    {
        popDialog = new AlertDialog.Builder(this);
        TheRate = new RatingBar(this, null, android.R.attr.ratingBarStyleIndicator);
        TheRate.setNumStars(9);
        TheRate.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
        TheRate.setMax(0);
        TheRate.setStepSize((float) 1.0);
        TheRate.setIsIndicator(false);
        TheRate.setRating(Float.parseFloat(rate));

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Rate!");
        popDialog.setView(TheRate);

        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });

        popDialog.create();
        popDialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);

        qid = getIntent().getExtras().getString("QID");

        dbRef = FirebaseDatabase.getInstance().getReference();
        RTableRef = dbRef.child("Replies");
        QTableRef = dbRef.child("Question");
        QRef = QTableRef.child(qid);

        TheQuestioin = (TextView) findViewById(R.id.txtviewQuestion);
        AnswerList = (ListView) findViewById(R.id.listviewAnswers);

        QRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map test = (Map) dataSnapshot.getValue();
                Question = (String) test.get("Question");
                TheQuestioin.setText(Question);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        RTableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                RepliedQuestionIDs = new ArrayList<String>();
                Replies = new ArrayList<String>();
                ReplyRates = new ArrayList<String>();
                Answers = new ArrayList<String>();
                Ratings = new ArrayList<String>();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Map test = (Map) dsp.getValue();
                    RepliedQuestionIDs.add((String) test.get("QID"));
                }

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Map test = (Map) dsp.getValue();
                    Replies.add((String) test.get("Reply"));
                }

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Map test = (Map) dsp.getValue();
                    ReplyRates.add((String) test.get("Rating"));
                }

                for(int i=0; i<RepliedQuestionIDs.size();i++)
                {
                    String id = RepliedQuestionIDs.get(i);
                    if(id.equals(qid))
                    {
                        Answers.add(Replies.get(i));
                        Ratings.add(ReplyRates.get(i));
                    }
                }

                adapter = new ArrayAdapter(ViewQuestion.this, android.R.layout.simple_list_item_1, Answers);
                AnswerList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        AnswerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String rating=Ratings.get(position);
                ShowDialog(rating);
            }
        });
    }
}
