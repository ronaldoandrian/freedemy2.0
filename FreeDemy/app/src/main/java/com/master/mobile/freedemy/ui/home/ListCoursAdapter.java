package com.master.mobile.freedemy.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.master.mobile.freedemy.R;
import com.master.mobile.freedemy.classes.models.CoursModel;
import com.master.mobile.freedemy.ui.cours.CoursActivity;

import java.util.ArrayList;

public class ListCoursAdapter extends RecyclerView.Adapter<ListCoursAdapter.MyViewHolder> {
    private final Activity context;
    private final ArrayList<CoursModel> models;

    public ListCoursAdapter(Activity context, ArrayList<CoursModel> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_courses, parent, false);
        MyViewHolder vh = new MyViewHolder(layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LinearLayout layout = holder.layout;
        TextView titre = layout.findViewById(R.id.textViewTitreModel);
        titre.setText(models.get(position).getTitre());
        TextView description = layout.findViewById(R.id.textViewDescriptionModel);
        description.setText(models.get(position).getDescription());
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CoursActivity.class);
                intent.putExtra("COURS_ID", models.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            layout = v;
        }
    }
}
