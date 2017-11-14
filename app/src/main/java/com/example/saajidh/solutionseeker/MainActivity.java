package com.example.saajidh.solutionseeker;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";


    public static
    PlaceholderFragment fragment_obj;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_obj.sid = getIntent().getExtras().getString("stdid");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Log.d(TAG, "onCreate: Started.");
    }

    public void myFancyMethod(View v) {
        fragment_obj = (PlaceholderFragment)getSupportFragmentManager().findFragmentById(R.id.container);

        Intent i = new Intent(MainActivity.this, addPost.class);
        i.putExtra("stdid",fragment_obj.sid);
        addPost.fromMainActivity=true;
        addPost.fromViewUser=false;
        MainActivity.this.startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        Button dbtn;
        ListView myList1;
        ListView askedlist;
        ListView replylist;
        Button mySearch;
        private FirebaseDatabase firebasedb;
        public DatabaseReference myRef;
        DatabaseReference questionRef;
        ArrayAdapter adapter;
        ArrayAdapter adapter1;
        ArrayAdapter adapter2;
        DatabaseReference UTableRef;
        DatabaseReference QTableRef;
        ArrayList<String> Names;
        ArrayList<String> Questions;
        public static String sid;
        ArrayList<String> askedquestionlist;
        ArrayList<String> sendingtolist;
        ArrayList<String> sendingarray;
        ArrayList<String> askedarray;
        ArrayList<String> idsofaskedarray;
        String itemclick1;
        String qid;
        ArrayList<String> askedids;
        ArrayList<String> sidset;
        ArrayList<String> questionset;
        ArrayList<String> finalquestionset;
        ArrayList<String> idsofquestions;
        ArrayList<String> finalidset;
        DatabaseReference uinforef;
        ArrayList<String> uinfolist;
        TextView uname;
        TextView uattendingyear;
        TextView uemai;
        TextView udccn1;
        TextView uipe;
        TextView umit;
        TextView ucf;
        TextView ust1;
        TextView udbms1;
        TextView uita;
        TextView ufcs;
        Button changepwd;
        Button editprofile;
        ArrayList<String> filtersearch;
        ArrayList<String> questionids;
        ArrayList<String> realquestions;
        ArrayList<String> keylist;


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            if( getArguments().getInt(ARG_SECTION_NUMBER) == 1)
            {
                View rootView = inflater.inflate(R.layout.fragment_home2, container, false);
                dbtn = (Button) rootView.findViewById(R.id.dummy);
                myList1 = (ListView) rootView.findViewById(R.id.listView);
                mySearch = (Button) rootView.findViewById(R.id.btnSearch);

                filtersearch = new ArrayList<>();

                firebasedb = FirebaseDatabase.getInstance();
                myRef = firebasedb.getReference();
                UTableRef = myRef.child("Users");
                QTableRef = myRef.child("Question");

                QTableRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Get map of users in datasnapshot
                                Questions = new ArrayList<String>();
                                Names = new ArrayList<String>();
                                questionids = new ArrayList<String>();

                                // Result will be holded Here
                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    Map test1 = (Map) dsp.getValue();
                                    questionids.add((String) test1.get("QID"));
                                }

                                realquestions = new ArrayList<>();
                                for(int f=0;f<questionids.size();f++)
                                {
                                    String value=questionids.get(f);
                                    questionRef = myRef.child("Question").child(value).child("Question");
                                    questionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            String bitch=dataSnapshot.getValue(String.class);
                                            realquestions.add(bitch.replaceAll("[;]",","));
                                            adapter = new ArrayAdapter (getActivity(), android.R.layout.simple_list_item_1, realquestions);
                                            myList1.setAdapter(adapter);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                                Toast.makeText(getActivity(), databaseError.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });

                mySearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), SearchInterface.class);
                        i.putExtra("stdid",fragment_obj.sid);
                        startActivity(i);
                    }
                });

                myList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String qid = questionids.get(position);
                        Intent i = new Intent(getActivity(), ViewQuestion.class);
                        i.putExtra("QID",qid);
                        startActivity(i);
                    }
                });

                return rootView;
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2)
            {
                View rootView = inflater.inflate(R.layout.fragment_message2, container, false);
                askedlist = (ListView) rootView.findViewById(R.id.askedlist);
                replylist = (ListView) rootView.findViewById(R.id.replylist);
                firebasedb = FirebaseDatabase.getInstance();
                myRef = firebasedb.getReference();
                QTableRef = myRef.child("Question");

                //Asked Question
                QTableRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Get map of users in datasnapshot
                                sendingtolist = new ArrayList<String>();
                                askedquestionlist = new ArrayList<String>();
                                idsofaskedarray = new ArrayList<String>();

                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    Map test1 = (Map) dsp.getValue();
                                    sendingtolist.add((String) test1.get("SendingTo"));
                                    String value=(String) test1.get("Question");
                                    askedquestionlist.add(value.replaceAll("[;]",","));
                                    idsofaskedarray.add((String) test1.get("QID"));
                                }

                                askedarray = new ArrayList<>();
                                sendingarray = new ArrayList<>();
                                askedids = new ArrayList<String>();
                                keylist = new ArrayList<String>();
                                String[] arr;


                                for(int k=0;k<sendingtolist.size();k++)
                                {

                                    String value = sendingtolist.get(k);
                                    arr = value.split("\\W+");
                                    if(arr.length>0)
                                    {
                                        for(String word : arr)
                                        {
                                            if(word.equals(fragment_obj.sid))
                                            {
                                                keylist.add(String.valueOf(k));
                                            }
                                        }
                                    }
                                }

                                for(int a=0;a<keylist.size();a++)
                                {
                                        askedarray.add(askedquestionlist.get(Integer.parseInt(keylist.get(a))));
                                        askedids.add(idsofaskedarray.get(Integer.parseInt(keylist.get(a))));
                                }


                                adapter1 = new ArrayAdapter (getActivity(), android.R.layout.simple_list_item_1, askedarray);
                                askedlist.setAdapter(adapter1);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                                Toast.makeText(getActivity(), databaseError.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });

                //Replied Questions
                QTableRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {

                                sidset = new ArrayList<String>();
                                questionset = new ArrayList<String>();
                                idsofquestions = new ArrayList<String>();

                                for (DataSnapshot dsp : dataSnapshot.getChildren()){

                                    Map test1 = (Map) dsp.getValue();
                                    sidset.add((String) test1.get("SID"));
                                    String value=(String) test1.get("Question");
                                    questionset.add(value.replaceAll("[;]]",","));
                                    idsofquestions.add((String) test1.get("QID"));
                                }

                                finalquestionset = new ArrayList<>();
                                finalidset = new ArrayList<>();
                                Integer l =0;
                                for(String word : sidset)
                                {
                                    if(word.toString().equals(fragment_obj.sid))
                                    {
                                        finalquestionset.add(questionset.get(l));
                                        finalidset.add(idsofquestions.get(l));
                                    }
                                    l++;
                                }

                                adapter2 = new ArrayAdapter (getActivity(), android.R.layout.simple_list_item_1, finalquestionset);
                                replylist.setAdapter(adapter2);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                                Toast.makeText(getActivity(), databaseError.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });

                askedlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getActivity(), AskedQuestionView.class);
                        itemclick1 = (String) parent.getItemAtPosition(position);
                        qid = askedids.get(position);
                        i.putExtra("thequestion", itemclick1);
                        i.putExtra("theqid", qid);
                        i.putExtra("theuserid", fragment_obj.sid);
                        startActivity(i);
                    }
                });

                replylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getActivity(), RepliedQuestionView.class);
                        itemclick1 = (String) parent.getItemAtPosition(position);
                        qid = finalidset.get(position);
                        i.putExtra("thequestion", itemclick1);
                        i.putExtra("theqid", qid);
                        startActivity(i);
                    }
                });
                return rootView;
            }
            else
            {
                View rootView = inflater.inflate(R.layout.fragment_profile2, container, false);

                firebasedb = FirebaseDatabase.getInstance();
                myRef = firebasedb.getReference();
                UTableRef = myRef.child("Users");
                uinforef = UTableRef.child(fragment_obj.sid);

                uinfolist = new ArrayList<>();

                uname = (TextView) rootView.findViewById(R.id.name);
                uattendingyear = (TextView) rootView.findViewById(R.id.attendingyear);
                uemai = (TextView) rootView.findViewById(R.id.email);
                udccn1 = (TextView) rootView.findViewById(R.id.dccni);
                uipe = (TextView) rootView.findViewById(R.id.ipe);
                umit = (TextView) rootView.findViewById(R.id.mit);
                ucf = (TextView) rootView.findViewById(R.id.cf);
                ust1 = (TextView) rootView.findViewById(R.id.sti);
                udbms1 = (TextView) rootView.findViewById(R.id.dbmsi);
                uita = (TextView) rootView.findViewById(R.id.ita);
                ufcs = (TextView) rootView.findViewById(R.id.fcs);
                changepwd = (Button) rootView.findViewById(R.id.ChangePwd);
                editprofile = (Button) rootView.findViewById(R.id.editprofile);

                uinforef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Get map of users in datasnapshot
                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    uinfolist.add(String.valueOf(dsp.getValue())); //add result into array list
                                }

                                uname.setText(uinfolist.get(9));
                                uattendingyear.setText(uinfolist.get(0));
                                uemai.setText(uinfolist.get(4));
                                udccn1.setText(uinfolist.get(3));
                                uipe.setText(uinfolist.get(6));
                                umit.setText(uinfolist.get(8));
                                ucf.setText(uinfolist.get(1));
                                ust1.setText(uinfolist.get(12));
                                udbms1.setText(uinfolist.get(2));
                                uita.setText(uinfolist.get(7));
                                ufcs.setText(uinfolist.get(5));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                                Toast.makeText(getActivity(), databaseError.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });

                changepwd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), change_password.class);
                        i.putExtra("SID", fragment_obj.sid);
                        startActivity(i);
                    }
                });

                editprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), edit_profile.class);
                        i.putExtra("SID", fragment_obj.sid);
                        i.putExtra("Name", uname.getText());
                        i.putExtra("AttendingYear", uattendingyear.getText());
                        i.putExtra("Email", uemai.getText());
                        i.putExtra("DCCN1", udccn1.getText());
                        i.putExtra("IPE", uipe.getText());
                        i.putExtra("MIT", umit.getText());
                        i.putExtra("CF", ucf.getText());
                        i.putExtra("ST1", ust1.getText());
                        i.putExtra("DBMS1", udbms1.getText());
                        i.putExtra("ITA", uita.getText());
                        i.putExtra("FCS", ufcs.getText());
                        startActivity(i);
                    }
                });

                return rootView;
            }


        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Home";
                case 1:
                    return "Messages";
                case 2:
                    return "Profile";
            }
            return null;
        }
    }
}
