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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alz313.q4task.Db.DbSchema;


public class ListTaskFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_ORDER_COLUMN = "order_column";
    private static final Uri TASK_URI = Uri.parse("content://com.alz313.q4task/task");

    private String mOrderColumn = DbSchema.TaskTable.Cols.ID;

    private ListTaskRecyclerViewAdapter mListTaskRecyclerViewAdapter;

    public ListTaskFragment() {
    }

    public static ListTaskFragment newInstance(String orderColumn) {
        ListTaskFragment fragment = new ListTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ORDER_COLUMN, orderColumn);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mOrderColumn = getArguments().getString(ARG_ORDER_COLUMN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_task, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mListTaskRecyclerViewAdapter = new ListTaskRecyclerViewAdapter();
        recyclerView.setAdapter(mListTaskRecyclerViewAdapter);

//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(itemDecoration);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), TASK_URI, null, DbSchema.TaskTable.Cols.IS_SOLVED + " = ?", new String[]{"0"}, mOrderColumn + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mListTaskRecyclerViewAdapter.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListTaskRecyclerViewAdapter.setCursor(null);
    }


}
