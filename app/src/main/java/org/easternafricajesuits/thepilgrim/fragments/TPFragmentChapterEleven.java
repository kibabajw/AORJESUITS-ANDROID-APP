package org.easternafricajesuits.thepilgrim.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.TpFragmentChapterElevenBinding;

public class TPFragmentChapterEleven extends Fragment {

    private TpFragmentChapterElevenBinding binding;

    public TPFragmentChapterEleven() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TpFragmentChapterElevenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}
