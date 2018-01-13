package com.example.bhaskarkumar.bakingtime.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhaskarkumar.bakingtime.R;
import com.example.bhaskarkumar.bakingtime.RecipeDetailStep;
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


public class RecipeDetailStepFragment extends Fragment {

    private static final String LOG_TAG = RecipeDetailStep.class.getSimpleName();
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private Uri mUri;
    private TextView mDetailDescription, mIntroDescription;
    private Steps step;

    public RecipeDetailStepFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps_detail, container, false);

        mDetailDescription = view.findViewById(R.id.detailDescription);
        mIntroDescription = view.findViewById(R.id.introDescription);

        mPlayerView = view.findViewById(R.id.playerView);
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

        return view;
    }

    private void initializeExoPlayer(Uri uri) {
        if (mExoPlayer == null && uri != null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl controller = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, controller);
            mPlayerView.setPlayer(mExoPlayer);

            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                    Util.getUserAgent(getActivity(), "BakingTime"), null);
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

    public void setSteps(Steps mSteps){
        step = mSteps;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayerView.getVisibility() == View.VISIBLE) {
            releasePlayer();
        }
    }
}
