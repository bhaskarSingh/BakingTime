package com.example.bhaskarkumar.bakingtime;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.bhaskarkumar.bakingtime.object.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeDetailStep extends AppCompatActivity {

    private static final String LOG_TAG = RecipeDetailStep.class.getSimpleName();
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer  mExoPlayer;
    private Uri mUri;
    private TextView mDetailDescription, mIntroDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_step);

        mDetailDescription = findViewById(R.id.detailDescription);
        mIntroDescription = findViewById(R.id.introDescription);
        Steps step = getIntent().getExtras().getParcelable(RecipeSteps.STEPS_DETAIL_KEY);
        String bake = getIntent().getStringExtra(MainActivity.RECIPE_NAME_KEY);
        setTitle(bake);
        Log.i(LOG_TAG, step.getShortDescription() + " ");
        Log.i(LOG_TAG, step.getDescription() + " ");
        Log.i(LOG_TAG, step.getThumbnailURL() + " ");
        Log.i(LOG_TAG, step.getVideoURL() + " ");

        mPlayerView = findViewById(R.id.playerView);
        String thumbnailUrl = step.getThumbnailURL();
        String videoUrl = step.getVideoURL();
        if (!(thumbnailUrl.length() == 0)){
            mUri = Uri.parse(thumbnailUrl);
            Log.i(LOG_TAG, mUri.toString());
            Log.i(LOG_TAG, videoUrl + " : VideoUrl");
        }else if (!(videoUrl.length() == 0)){
            mUri = Uri.parse(videoUrl);
            Log.i(LOG_TAG, mUri.toString());
            Log.i(LOG_TAG, thumbnailUrl + " thumbnail url");
        }else {
            mUri = null;
            Log.i(LOG_TAG, "NO url");
        }
        initializeExoPlayer(mUri);
        mDetailDescription.setText(step.getDescription());
        if (step.getId() != 0) {
            mIntroDescription.setText(step.getShortDescription());
        }

    }

    private void initializeExoPlayer(Uri uri) {
        if (mExoPlayer == null && uri != null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl controller = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, controller);
            mPlayerView.setPlayer(mExoPlayer);

            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "BakingTime"), null);
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource(uri, dataSourceFactory,
                    new DefaultExtractorsFactory(), null, null);
            // Prepare the player with the source.
            mExoPlayer.prepare(videoSource);

            mExoPlayer.setPlayWhenReady(true);
        }else {
            mPlayerView.setVisibility(View.GONE);
        }

    }

    public void releasePlayer(){
        mExoPlayer.stop();
        mExoPlayer.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPlayerView.getVisibility() == View.VISIBLE) {
            releasePlayer();
        }
    }
}
