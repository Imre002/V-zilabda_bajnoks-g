package com.example.vizilabda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class dataListAdapter extends RecyclerView.Adapter<dataListAdapter.ViewHolder> implements Filterable {

    private ArrayList<dataItem> mdataItemsData;
    private ArrayList<dataItem>  mdataItemsDataAll;
    private Context mContext;
    private int lastPosition = -1;
    dataListAdapter(Context context, ArrayList<dataItem> itemsData){
        this.mdataItemsData = itemsData;
        this.mdataItemsDataAll = itemsData;
        this.mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder( dataListAdapter.ViewHolder holder, int position) {
        dataItem currentItem = mdataItemsData.get(position);

        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mdataItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<dataItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                results.count = mdataItemsDataAll.size();
                results.values = mdataItemsDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(dataItem item : mdataItemsDataAll){
                    if(item.getTeamName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mdataItemsData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTeamText;
        private TextView mPointText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTeamText = itemView.findViewById(R.id.teamText);
            mPointText = itemView.findViewById(R.id.pointText);
        }

        public void bindTo(dataItem currentItem) {
            mTeamText.setText(currentItem.getTeamName());
            mPointText.setText(currentItem.getPoint());
        }
    };
};


