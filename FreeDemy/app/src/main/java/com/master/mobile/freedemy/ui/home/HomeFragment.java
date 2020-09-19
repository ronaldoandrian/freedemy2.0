package com.master.mobile.freedemy.ui.home;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.master.mobile.freedemy.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        checkUserConnected(root);
        return root;
    }

    private void checkUserConnected(View root) {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            TextView view = root.findViewById(R.id.mailMainTextView);
            if(view != null) view.setText(user.getEmail());
        }
    }
}