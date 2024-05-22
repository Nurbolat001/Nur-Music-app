package com.example.nur_muz1.ui.recomended;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nur_muz1.R;
import com.example.nur_muz1.databinding.RecomendedLayoutBinding;
import com.example.nur_muz1.ui.music_play.MusicPlay;

public class Recomended extends Fragment {

    RecomendedLayoutBinding recomendedLayoutBinding;

    LinearLayout linearLayout;

    LinearLayout linearLayout2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recomendedLayoutBinding = RecomendedLayoutBinding.inflate(inflater,container,false);
        linearLayout = recomendedLayoutBinding.music1;
        linearLayout2 = recomendedLayoutBinding.music2;

        linearLayout.setOnClickListener(View -> openMusicPlayFragment(1));
        linearLayout2.setOnClickListener(View -> openMusicPlayFragment(2));
        return recomendedLayoutBinding.getRoot();
    }

    private void openMusicPlayFragment(int songId) {
        MusicPlay newFragment = MusicPlay.newInstance(songId);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
