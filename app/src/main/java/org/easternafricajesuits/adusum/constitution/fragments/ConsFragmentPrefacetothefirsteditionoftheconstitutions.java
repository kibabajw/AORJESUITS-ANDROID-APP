package org.easternafricajesuits.adusum.constitution.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.easternafricajesuits.R;

public class ConsFragmentPrefacetothefirsteditionoftheconstitutions extends Fragment {

    public ConsFragmentPrefacetothefirsteditionoftheconstitutions() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cons_fragment_preface_to_the_first_edition_of_the_constitutions, container, false);
    }
}
