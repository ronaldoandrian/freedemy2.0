package com.master.mobile.freedemy.ui.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.master.mobile.freedemy.R;

public class ListCoursAdapter extends RecyclerView.Adapter<ListCoursAdapter.MyViewHolder> {
    private final Activity context;
    private final String[] titreArray;
    private final String[] descriptionArray;

    public ListCoursAdapter(Activity context, String[] titreArray, String[] descriptionArray){
        this.context = context;
        this.titreArray = titreArray;
        this.descriptionArray = descriptionArray;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_courses, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LinearLayout layout = holder.layout;
        TextView titre = layout.findViewById(R.id.textViewTitreModel);
        titre.setText(titreArray[position]);
        TextView description = layout.findViewById(R.id.textViewDescriptionModel);
        description.setText(descriptionArray[position]);
    }

    @Override
    public int getItemCount() {
        return titreArray.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            layout = v;
        }
    }
}
