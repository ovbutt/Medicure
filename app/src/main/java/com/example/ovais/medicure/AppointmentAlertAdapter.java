package com.example.ovais.medicure;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.example.ovais.medicure.R;
import com.example.ovais.medicure.AppointmentAlert;

public class AppointmentAlertAdapter extends RecyclerView.Adapter<AppointmentAlertAdapter.ViewHolder> {
    private List<AppointmentAlert> mPeopleList;
    private Context mContext;
    private RecyclerView mRecyclerV;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView docName;
        public TextView docPhoneNum;
        public TextView docAddres;
        public TextView time;
        public TextView date;
        public TextView notes;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            title = (TextView) v.findViewById(R.id.textViewTitle);
            docName = (TextView) v.findViewById(R.id.textViewName);
            docPhoneNum = (TextView) v.findViewById(R.id.textPNum);
            docAddres = (TextView) v.findViewById(R.id.textViewAddress);
            time = (TextView) v.findViewById(R.id.textViewTime);
            date = (TextView) v.findViewById(R.id.textViewDate);
            notes = (TextView) v.findViewById(R.id.textViewNotes);
        }
    }

    public void add(int position, AppointmentAlert appointmentAlert) {
        mPeopleList.add(position, appointmentAlert);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public AppointmentAlertAdapter(List<AppointmentAlert> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AppointmentAlertAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.appointment_alert_recycler, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final AppointmentAlert appointmentAlert = mPeopleList.get(position);
        holder.title.setText(appointmentAlert.getTitle());
        holder.docName.setText(appointmentAlert.getDocname());
        holder.docPhoneNum.setText(appointmentAlert.getDocphonenum());
        holder.docAddres.setText(appointmentAlert.getDocaddress());
        holder.time.setText(appointmentAlert.getTime());
        holder.date.setText(appointmentAlert.getDate());
        holder.notes.setText(appointmentAlert.getNotes());

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Delete Alarm?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppointmentAlertDBHelper dbHelper = new AppointmentAlertDBHelper(mContext);
                        dbHelper.deletePersonRecord(appointmentAlert.getId(), mContext);

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