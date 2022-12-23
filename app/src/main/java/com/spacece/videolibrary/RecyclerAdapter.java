package com.spacece.videolibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements Filterable {

    ArrayList<Topic> topics;
    ArrayList<Topic> AllTopics,preFilteredList;

    private RecyclerViewClickListener listener;

    public RecyclerAdapter(ArrayList<Topic> topics, RecyclerViewClickListener listener){
        this.topics=topics;
        this.listener= listener;
        AllTopics= new ArrayList<>(topics);
        preFilteredList=new ArrayList<>(topics);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView topic_name;
        private TextView length;
        private ImageView bg_img;

        public MyViewHolder(@NonNull View view) {
            super(view);
            topic_name = view.findViewById(R.id.topicName);
            bg_img = view.findViewById(R.id.imageView);
            length= view.findViewById(R.id.durationView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String name = topics.get(position).getTopic_name();
        String v_id = topics.get(position).getV_id();
        String duration = topics.get(position).getLength();
        holder.topic_name.setText(name);
        holder.length.setText(duration);
        Picasso.get().load("https://i.ytimg.com/vi/"+v_id+"/0.jpg").into(holder.bg_img);
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public void listUpdate(String name, String description) {
        preFilteredList = new ArrayList<>();

        if (name!= null || description != null) {
            for (Topic item : AllTopics) {
                if (String.valueOf(item.getTopic_name()).toLowerCase(Locale.ROOT).contains(name) && item.getDescription().toLowerCase(Locale.ROOT).contains(description)) {
                    preFilteredList.add(item);
                }
            }
        }else{
            preFilteredList= new ArrayList<Topic>(AllTopics);
        }
        topics.clear();
        topics.addAll(preFilteredList);
        getFilter();

    }

    @Override
    public Filter getFilter(){
        return SearchFilter;
    }

    private Filter SearchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Topic> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(preFilteredList);
            } else {
                String filteredPattern = charSequence.toString().toLowerCase(Locale.ROOT).trim();

                for (Topic item : preFilteredList) {
                    if (item.getTopic_name().toLowerCase(Locale.ROOT).contains(filteredPattern) || item.getDescription().toLowerCase(Locale.ROOT).contains(filteredPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            topics.clear();
            topics.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

    };
}