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
import org.easternafricajesuits.databinding.JjFragmentMBinding;

public class JJFragmentM extends Fragment {

    private JjFragmentMBinding binding;

    public JJFragmentM() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = JjFragmentMBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}
