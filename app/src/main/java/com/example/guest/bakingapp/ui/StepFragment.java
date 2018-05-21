package com.example.guest.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.bakingapp.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by l1maginaire on 4/29/18.
 */

public class StepFragment extends Fragment implements ExoPlayer.EventListener {
    public static final String DESCRIPTION = "single_step_description";
    public static final String VIDEO_URL = "single_step_url";
    private static final String THUMBNAIL = "single_step_thumbnail";
    private static final String PLAY_POSITION = "play_position";

    private String description;
    private String videoUrl;
    private String thumbnail;
    private long playPosition;


    @BindView(R.id.video_view)
    SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.step_thumbnail)
    ImageView stepThumbnailImageView;
    @BindView(R.id.fragment_pager_nested_scrollview)
    NestedScrollView nestedScrollView;
    @BindView(R.id.pager_description)
    protected TextView tv;
    @BindView(R.id.pager_fragment_back_arrow)
    protected ImageView backArrow;
    SimpleExoPlayer exoPlayer;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private Unbinder unbinder;

    public StepFragment() {
    }

    public static Fragment newInstance(String videoUrl, String description, String thumbnail) {
        Bundle args = new Bundle();
        args.putString(DESCRIPTION, description);
        args.putString(VIDEO_URL, videoUrl);
        args.putString(THUMBNAIL, thumbnail);
        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        description = getArguments().getString(DESCRIPTION);
        videoUrl = getArguments().getString(VIDEO_URL);
        thumbnail = getArguments().getString(THUMBNAIL);
        if (savedInstanceState != null)
            playPosition = savedInstanceState.getLong(PLAY_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_steps, container, false);
        unbinder = ButterKnife.bind(this, v);
        tv.setText(description);
        if (getActivity() != null && !(getActivity().getLocalClassName().equals("com.example.guest.bakingapp.ui.MainActivity"))) { //twopane mode detector
            backArrow.setVisibility(View.VISIBLE);
            backArrow.setOnClickListener(viev -> getActivity().onBackPressed());
        }
        if (thumbnail != null && !thumbnail.isEmpty()) {
            stepThumbnailImageView.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(thumbnail).into(stepThumbnailImageView);
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (videoUrl != null && !videoUrl.isEmpty()) {
            initializeMediaSession();
            initializePlayer(Uri.parse(videoUrl));
        } else {
            exoPlayerView.setVisibility(View.GONE);
        }
    }

    private void initializeMediaSession() {
        mediaSession = new MediaSessionCompat(getContext(), "StepFragment");
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                exoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onPause() {
                super.onPause();
                exoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                exoPlayer.seekTo(0);
            }
        });
        mediaSession.setActive(true);
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), "StepVideo");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == Player.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == Player.STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }

        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            playPosition = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAY_POSITION, playPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayer != null) {
            exoPlayer.seekTo(playPosition);
        } else if (videoUrl != null && !videoUrl.isEmpty()) {
            initializeMediaSession();
            initializePlayer(Uri.parse(videoUrl));
            exoPlayer.setPlayWhenReady(true);
            exoPlayer.seekTo(playPosition);
        }
    }
}
