package com.example.a0711_0127122usafeadminapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ReportDetailsActivity extends AppCompatActivity {
    private TextView tvIssue, tvReportId, tvDescription, tvLocation, tvUserId;
    private ImageView imvReport;

    private String reportId = "";
    private String issue = "";
    private String location = "";
    private String description = "";
    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);

        tvIssue = findViewById(R.id.tv_issue);
        tvReportId = findViewById(R.id.tv_report_id);
        tvDescription = findViewById(R.id.tv_description);
        tvLocation = findViewById(R.id.tv_location);
        tvUserId = findViewById(R.id.tv_user_id);
        imvReport = findViewById(R.id.imv_report);

        // Get text from ViewReportsActivity.java
        Bundle extras = getIntent().getExtras();
        issue = extras.getString("issue");
        reportId = extras.getString("reportId");
        description = extras.getString("description");
        location = extras.getString("location");
        userId = extras.getString("userId");

        // Set text & image
        tvIssue.setText(issue);
        tvReportId.setText(reportId);
        tvDescription.setText(description);
        tvLocation.setText(location);
        tvUserId.setText(userId);

        //Load saved image from firebase firestore database.
        StorageReference imgRef;
        imgRef = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = imgRef.child("Report/report_" + Integer.parseInt(reportId) + ".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imvReport);
            }
        });

    }
}