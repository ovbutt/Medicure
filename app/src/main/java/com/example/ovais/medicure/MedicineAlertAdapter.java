package com.example.ovais.medicure;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MedicineAlertAdapter extends RecyclerView.Adapter<MedicineAlertAdapter.ViewHolder> {
    private List<MedicineAlert> mPeopleList;
    private Context mContext;
    private RecyclerView mRecyclerV;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView time;
        public TextView date;
        public TextView quantity;
        public TextView notes;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            title = (TextView) v.findViewById(R.id.textViewTitle);
            time = (TextView) v.findViewById(R.id.textViewTime);
            date = (TextView) v.findViewById(R.id.textViewDate);
            quantity = (TextView) v.findViewById(R.id.textViewQuantity);
            notes = (TextView) v.findViewById(R.id.textViewNotes);
        }
    }

    public void add(int position, MedicineAlert medicineAlert) {
        mPeopleList.add(position, medicineAlert);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public MedicineAlertAdapter(List<MedicineAlert> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MedicineAlertAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.medicine_single_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final MedicineAlert medicineAlert = mPeopleList.get(position);
        holder.title.setText(medicineAlert.getTitle());
        holder.time.setText(medicineAlert.getTime());
        holder.date.setText(medicineAlert.getDate());
        holder.quantity.setText(medicineAlert.getQuantity());
        holder.notes.setText(medicineAlert.getNotes());

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Delete user?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MedicineAlertDBHelper dbHelper = new MedicineAlertDBHelper(mContext);
                        dbHelper.deletePersonRecord(medicineAlert.getId(), mContext);

                        mPeopleList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mPeopleList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });


    }
     // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }

}