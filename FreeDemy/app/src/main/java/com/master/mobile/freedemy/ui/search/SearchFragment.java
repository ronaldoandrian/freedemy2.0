package com.master.mobile.freedemy.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.master.mobile.freedemy.R;
import com.master.mobile.freedemy.classes.data.FireBaseDataAccess;
import com.master.mobile.freedemy.ui.home.HomeViewModel;
import com.master.mobile.freedemy.ui.home.ListCoursAdapter;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.cours_listView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        try {
            GifDrawable gifFromAssets = new GifDrawable( getActivity().getAssets(), "loading.gif" );
            GifImageView gifImage = root.findViewById(R.id.loading_spinner);
            gifImage.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initSearchView(root);
        return root;
    }

    private void initSearchView(View root) {
        hideLoading(root);
        EditText searchTo = (EditText)root.findViewById(R.id.editTextRecherche);
        searchTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 3) {
                    searching(root);
                    new FireBaseDataAccess().searchByKeyWord(s.toString(), SearchFragment.this, root);
                }
            }
        });
    }

    public void putDataCours(HomeViewModel model, View root) {
        if(model.getListeCours().size() > 0) {
            hideLoading(root);
            ListCoursAdapter coursAdapter = new ListCoursAdapter(getActivity(), model.getListeCours());
            recyclerView.setAdapter(coursAdapter);
        }
        else {
            putErrorCours(root);
        }
    }

    public void putErrorCours(View root) {
        LinearLayout loading_spinner_layout = root.findViewById(R.id.loading_layout);
        loading_spinner_layout.setVisibility(View.GONE);
        LinearLayout aucun_cours_layout = root.findViewById(R.id.aucun_cours);
        aucun_cours_layout.setVisibility(View.VISIBLE);
    }

    private void hideLoading(View root) {
        LinearLayout loading_spinner_layout = root.findViewById(R.id.loading_layout);
        loading_spinner_layout.setVisibility(View.GONE);
        LinearLayout aucun_cours_layout = root.findViewById(R.id.aucun_cours);
        aucun_cours_layout.setVisibility(View.GONE);
    }

    private void searching(View root) {
        LinearLayout loading_spinner_layout = root.findViewById(R.id.loading_layout);
        loading_spinner_layout.setVisibility(View.VISIBLE);
        LinearLayout aucun_cours_layout = root.findViewById(R.id.aucun_cours);
        aucun_cours_layout.setVisibility(View.GONE);
    }
}