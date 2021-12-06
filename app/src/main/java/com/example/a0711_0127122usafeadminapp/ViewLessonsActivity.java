package com.example.a0711_0127122usafeadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ViewLessonsActivity extends AppCompatActivity {
    private DatabaseReference lessonRef;
    private Lesson lesson;
    private ArrayList<Lesson> lessonList = new ArrayList<>();
    private ArrayList<Lesson> tempLessonList = new ArrayList<>();
    private TextView tvModName, tvDayDate, tvTime, tvLoc, tvSeats, noClass;

    // Set time zone and format date and time
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
    //Variables
    private String active = "true";
    private String lessonId = "0";
    private String lessonDate = "";
    private String startTime = "";
    private String endTime = "";
    private String startDateTime = "";
    private String week = "";
    private String location = "";
    private String capacity = "";
    private String day = "";
    private String date = "";
    private String moduleName = "";
    private String mode = "";
    private String lecturer = "";
    private Date now = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lessons);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);

        // Retrieving data from firebase real-time db.
        lessonRef = FirebaseDatabase.getInstance("https://usafe---0127122-a31c2-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Lesson");
        lessonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    // Lesson Node
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        // lessonId Node
                        ArrayList<String> seatList = new ArrayList<>();

                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            //Get the current child node
                            String child2 = snapshot2.getKey();

                            if(child2.equals("lessonId")){
                                lessonId = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("week")){
                                week = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("location")){
                                location = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("capacity")){
                                capacity = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("seatNo")){
                                seatList.clear();
                                for(DataSnapshot snapshot3 : snapshot2.getChildren()) {
                                    String child3 = snapshot3.getKey();
                                    String seat = snapshot3.getValue(String.class);
                                    seatList.add(seat);
                                }
                            }else if(child2.equals("day")){
                                day = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("date")){
                                date = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("startTime")){
                                startTime = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("endTime")){
                                endTime = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("moduleName")){
                                moduleName = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("mode")){
                                mode = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("lecturer")){
                                lecturer = snapshot2.getValue(String.class);
                            }
                            else if(child2.equals("active")){
                                active = snapshot2.getValue(String.class);
                            }
                        }

                        // Add into the lessonList
                        tempLessonList.add(new Lesson(lessonId, week, location,  capacity,  day,  date,  startTime,  endTime,  moduleName,  mode,  lecturer,  active));
                        Log.d("ADebugTag", "Lesson Id 1: " + lessonId);
                        Log.d("ADebugTag", "Module Name 1: " + moduleName);
                        Log.d("ADebugTag", "Start Time 1: " + startTime);
                        Log.d("ADebugTag", "End Time 1: " + endTime);
                        Log.d("ADebugTag", "Is Active 1: " + active);
                        Log.d("ADebugTag", "Lecturer 1: " + lecturer);
                    }

                    // Check if the lesson is still active
                    for (Lesson lesson : tempLessonList) {
                        Log.d("ADebugTag", "Lesson Id 2: " + lesson.getLessonId());
                        Log.d("ADebugTag", "Module Name 2: " + lesson.getModuleName());
                        Log.d("ADebugTag", "Start Time 2: " + lesson.getStartTime());
                        Log.d("ADebugTag", "End Time 2: " + lesson.getEndTime());
                        Log.d("ADebugTag", "Is Active 2: " + lesson.isActive());
                        Log.d("ADebugTag", "Lecturer 2: " + lesson.getLecturer());
                        lessonDate = lesson.getDate();
                        startTime = lesson.getStartTime();
                        String active = lesson.isActive();
                        startDateTime = lessonDate + " " + startTime;
                        Date startDT = new Date();

                        // Formatting the date and time of the lesson
                        try {
                            startDT = dateTimeFormat.parse(startDateTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // To check if the lesson is still active
                        if (startDT.after(now) && active.equals("true")) {
                            lessonList.add(lesson);
                            Log.d("ADebugTag", "Lesson Id 3: " + lesson.getLessonId());
                            Log.d("ADebugTag", "Module Name 3: " + lesson.getModuleName());
                            Log.d("ADebugTag", "Start Time 3: " + lesson.getStartTime());
                            Log.d("ADebugTag", "End Time 3: " + lesson.getEndTime());
                            Log.d("ADebugTag", "Is Active 3: " + lesson.isActive());
                            Log.d("ADebugTag", "Lecturer 3: " + lesson.getLecturer());
                        }
                        else{
                            // Update active status -> false
                            lessonRef.child(lesson.getLessonId()).child("active").setValue("false");
                            Log.d("ADebugTag", "active(after): " + false);
                        }
                    }


                    GridView gridView = findViewById(R.id.gv_lesson_list);
                    ViewLessonsAdapter viewLessonsAdapter = new ViewLessonsAdapter(ViewLessonsActivity.this, R.layout.lesson_item, lessonList);
                    gridView.setAdapter(viewLessonsAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ViewLessonsActivity.this, LessonDetailsActivity.class);
                            intent.putExtra("lessonId", lessonList.get(position).getLessonId());
                            intent.putExtra("location", lessonList.get(position).getLocation());
                            intent.putExtra("capacity", lessonList.get(position).getCapacity());
                            intent.putExtra("seatNo", lessonList.get(position).getSeatNo());
                            intent.putExtra("dayDate", lessonList.get(position).getDay() + ", " + lessonList.get(position).getDate());
                            intent.putExtra("time", lessonList.get(position).getStartTime() + " - " + lessonList.get(position).getEndTime());
                            intent.putExtra("moduleName", lessonList.get(position).getModuleName());
                            intent.putExtra("lecturer", lessonList.get(position).getLecturer());
                            intent.putExtra("moduleName", lessonList.get(position).getModuleName());
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