package com.master.mobile.freedemy.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.master.mobile.freedemy.classes.models.CoursModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private ArrayList<CoursModel> listeCours;

    public HomeViewModel() {
        listeCours = new ArrayList<CoursModel>();
    }

    public ArrayList<CoursModel> getListeCours() {
        return listeCours;
    }

    public void setListeCours(ArrayList<CoursModel> listeCours) {
        this.listeCours = listeCours;
    }
}