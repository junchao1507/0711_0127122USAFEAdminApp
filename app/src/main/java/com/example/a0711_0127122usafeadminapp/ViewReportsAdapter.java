package com.example.a0711_0127122usafeadminapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewReportsAdapter extends ArrayAdapter<Report> {
    List<Report> items_list = new ArrayList<>();
    int menu_layout_id;

    public ViewReportsAdapter(@NonNull Context context, int resource, @NonNull List<Report> objects) {
        super(context, resource, objects);
        items_list = objects;
        menu_layout_id = resource;
    }

    @Override
    public int getCount() {
        return items_list.size();
    }

    @Override
    public Report getItem(int position) {
        return items_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            // getting reference to the main layout and
            // initializing
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(menu_layout_id, null);
        }

        // initializing the imageview and textview and
        // setting data

        TextView tvIssue = v.findViewById(R.id.tv_issue);
        TextView tvDescription = v.findViewById(R.id.tv_description);
        ImageView imvReport = v.findViewById(R.id.imv_report);


        // get the item using the  position param
        Report report = items_list.get(position);
        tvIssue.setText(report.getIssue());
        tvDescription.setText(report.getDescription());

        //Load saved image from firebase firestore database.
        StorageReference imgRef;
        imgRef = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = imgRef.child("Report/report_" + report.getReportId() + ".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imvReport);
            }
        });

        return v;
    }
}