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


import com.example.ovais.medicure.Doctors;
import com.example.ovais.medicure.R;

//MyDoctor2

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {
    private List<Doctors> mPeopleList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private TextView plustv;




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView docName;
        public TextView docSpec;
        public TextView docPhoneNum;
        public TextView docLandLine;
        public TextView docEmail;
        public TextView docAddress;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            plustv = (TextView)v.findViewById(R.id.textViewplus);
            docName = (TextView) v.findViewById(R.id.textViewName);
            docSpec = (TextView) v.findViewById(R.id.textViewSpecializ);
            docPhoneNum = (TextView) v.findViewById(R.id.textPNum);
            docLandLine = (TextView) v.findViewById(R.id.textViewLandLine);
            docEmail = (TextView) v.findViewById(R.id.textViewEmail);
            docAddress = (TextView) v.findViewById(R.id.textViewAddress);
        }
    }

    public void add(int position, Doctors doctors) {
        mPeopleList.add(position, doctors);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public DoctorsAdapter(List<Doctors> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DoctorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.doctor_recycler, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Doctors doctors = mPeopleList.get(position);
        holder.docName.setText(doctors.getName());
        holder.docSpec.setText(doctors.getSpecialization());
        holder.docPhoneNum.setText(doctors.getPhonenum());
        holder.docLandLine.setText(doctors.getLandline());
        holder.docEmail.setText(doctors.getEmail());
        holder.docAddress.setText(doctors.getAddress());
//        Picasso.with(mContext).load(doctors.getImage()).placeholder(R.mipmap.ic_launcher).into(holder.docLandLine);

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete Doctor");
                builder.setMessage("Do you want to delete doctor?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DoctorsDBHelper dbHelper = new DoctorsDBHelper(mContext);
                        dbHelper.deletePersonRecord(doctors.getId(), mContext);

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

//    public void setTextView()
//    {
//        if (mRecyclerV.getAdapter().getItemCount() == 0)
//        {
//            plustv.setVisibility(View.VISIBLE);
//
//        }
//        else
//        {
//            plustv.setVisibility(View.GONE);
//        }
//    }

}
