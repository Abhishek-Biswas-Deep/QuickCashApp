package com.Main.csci3130groupassignment.HelperFunctions;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

public class PreferencesManager {
    /**
     * This class was influenced heavily by the following links:
     * https://www.youtube.com/watch?v=03gZQDlUFqY
     * https://developer.android.com/reference/android/content/SharedPreferences
     * https://www.geeksforgeeks.org/shared-preferences-in-android-with-examples/
     * https://www.digitalocean.com/community/tutorials/android-shared-preferences-example-tutorial
     */


    private SharedPreferences sharedPreferences;
    private Context context;


    public PreferencesManager(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
    }

    public void updateStoredJobCount(int jobCount){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("jobCount", jobCount);
        editor.apply();
    }

    public int getStoredJobCount(){
        return sharedPreferences.getInt("JobCount", 0);
    }

    public void checkForNewJobs(Consumer<Integer> callback){
        getCurrentJobCount(newJobCount -> {
            int storedJobCount = getStoredJobCount();
            if(newJobCount > storedJobCount){
                Toast.makeText(context, "New Jobs Are Available!", Toast.LENGTH_SHORT).show();
                updateStoredJobCount(newJobCount);
                if(callback != null){
                    callback.accept(newJobCount);
                }
            }
        });

    }

    private void getCurrentJobCount(Consumer<Integer> callback){
        DatabaseReference jobReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Jobs");

        jobReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int currentJobCount = (int) snapshot.getChildrenCount();
                if(callback != null){
                    callback.accept(currentJobCount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PreferenceManager", "Failed to read job count", error.toException());

                Toast.makeText(context, "Error while fetching jobs", Toast.LENGTH_SHORT).show();

                if(callback != null){
                    callback.accept(-1);
                }
            }
        });

    }

}
