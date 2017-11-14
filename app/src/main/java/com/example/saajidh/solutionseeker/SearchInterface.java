package com.example.saajidh.solutionseeker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class SearchInterface extends AppCompatActivity {

    DatabaseReference dbRef;
    DatabaseReference QTableRef;
    DatabaseReference UTableRef;
    Spinner Searching;
    Spinner SearchingBy;
    Button SearchClick;
    Integer search;
    ListView SearchList;
    ArrayList<String> QuestionList;
    ArrayList<String> SearchedQuestions;
    ArrayList<String> QuestionIDs;
    ArrayList<String> SearchedQuestionIDs;
    ArrayList<String> UserList;
    ArrayList<String> CategoryList;
    ArrayList<String> AttendingYearList;
    ArrayList<String> SearchedUsers;
    ArrayList<String> UserIDs;
    ArrayList<String> SearchedUserIDs;
    String Category;
    String Year;
    ArrayAdapter<String> adapter;
    Button ItemClick;
    Boolean isItemClicked;
    String ClickedItem;
    String StudentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_interface);

        StudentID = getIntent().getExtras().getString("stdid");

        dbRef = FirebaseDatabase.getInstance().getReference();
        QTableRef = dbRef.child("Question");
        UTableRef = dbRef.child("Users");

        SearchClick = (Button) findViewById(R.id.btnSearchClick);
        SearchList = (ListView) findViewById(R.id.listviewSearch);
        ItemClick = (Button) findViewById(R.id.btnItemClick);

        isItemClicked = false;

        Searching = (Spinner) findViewById(R.id.spinnerSearching);
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(SearchInterface.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.searching));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Searching.setAdapter(myAdapter1);

        SearchingBy = (Spinner) findViewById(R.id.spinnerSearchBy);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(SearchInterface.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.nothing));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SearchingBy.setAdapter(myAdapter2);

        Searching.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    SearchList.setAdapter(null);
                    SearchingBy = (Spinner) findViewById(R.id.spinnerSearchBy);
                    ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(SearchInterface.this,
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.category));
                    myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SearchingBy.setAdapter(myAdapter2);
                    search = 0;
                }
                else
                {
                    SearchList.setAdapter(null);
                    SearchingBy = (Spinner) findViewById(R.id.spinnerSearchBy);
                    ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(SearchInterface.this,
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.year));
                    myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SearchingBy.setAdapter(myAdapter2);
                    search = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SearchClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isItemClicked = false;
                if(search==0)
                {
                    Category = SearchingBy.getSelectedItem().toString();
                    QTableRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            QuestionList = new ArrayList<String>();
                            CategoryList = new ArrayList<String>();
                            SearchedQuestions = new ArrayList<String>();
                            QuestionIDs = new ArrayList<String>();
                            SearchedQuestionIDs = new ArrayList<String>();
                            Map test1;

                            // Result will be holded Here
                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                test1 = (Map) dsp.getValue();
                                CategoryList.add((String) test1.get("Category"));
                            }

                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                test1 = (Map) dsp.getValue();
                                QuestionList.add((String) test1.get("Question"));
                            }

                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                test1 = (Map) dsp.getValue();
                                QuestionIDs.add((String) test1.get("QID"));
                            }

                            for(int i=0; i<CategoryList.size(); i++)
                            {
                                if(CategoryList.get(i).equals(Category))
                                {
                                    SearchedQuestions.add(QuestionList.get(i).replaceAll("[;]",","));
                                    SearchedQuestionIDs.add(QuestionIDs.get(i));

                                    adapter = new ArrayAdapter (SearchInterface.this, android.R.layout.simple_list_item_1, SearchedQuestions);
                                    SearchList.setAdapter(adapter);
                                }
                            }
                            if(SearchedQuestions.size()==0)
                            {
                                Toast.makeText(SearchInterface.this, "There are no Questions!",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else
                {
                    Year = SearchingBy.getSelectedItem().toString();
                    UTableRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            UserList = new ArrayList<String>();
                            AttendingYearList = new ArrayList<String>();
                            SearchedUsers = new ArrayList<String>();
                            UserIDs = new ArrayList<String>();
                            SearchedUserIDs = new ArrayList<String>();
                            Map test1;

                            // Result will be holded Here
                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                test1 = (Map) dsp.getValue();
                                AttendingYearList.add((String) test1.get("Attending Year"));
                            }

                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                test1 = (Map) dsp.getValue();
                                UserList.add((String) test1.get("Name"));
                            }

                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                test1 = (Map) dsp.getValue();
                                UserIDs.add((String) test1.get("SID"));
                            }

                            for(int i=0; i<AttendingYearList.size(); i++)
                            {
                                if(AttendingYearList.get(i).equals(Year))
                                {
                                    SearchedUsers.add(UserList.get(i));
                                    SearchedUserIDs.add(UserIDs.get(i));

                                    adapter = new ArrayAdapter (SearchInterface.this, android.R.layout.simple_list_item_1, SearchedUsers);
                                    SearchList.setAdapter(adapter);

                                }
                            }

                            if(SearchedUsers.size()==0)
                            {
                                Toast.makeText(SearchInterface.this, "There are no users!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        SearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                isItemClicked = true;
                String searching = Searching.getSelectedItem().toString();
                if(searching.equals("Question"))
                {
                    ClickedItem = SearchedQuestionIDs.get(position);
                }
                if(searching.equals("Student"))
                {
                    ClickedItem = SearchedUserIDs.get(position);
                }
            }
        });

        ItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isItemClicked==true)
                {
                    String searching = Searching.getSelectedItem().toString();

                    if(searching.equals("Question"))
                    {
                        Intent i = new Intent(SearchInterface.this, ViewQuestion.class);
                        i.putExtra("QID",ClickedItem);
                        startActivity(i);
                    }
                    else if(searching.equals("Student"))
                    {
                        Intent i = new Intent(SearchInterface.this, ViewUser.class);
                        i.putExtra("SID",ClickedItem);
                        i.putExtra("UserID",StudentID);
                        startActivity(i);
                    }
                }
                else
                {
                    Toast.makeText(SearchInterface.this, "Please click an item first!",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
