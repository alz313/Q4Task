package com.alz313.q4task;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alz313.q4task.Db.DbSchema;


public class SquareTaskFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = "log13";

    private SquareTaskRecyclerViewAdapter mRecyclerViewAdapter_11;
    private SquareTaskRecyclerViewAdapter mRecyclerViewAdapter_12;
    private SquareTaskRecyclerViewAdapter mRecyclerViewAdapter_21;
    private SquareTaskRecyclerViewAdapter mRecyclerViewAdapter_22;

    public SquareTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " Start init Loaders");
        getLoaderManager().initLoader(11, null, this);
        getLoaderManager().initLoader(12, null, this);
        getLoaderManager().initLoader(21, null, this);
        getLoaderManager().initLoader(22, null, this);
        Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " Finish init Loaders");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_square_task, container, false);
        Context context = v.getContext();

        RecyclerView recyclerView_11 = (RecyclerView) v.findViewById(R.id.fragment_square_rv_11);
        RecyclerView recyclerView_12 = (RecyclerView) v.findViewById(R.id.fragment_square_rv_12);
        RecyclerView recyclerView_21 = (RecyclerView) v.findViewById(R.id.fragment_square_rv_21);
        RecyclerView recyclerView_22 = (RecyclerView) v.findViewById(R.id.fragment_square_rv_22);

        recyclerView_11.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_12.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_21.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_22.setLayoutManager(new LinearLayoutManager(context));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView_11.addItemDecoration(itemDecoration);
        recyclerView_12.addItemDecoration(itemDecoration);
        recyclerView_21.addItemDecoration(itemDecoration);
        recyclerView_22.addItemDecoration(itemDecoration);

        Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " Start creating Adapters");
        mRecyclerViewAdapter_11 = new SquareTaskRecyclerViewAdapter();
        mRecyclerViewAdapter_12 = new SquareTaskRecyclerViewAdapter();
        mRecyclerViewAdapter_21 = new SquareTaskRecyclerViewAdapter();
        mRecyclerViewAdapter_22 = new SquareTaskRecyclerViewAdapter();
        Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " End creating Adapters");

        Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " Start setting Adapters");
        recyclerView_11.setAdapter(mRecyclerViewAdapter_11);
        recyclerView_12.setAdapter(mRecyclerViewAdapter_12);
        recyclerView_21.setAdapter(mRecyclerViewAdapter_21);
        recyclerView_22.setAdapter(mRecyclerViewAdapter_22);
        Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " End setting Adapters");


        return v;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 11:
                Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " create Loader 11");
                return new CursorLoader(getActivity(), Uri.parse("content://com.alz313.q4task/task/no/urgent/important"), null, DbSchema.TaskTable.Cols.IS_SOLVED + " = ? ", new String[]{"0"}, null);
            case 12:
                Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " create Loader 12");
                return new CursorLoader(getActivity(), Uri.parse("content://com.alz313.q4task/task/important"), null, DbSchema.TaskTable.Cols.IS_SOLVED + " = ? ", new String[]{"0"}, null);
            case 21:
                Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " create Loader 21");
                return new CursorLoader(getActivity(), Uri.parse("content://com.alz313.q4task/task/urgent"), null, DbSchema.TaskTable.Cols.IS_SOLVED + " = ? ", new String[]{"0"}, null);
            case 22:
                Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " create Loader 22");
                return new CursorLoader(getActivity(), Uri.parse("content://com.alz313.q4task/task/urgent/important"), null, DbSchema.TaskTable.Cols.IS_SOLVED + " = ? ", new String[]{"0"}, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 11:
                mRecyclerViewAdapter_11.setCursor(data);
                Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " Load Finished 11");
                break;
            case 12:
                mRecyclerViewAdapter_12.setCursor(data);
                Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " Load Finished 12");
                break;
            case 21:
                mRecyclerViewAdapter_21.setCursor(data);
                Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " Load Finished 21");
                break;
            case 22:
                mRecyclerViewAdapter_22.setCursor(data);
                Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " Load Finished 22");
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, SquareTaskFragment.class.getName() + " onLoaderReset (method Blank now)");
    }
}
