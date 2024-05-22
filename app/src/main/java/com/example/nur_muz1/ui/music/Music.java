package com.example.nur_muz1.ui.music;

import static androidx.databinding.DataBindingUtil.setContentView;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.nur_muz1.R;
import com.example.nur_muz1.databinding.HomeBinding;
import com.example.nur_muz1.databinding.MusicBinding;
import com.example.nur_muz1.ui.home.HomeViewModel;

public class Music extends Fragment {

    private MusicBinding musicBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MusicViewModel musicViewModel =  new ViewModelProvider(this).get(MusicViewModel.class);

        musicBinding = MusicBinding.inflate(inflater, container, false);
        View root = musicBinding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        musicBinding = null;
    }
}
