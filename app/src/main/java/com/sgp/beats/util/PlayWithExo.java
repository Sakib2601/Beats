package com.sgp.beats.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media.MediaBrowserServiceCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
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
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sgp.beats.R;


public class PlayWithExo implements Player.EventListener {

    private ExoPlayer exoPlayer;
    private Context context;
    private String mUrl;
    private boolean isOffline;
    private PlayerView playerView;
    private PlayerControlView controlView;

    public PlayWithExo(PlayerView playerView, Context context, String mUrl, boolean isOffline, PlayerControlView controlView) {
        this.context = context;
        this.mUrl = mUrl;
        this.isOffline = isOffline;
        this.playerView = playerView;
        this.controlView = controlView;
        initExoPlayer();

    }



    public ExoPlayer getExoPlayer() {
        return exoPlayer;
    }

    private void initExoPlayer() {

        TrackSelector trackSelector = new DefaultTrackSelector();
        DefaultLoadControl loadControl = new DefaultLoadControl();
        DefaultRenderersFactory rendererFactory = new DefaultRenderersFactory(context);
        exoPlayer = ExoPlayerFactory.newSimpleInstance(rendererFactory, trackSelector, loadControl);
        controlView.setPlayer(exoPlayer);
        playerView.setShowBuffering(true);

        if (isOffline) {
            play(mUrl);
        } else {
            playOffline(mUrl);
        }
    }

    private void play(String url) {

        MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getString(R.string.app_name))))
                .setExtractorsFactory(new DefaultExtractorsFactory())
                .createMediaSource(Uri.parse(url));
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
    }

    private ExoPlayer getPlayerImpl(Context context) {
        this.context = context;
        initExoPlayer();
        return exoPlayer;
    }

    private void playOffline(String uri) {

        String playerInfo = Util.getUserAgent(context, context.getString(R.string.app_name));
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                context, playerInfo
        );

        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(new DefaultExtractorsFactory())
                .createMediaSource(Uri.parse(uri));
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
        //noinspection deprecation
        exoPlayer.setRepeatMode(exoPlayer.REPEAT_MODE_ALL);
    }


    public void releasePlayer() {

        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
            exoPlayer.release();
            exoPlayer = null;
            playerView.getPlayer().release();
            playerView = null;
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

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
}
