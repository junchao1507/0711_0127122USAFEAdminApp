package com.example.a0711_0127122usafeadminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);
        Intent getUserIntent = getIntent();
        Admin admin = (Admin)getUserIntent.getSerializableExtra("adminObject");

        List<MainMenuItem> itemList = new ArrayList<>();
        itemList.add(new MainMenuItem("Add Lessons"));
        itemList.add(new MainMenuItem("View Lessons"));
        itemList.add(new MainMenuItem("View Reports"));
        itemList.add(new MainMenuItem("Add Quiz Questions"));
        itemList.add(new MainMenuItem("Logout"));

        GridView gridView = findViewById(R.id.gv_menu);
        MainMenuAdapter menuAdapter = new MainMenuAdapter(this, R.layout.main_menu_item, itemList);
        gridView.setAdapter(menuAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Context context = MainActivity.this;
                Intent intent;

                switch(position)
                {
                    case 0:
                        intent =  new Intent(context, AddLessonsActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(context, ViewLessonsActivity.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent =  new Intent(context, ViewReportsActivity.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent =  new Intent(context, UpdateQuizActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent =  new Intent(context, CoverActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}