package com.alz313.q4task;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alz313.q4task.Db.DbSchema;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private Fragment mSquareFragment;
    private Fragment mImportantFragment;
    private Fragment mUrgentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tasks sorted by:");
        toolbar.setSubtitle("Urgancy \u2192 Importance \u2191");
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getSupportActionBar().setTitle("All tasks:");
                        getSupportActionBar().setSubtitle("Urgancy \u2192 Importance \u2191");
                        break;
                    case 1:
                        getSupportActionBar().setTitle("Importance");
                        getSupportActionBar().setSubtitle("Tasks sorted by importance");
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Urgency");
                        getSupportActionBar().setSubtitle("Tasks sorted by urgency");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTaskIntent = new Intent(MainActivity.this, TaskActivity.class);
                startActivity(addTaskIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (mSquareFragment == null){
                        mSquareFragment = new SquareTaskFragment();
                    }
                    return mSquareFragment;
                case 1:
                    if (mImportantFragment == null) {
                        mImportantFragment = ListTaskFragment.newInstance(DbSchema.TaskTable.Cols.IMPORTANCE);
                    }
                    return mImportantFragment;
                case 2:
                    if (mUrgentFragment == null) {
                        mUrgentFragment = ListTaskFragment.newInstance(DbSchema.TaskTable.Cols.URGENCY);
                    }
                    return mUrgentFragment;
                default:
                    return null;
            }
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
                    return "All";
                case 1:
                    return "Important";
                case 2:
                    return "Urgent";
            }
            return null;
        }
    }
}
