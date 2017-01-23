package com.alz313.q4task;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alz313.q4task.Db.DbSchema;

public class ListTaskRecyclerViewAdapter extends RecyclerView.Adapter<ListTaskRecyclerViewAdapter.ViewHolder> {
    private Cursor mCursor = null;
    private Context mContext;

    public ListTaskRecyclerViewAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_list_task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View mView;
        final TextView mId;
        final ImageView mEstimation;
        final TextView mTitle;
        final TextView mDescription;
        final ImageView mFrog;

        ViewHolder(View row) {
            super(row);

            mView = row;
            mEstimation = (ImageView) row.findViewById(R.id.list_task_item_estimate);
            mId = (TextView) row.findViewById(R.id.list_task_item_id);
            mTitle = (TextView) row.findViewById(R.id.list_task_item_title);
            mDescription = (TextView) row.findViewById(R.id.list_task_item_description);
            mFrog = (ImageView) row.findViewById(R.id.list_task_item_frog);

            row.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, TaskActivity.class);
            i.putExtra(DbSchema.TaskTable.Cols.ID, Long.valueOf(mId.getText().toString()));
            mContext.startActivity(i);
        }

        void bindModel(Cursor row) {
            mId.setText(row.getString(row.getColumnIndex(DbSchema.TaskTable.Cols.ID)));

            String imageName;
            switch (row.getInt(row.getColumnIndex(DbSchema.TaskTable.Cols.ESTIMATE))) {
                case 0:
                    imageName = "aroow_3_5min";
                    break;
                case 1:
                    imageName = "aroow_3_15min";
                    break;
                case 2:
                    imageName = "aroow_3_30min";
                    break;
                case 3:
                    imageName = "aroow_3_1h";
                    break;
                case 4:
                    imageName = "aroow_3_2h";
                    break;
                case 5:
                    imageName = "aroow_3_05day";
                    break;
                case 6:
                    imageName = "aroow_3_day";
                    break;
                default:
                    imageName = "aroow_3_5min";
                    break;
            }
            int resID = mContext.getResources().getIdentifier(imageName, "drawable", "com.alz313.q4task");
            mEstimation.setImageResource(resID);

            mTitle.setText(row.getString(row.getColumnIndex(DbSchema.TaskTable.Cols.TITLE)));

            String description = row.getString(row.getColumnIndex(DbSchema.TaskTable.Cols.DESCRIPTION));
            if (description.equals("")) {
                mDescription.setVisibility(View.GONE);
            } else {
                mDescription.setText(row.getString(row.getColumnIndex(DbSchema.TaskTable.Cols.DESCRIPTION)));
            }


            if (row.getInt(row.getColumnIndex(DbSchema.TaskTable.Cols.IS_FROG)) == 0) {
                mFrog.setVisibility(View.GONE);
            } else {
                mFrog.setVisibility(View.VISIBLE);
            }

        }
    }
}
