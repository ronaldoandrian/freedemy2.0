package com.master.mobile.freedemy.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.master.mobile.freedemy.DarkModePrefManager;
import com.master.mobile.freedemy.MainActivity;
import com.master.mobile.freedemy.R;
import com.master.mobile.freedemy.account.LoginActivity;
import com.master.mobile.freedemy.classes.data.FireBaseDataAccess;

public class SettingsFragment extends Fragment {
    private Switch darkModeSwitch;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mAuth = FirebaseAuth.getInstance();
        setDarkModeSwitch(view);
        setLogoutAction(view);
        setEditPseudo(view);
        initComponent(view);
        TextView usernameTextView = view.findViewById(R.id.usernameTextView);
        //usernameTextView.setText(((MainActivity)getActivity()).getUserModel().getPseudo());
        return view;
    }

    private void initComponent(View root) {
        TextView mailTextView = root.findViewById(R.id.mailTextView);
        FirebaseUser user = mAuth.getCurrentUser();
        mailTextView.setText(user.getEmail());
    }

    private void setEditPseudo(View root) {
        TextView logout = root.findViewById(R.id.editPseudoTextView);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditPseudoDialog(root);
            }
        });
    }

    private void setLogoutAction(View root) {
        TextView logout = root.findViewById(R.id.logoutTextView);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertLogout();
            }
        });
    }

    private void setEditPseudoDialog(View root) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Nouveau pseudo");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pseudo = input.getText().toString();
                if(pseudo.isEmpty()) return;
                FirebaseUser user = mAuth.getCurrentUser();
                new FireBaseDataAccess().updateUser(user.getUid(), pseudo, SettingsFragment.this, root);
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void setAlertLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Déconnexion");
        builder.setMessage("Vous voulez vous déconnecter?");
        builder.setCancelable(false);
        builder.setPositiveButton("Oui", (dialog, id) -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        });
        builder.setNegativeButton("Non", (dialog, id) -> {  });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setDarkModeSwitch(View root){
        darkModeSwitch = root.findViewById(R.id.darkModeSwitch);
        DarkModePrefManager manager = new DarkModePrefManager(getActivity());
        darkModeSwitch.setChecked(new DarkModePrefManager(getActivity()).isNightMode());
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DarkModePrefManager darkModePrefManager = new DarkModePrefManager(getActivity());
                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                getActivity().recreate();
            }
        });
    }

    public void putData(String pseudo, View root) {
        TextView usernameTextView = root.findViewById(R.id.usernameTextView);
        usernameTextView.setText(pseudo);
        TextView freedemy_user_name = getActivity().findViewById(R.id.freedemy_user_name);
        freedemy_user_name.setText(pseudo);
    }

    public void putError() {
        Toast.makeText(getContext(), "Le changement de votre pseudo a échoué.", Toast.LENGTH_SHORT).show();
    }
}
