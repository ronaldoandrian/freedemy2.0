package com.master.mobile.freedemy.ui.cours;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.master.mobile.freedemy.R;
import com.master.mobile.freedemy.classes.data.FireBaseDataAccess;
import com.master.mobile.freedemy.classes.models.CoursModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class CoursActivity extends AppCompatActivity {
    private String cours_id;
    private String API_KEY = "AIzaSyABC2-2UrfFJ-WWh4HpCxsUowk0Fd5bPg4";
    private YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cours);
        Intent intent = getIntent();
        cours_id = intent.getStringExtra("COURS_ID");
        initComponent();
    }

    private void initComponent() {
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.setVisibility(View.GONE);
        try {
            GifDrawable gifFromAssets = new GifDrawable(getAssets(), "loading.gif" );
            GifImageView gifImage = findViewById(R.id.loading_spinner);
            gifImage.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loading();
        new FireBaseDataAccess().getCoursById(cours_id, this);
    }

    public void putData(CoursModel model) {
        success();
        TextView titre_details_cours = findViewById(R.id.titre_details_cours);
        titre_details_cours.setText(model.getTitre());
        TextView description_details_cours = findViewById(R.id.description_details_cours);
        description_details_cours.setText(model.getDescription());
        TextView contenu_cours = findViewById(R.id.contenu_cours);
        contenu_cours.setText(Html.fromHtml(model.getContenu()));

        youTubePlayerView.setVisibility(View.VISIBLE);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(model.getVideoId(), 0);
            }
        });
    }

    public void putErrorCours() {
        LinearLayout aucun_cours_layout = findViewById(R.id.aucun_cours);
        aucun_cours_layout.setVisibility(View.VISIBLE);
        LinearLayout loading_spinner_layout = findViewById(R.id.loading_layout);
        loading_spinner_layout.setVisibility(View.GONE);
    }

    private void loading() {
        LinearLayout aucun_cours_layout = findViewById(R.id.aucun_cours);
        aucun_cours_layout.setVisibility(View.GONE);
        LinearLayout loading_spinner_layout = findViewById(R.id.loading_layout);
        loading_spinner_layout.setVisibility(View.VISIBLE);
    }

    private void success() {
        LinearLayout aucun_cours_layout = findViewById(R.id.aucun_cours);
        aucun_cours_layout.setVisibility(View.GONE);
        LinearLayout loading_spinner_layout = findViewById(R.id.loading_layout);
        loading_spinner_layout.setVisibility(View.GONE);
    }
}
