package com.example.nur_muz1.ui.music_play;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nur_muz1.R;
import com.example.nur_muz1.databinding.MusicPlayBinding;
import com.example.nur_muz1.ui.home.HomeFragment;

import java.io.IOException;
import java.util.Locale;

public class MusicPlay extends Fragment {

    private static final String ARG_SONG_ID = "song_id";

    private MusicPlayBinding musicPlayBinding;
    private MediaPlayer mediaPlayer;
    private TextView textViewSongTitle;
    private SeekBar seekBar;
    private Button buttonPlayPause;

    private Handler handler = new Handler();
    private boolean isPlaying = false;

    private TextView textTime1;
    private TextView textTime2;

    private int songId;

    public static MusicPlay newInstance(int songId) {
        MusicPlay fragment = new MusicPlay();
        Bundle args = new Bundle();
        args.putInt(ARG_SONG_ID, songId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songId = getArguments().getInt(ARG_SONG_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        musicPlayBinding = MusicPlayBinding.inflate(inflater, container, false);
        View view = musicPlayBinding.getRoot();

        textViewSongTitle = musicPlayBinding.textView7;
        seekBar = musicPlayBinding.seekBar;
        buttonPlayPause = musicPlayBinding.buttonPlayPause;
        textTime1 = musicPlayBinding.textTime1;
        textTime2 = musicPlayBinding.textTime2;

        initializeMediaPlayer();

        buttonPlayPause.setOnClickListener(v -> togglePlayPause());

        ImageButton imageButton = view.findViewById(R.id.musicexit);
        imageButton.setOnClickListener(this::onImageButtonClick);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setMax(mediaPlayer.getDuration());
        updateSeekBar();
        textTime2.setText(getFormattedTime(mediaPlayer.getDuration()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(seekBarUpdateRunnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateSeekBar();
            }
        });

        return view;
    }


        private void initializeMediaPlayer() {
        int songResId = R.raw.musiz;  // Default to song1
        if (songId == 1) {
            songResId = R.raw.carousel;
        } else if (songId == 2) {
            songResId = R.raw.muz1;
        }
        mediaPlayer = MediaPlayer.create(requireContext(), songResId);
    }

    public void onImageButtonClick(View view) {
        mediaPlayer.stop();
        HomeFragment newFragment = new HomeFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void updateSeekBar() {
        handler.postDelayed(seekBarUpdateRunnable, 100);
    }

    private Runnable seekBarUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            updateElapsedTime();
            handler.postDelayed(this, 100);
        }
    };

    private void updateElapsedTime() {
        textTime1.setText(getFormattedTime(mediaPlayer.getCurrentPosition()));
    }

    private String getFormattedTime(int milliseconds) {
        int seconds = (milliseconds / 1000) % 60;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    private void playMusic() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
            musicPlayBinding.buttonPlayPause.setText("❚❚");
        }
    }

    private void togglePlayPause() {
        if (isPlaying) {
            pauseMusic();
        } else {
            playMusic();
        }
    }

    private void pauseMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            musicPlayBinding.buttonPlayPause.setText("▶");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseMusic();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mediaPlayer.release();
        mediaPlayer = null;
        handler.removeCallbacksAndMessages(null);
    }
}
