package org.easternafricajesuits.thepilgrim.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.databinding.TpFragmentNotesToChapterOneBinding;


public class TPNotesToChapterOne extends Fragment {

    private TpFragmentNotesToChapterOneBinding binding;

    public TPNotesToChapterOne() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TpFragmentNotesToChapterOneBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}
