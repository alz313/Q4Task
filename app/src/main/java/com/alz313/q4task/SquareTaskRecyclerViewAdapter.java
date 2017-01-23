package com.alz313.q4task;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alz313.q4task.Db.DbSchema;


public class SquareTaskRecyclerViewAdapter extends RecyclerView.Adapter<SquareTaskRecyclerViewAdapter.ViewHolder>{
    private Cursor mCursor = null;
    private Context mContext;

    public SquareTaskRecyclerViewAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_square_task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.bindModel(mCursor);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return (0);
        }
        return (mCursor.getCount());
    }

    void setCursor(Cursor cursor) {
        this.mCursor = cursor;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final View mView;
        final TextView mId;
        final TextView mTitle;
        final TextView mDescription;

        ViewHolder(View row) {
            super(row);

            mView = row;
            mId = (TextView) row.findViewById(R.id.fragment_square_item_tv_id);
            mTitle = (TextView) row.findViewById(R.id.fragment_square_item_tv_title);
            mDescription = (TextView) row.findViewById(R.id.fragment_square_item_tv_desc);
        }

        void bindModel(Cursor cursor) {
            mId.setText(cursor.getString(cursor.getColumnIndex(DbSchema.TaskTable.Cols.ID)));
            mTitle.setText(cursor.getString(cursor.getColumnIndex(DbSchema.TaskTable.Cols.TITLE)));

            String descripion = cursor.getString(cursor.getColumnIndex(DbSchema.TaskTable.Cols.DESCRIPTION));
            if (descripion.equals("")) {
                mDescription.setVisibility(View.GONE);
            } else {
                mDescription.setText(cursor.getString(cursor.getColumnIndex(DbSchema.TaskTable.Cols.DESCRIPTION)));
            }
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, TaskActivity.class);
            i.putExtra(DbSchema.TaskTable.Cols.ID, Long.valueOf(mId.getText().toString()));
            mContext.startActivity(i);
        }
    }
}
