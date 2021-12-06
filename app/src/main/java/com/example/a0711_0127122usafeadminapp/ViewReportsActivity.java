package com.example.a0711_0127122usafeadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ViewReportsActivity extends AppCompatActivity {
    private DatabaseReference reportRef;
    private ArrayList<Report> reportList = new ArrayList<>();

    private String issue = "";
    private String location = "";
    private String description = "";
    private String reportId = "";
    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);

        // Retrieving data from firebase real-time db.
        reportRef = FirebaseDatabase.getInstance("https://usafe---0127122-a31c2-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Report");
        reportRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    // Report Node
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        // reportId Node
                        ArrayList<String> seatList = new ArrayList<>();

                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            //Get the current child node
                            String child2 = snapshot2.getKey();
                            Log.d("ADebugTag", "child2: " + child2);

                            if(child2.equals("description")){
                                description = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("issue")){
                                issue = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("location")){
                                location = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("reportId")){
                                reportId = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("userId")){
                                userId = snapshot2.getValue(String.class);
                            }
                        }

                        // Add into the reportList
                        reportList.add(new Report(issue, location, description, reportId, userId));
                    }



                    GridView gridView = findViewById(R.id.gv_report_list);
                    ViewReportsAdapter viewReportsAdapter = new ViewReportsAdapter(ViewReportsActivity.this, R.layout.report_item, reportList);
                    gridView.setAdapter(viewReportsAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ViewReportsActivity.this, ReportDetailsActivity.class);
                            intent.putExtra("description", reportList.get(position).getDescription());
                            intent.putExtra("issue", reportList.get(position).getIssue());
                            intent.putExtra("location", reportList.get(position).getLocation());
                            intent.putExtra("reportId", reportList.get(position).getReportId());
                            intent.putExtra("userId", reportList.get(position).getUserId());
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}