package com.example.ovais.medicure;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private Context mCtx;
    private List<Product> productList;

    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout,null);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {

        final Product product = productList.get(position);
        holder.dname.setText(product.getDname());
        holder.dspecialization.setText(product.getDspecilization());
        holder.drating.setText(String.valueOf(product.getRating()));
        holder.dfees.setText(String.valueOf(product.getFees()));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx,"You Clicked"+ productList.get(position),Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mCtx,ViewDoctorActivity.class);
                intent.putExtra("name",product.getDname());
                intent.putExtra("rating",product.getRating());
                intent.putExtra("specialization",product.getDspecilization());
                intent.putExtra("fees",product.getFees());
                intent.putExtra("address",product.getDaddress());
                intent.putExtra("timings",product.getDtimings());
                intent.putExtra("contact",product.getDcontact());
                intent.putExtra("landline",product.getDlandline());
                intent.putExtra("email",product.getDemail());
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView dname, dspecialization, drating, dfees, dcall;
        RelativeLayout relativeLayout;

        public ProductViewHolder(View itemView) {
            super(itemView);

            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.layout);
            dname = (TextView) itemView.findViewById(R.id.textViewTitle);
            dspecialization = (TextView) itemView.findViewById(R.id.textViewShortDesc);
            drating = (TextView) itemView.findViewById(R.id.textViewRating);
            dfees = (TextView) itemView.findViewById(R.id.textViewPrice);
            dcall = (TextView) itemView.findViewById(R.id.callButton);
        }
    }
}
