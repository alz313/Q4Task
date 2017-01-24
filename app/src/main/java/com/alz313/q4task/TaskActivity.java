package com.alz313.q4task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.alz313.q4task.Db.DbSchema;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        long id = intent.getLongExtra(DbSchema.TaskTable.Cols.ID, 0);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_task_frame_layout);
        if (fragment == null) {
            fragment = TaskFragment.newInstance(id);
            fm.beginTransaction()
                    .add(R.id.activity_task_frame_layout, fragment)
                    .commit();
        }


    }
}
