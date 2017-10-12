package com.labs.wildhan.assistantcooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class TipsVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static String KEY_ITEM = "item";
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubePlayerView;
    private TipsModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_video);

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(Config.YOUTUBE_API_KEY, this);


    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        model = (TipsModel) getIntent().getSerializableExtra(KEY_ITEM);
        if(!wasRestored){
            player.cueVideo(model.getLink());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()){
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        }else{
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == RECOVERY_REQUEST){
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider(){
        return youTubePlayerView;
    }
}
