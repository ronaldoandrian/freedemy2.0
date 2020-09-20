package com.master.mobile.freedemy.classes.data;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.master.mobile.freedemy.classes.models.CoursModel;
import com.master.mobile.freedemy.ui.cours.CoursActivity;
import com.master.mobile.freedemy.ui.home.HomeFragment;
import com.master.mobile.freedemy.ui.home.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FireBaseDataAccess {
    private FirebaseFirestore db = null;

    public FireBaseDataAccess() {
        db = FirebaseFirestore.getInstance();
    }

    public void saveUser(String id, String pseudo) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", id);
        user.put("pseudo", pseudo);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) { }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) { }
                });
    }

    public void getCoursData(HomeFragment fragment, View root) {
        HomeViewModel model = new HomeViewModel();
        db.collection("cours")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId() + " => " + document.getData());
                                CoursModel coursModel = new CoursModel();
                                coursModel.setId(document.getId());
                                coursModel.setTitre(document.get("titre").toString());
                                coursModel.setDescription(document.get("description").toString());
                                coursModel.setContenu(document.get("contenu").toString());
                                coursModel.setDate(document.get("date").toString());
                                model.getListeCours().add(coursModel);
                            }
                            fragment.putDataCours(model, root);
                        } else {
                            fragment.putErrorCours(root);
                        }
                    }
                });
    }

    public void getCoursById(String cours_id, CoursActivity coursActivity) {
        db.collection("cours").document(cours_id)
                .get()
                .addOnCompleteListener((OnCompleteListener<DocumentSnapshot>) task -> {
                    if (task.isSuccessful()) {
                        CoursModel coursModel = new CoursModel();
                        coursModel.setId(task.getResult().getId());
                        coursModel.setTitre(task.getResult().get("titre").toString());
                        coursModel.setDescription(task.getResult().get("description").toString());
                        coursModel.setContenu(task.getResult().get("contenu").toString());
                        coursModel.setDate(task.getResult().get("date").toString());
                        coursActivity.putData(coursModel);
                    } else {
                        coursActivity.putErrorCours();
                    }
                });
    }

    public void createFakeData() {
        for(int i = 1; i <= 20; i++) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Map<String, Object> cours = new HashMap<>();
            cours.put("titre", "Titre " + i);
            cours.put("description", "Description " + i);
            cours.put("contenu", "Contenu " + i);
            cours.put("date", formatter.format(date));

            db.collection("cours")
                    .add(cours)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) { }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { }
                    });
        }
    }
}
