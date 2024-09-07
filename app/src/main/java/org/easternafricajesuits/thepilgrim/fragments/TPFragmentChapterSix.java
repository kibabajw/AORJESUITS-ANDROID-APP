package org.easternafricajesuits.thepilgrim.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.TpFragmentChapterSixBinding;

public class TPFragmentChapterSix extends Fragment {

    private TpFragmentChapterSixBinding binding;

    public TPFragmentChapterSix() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TpFragmentChapterSixBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}
