package org.easternafricajesuits.jesuitjargon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.JjFragmentLBinding;

public class JJFragmentP extends Fragment {

    private JjFragmentLBinding binding;

    public JJFragmentP() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = JjFragmentLBinding.inflate(inflater, container, false);
//        View view = binding.getRoot();
//        return view;
        return inflater.inflate(R.layout.jj_fragment_p, container, false);
    }
}
