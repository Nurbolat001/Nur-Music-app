package com.example.nur_muz1.ui.favorites;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nur_muz1.R;
import com.example.nur_muz1.databinding.FavoritesBinding;

public class Favorites extends Fragment {

    private FavoritesBinding favoritesBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        favoritesBinding = FavoritesBinding.inflate(inflater, container, false);
        View root = favoritesBinding.getRoot();

        // Access the VideoView from favoritesBinding
        VideoView videoView = favoritesBinding.videoView3;

        // Set up the video path and start playing
        String videoPath = "android.resource://" + requireActivity().getPackageName() + "/" + R.raw.free55;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        favoritesBinding = null;
    }
}
