package com.master.mobile.freedemy.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.master.mobile.freedemy.R;
import com.master.mobile.freedemy.classes.data.FireBaseDataAccess;
import com.master.mobile.freedemy.classes.models.CoursModel;

import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) root.findViewById(R.id.cours_listView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        try {
            GifDrawable gifFromAssets = new GifDrawable( getActivity().getAssets(), "loading.gif" );
            GifImageView gifImage = root.findViewById(R.id.loading_spinner);
            gifImage.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkUserConnected(root);
        initData(root);
        return root;
    }

    private void initData(View root) {
        initCours(root);
        initVideo(root);
    }

    private void initCours(View root) {

        new FireBaseDataAccess().getCoursData(this, root);

        LinearLayout loading_spinner_layout = root.findViewById(R.id.loading_layout);
        loading_spinner_layout.setVisibility(View.VISIBLE);
        LinearLayout aucun_cours_layout = root.findViewById(R.id.aucun_cours);
        aucun_cours_layout.setVisibility(View.GONE);
    }

    public void putDataCours(HomeViewModel homeViewModel, View root) {
        if(!homeViewModel.getListeCours().isEmpty()) {
            LinearLayout aucun_cours_layout = root.findViewById(R.id.aucun_cours);
            aucun_cours_layout.setVisibility(View.GONE);
            LinearLayout loading_spinner_layout = root.findViewById(R.id.loading_layout);
            loading_spinner_layout.setVisibility(View.GONE);
        }
        ListCoursAdapter coursAdapter = new ListCoursAdapter(getActivity(), homeViewModel.getListeCours());
        recyclerView.setAdapter(coursAdapter);
    }

    public void putErrorCours(View root) {
        LinearLayout aucun_cours_layout = root.findViewById(R.id.aucun_cours);
        aucun_cours_layout.setVisibility(View.VISIBLE);
        LinearLayout loading_spinner_layout = root.findViewById(R.id.loading_layout);
        loading_spinner_layout.setVisibility(View.GONE);
    }

    private void initVideo(View root) {

    }

    private void checkUserConnected(View root) {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            TextView view = root.findViewById(R.id.mailMainTextView);
            if(view != null) view.setText(user.getEmail());
        }
    }
}