package org.easternafricajesuits.thepilgrim.fragments;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.TpFragmentChapterThreeBinding;

public class TPFragmentChapterThree extends Fragment {

    private TpFragmentChapterThreeBinding binding;

    public TPFragmentChapterThree() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TpFragmentChapterThreeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}
