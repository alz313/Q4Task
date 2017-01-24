package com.alz313.q4task;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.alz313.q4task.Db.DbSchema;

public class TaskFragment extends Fragment {
    final Uri TASK_URI = Uri.parse("content://com.alz313.q4task/task");
    private boolean mIsEditable = true;

    long mId;
    EditText mTitle;
    EditText mDescription;

    SeekBar mImportance;
    SeekBar mUrgency;

    Spinner mEstimation;

    CheckBox mIsFrog;
    CheckBox mIsSolved;

    public TaskFragment() {
    }

    public static TaskFragment newInstance(long id) {
        TaskFragment taskFragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putLong(DbSchema.TaskTable.Cols.ID, id);
        taskFragment.setArguments(args);
        return taskFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

        Bundle arguments = getArguments();
        mId = arguments.getLong(DbSchema.TaskTable.Cols.ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        mTitle = (EditText) v.findViewById(R.id.input_title);
        mDescription = (EditText) v.findViewById(R.id.input_description);

        mImportance = (SeekBar) v.findViewById(R.id.input_importance);
        mUrgency = (SeekBar) v.findViewById(R.id.input_urgency);

        mEstimation = (Spinner) v.findViewById(R.id.input_estimation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.string_array_estimate, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEstimation.setAdapter(adapter);

        mIsFrog = (CheckBox) v.findViewById(R.id.input_is_frog);
        mIsSolved = (CheckBox) v.findViewById(R.id.input_is_solved);

        if (mId != 0) {
            mIsEditable = false;
            setEditable(false);
            Cursor cursor = getActivity().getContentResolver().query(ContentUris.withAppendedId(TASK_URI, mId), null, null, null, null);
            setValues(cursor);
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task, menu);
        super.onCreateOptionsMenu(menu, inflater);
        if (mId != 0) {
            (menu.findItem(R.id.action_save_edit)).setIcon(mIsEditable ? android.R.drawable.ic_menu_save : android.R.drawable.ic_menu_edit);
        } else {
            (menu.findItem(R.id.action_delete)).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                //Do nothing
                break;
            case R.id.action_save_edit:
                if (mIsEditable) {
                    if (mId == 0) {
                        Uri newUri = getActivity().getContentResolver().insert(TASK_URI, getContentValues());
                        mId = ContentUris.parseId(newUri);
                    } else {
                        getActivity().getContentResolver().update(ContentUris.withAppendedId(TASK_URI, mId), getContentValues(), null, null);
                    }
                }
                mIsEditable = !mIsEditable;
                item.setIcon(mIsEditable ? android.R.drawable.ic_menu_save : android.R.drawable.ic_menu_edit);
                setEditable(mIsEditable);

                break;
            case R.id.action_delete:
                getActivity().getContentResolver().delete(ContentUris.withAppendedId(TASK_URI, mId), null, null);
                getActivity().finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(DbSchema.TaskTable.Cols.TITLE, mTitle.getText().toString());
        values.put(DbSchema.TaskTable.Cols.DESCRIPTION, mDescription.getText().toString());
        values.put(DbSchema.TaskTable.Cols.IMPORTANCE, Integer.toString(mImportance.getProgress()));
        values.put(DbSchema.TaskTable.Cols.URGENCY, Integer.toString(mUrgency.getProgress()));

        values.put(DbSchema.TaskTable.Cols.ESTIMATE, mEstimation.getSelectedItemPosition());
        values.put(DbSchema.TaskTable.Cols.IS_FROG, mIsFrog.isChecked());
        values.put(DbSchema.TaskTable.Cols.IS_SOLVED, mIsSolved.isChecked());
        return values;
    }

    private void setEditable(boolean enable) {
        mTitle.setEnabled(enable);
        mDescription.setEnabled(enable);

        mImportance.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !mIsEditable;
            }
        });
        mUrgency.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !mIsEditable;
            }
        });
        mEstimation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !mIsEditable;
            }
        });
        mIsFrog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !mIsEditable;
            }
        });
        mIsSolved.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !mIsEditable;
            }
        });
    }

    void setValues(Cursor values) {
        if (values.moveToFirst()) {
            mTitle.setText(values.getString(values.getColumnIndex(DbSchema.TaskTable.Cols.TITLE)));
            mDescription.setText(values.getString(values.getColumnIndex(DbSchema.TaskTable.Cols.DESCRIPTION)));

            mImportance.setProgress(values.getInt(values.getColumnIndex(DbSchema.TaskTable.Cols.IMPORTANCE)));
            mUrgency.setProgress(values.getInt(values.getColumnIndex(DbSchema.TaskTable.Cols.URGENCY)));

            mEstimation.setSelection(values.getInt(values.getColumnIndex(DbSchema.TaskTable.Cols.ESTIMATE)));
            mIsFrog.setChecked(values.getInt(values.getColumnIndex(DbSchema.TaskTable.Cols.IS_FROG)) != 0);
            mIsSolved.setChecked(values.getInt(values.getColumnIndex(DbSchema.TaskTable.Cols.IS_SOLVED)) != 0);
        }
    }
}
