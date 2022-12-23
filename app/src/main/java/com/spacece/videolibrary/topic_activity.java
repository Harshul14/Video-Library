package com.spacece.videolibrary;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class  topic_activity extends AppCompatActivity {

    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_activity);

        TextView discrip_view = findViewById(R.id.DescripTextView);
        String name = "No topic";
        String desc= "No ID";
        String v_id = "Video ID missing";
        Bundle extras = getIntent().getExtras();
        if(extras!= null){
            name=extras.getString("data");
            desc=extras.getString("discrp");
            v_id = extras.getString("v_id");
        }

        discrip_view.setText(desc);
        youTubePlayerView = findViewById(R.id.YoutubePlayerView);
        getLifecycle().addObserver(youTubePlayerView);
        String finalV_id = v_id;
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(finalV_id, 0);
            }
        });
    }

}
