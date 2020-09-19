package com.master.mobile.freedemy.ui.cours;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.master.mobile.freedemy.R;

public class CoursFragment extends Fragment {

    private CoursViewModel coursViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        coursViewModel =
                ViewModelProviders.of(this).get(CoursViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cours, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        coursViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}