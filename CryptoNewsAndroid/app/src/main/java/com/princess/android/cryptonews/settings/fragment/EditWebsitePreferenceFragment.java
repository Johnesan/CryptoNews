package com.princess.android.cryptonews.settings.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.binding.FragmentDataBindingComponent;
import com.princess.android.cryptonews.commons.AutoClearedValue;
import com.princess.android.cryptonews.databinding.FragmentManageEditBinding;
import com.princess.android.cryptonews.settings.viewmodel.ValidUrlViewModel;

import javax.inject.Inject;

/**
 * Created by numb3rs on 3/6/18.
 */

public class EditWebsitePreferenceFragment extends Fragment {


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ValidUrlViewModel validUrlViewModel;

    String testUrl;


    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<FragmentManageEditBinding> binding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentManageEditBinding manageEditBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_manage_edit, container,false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, manageEditBinding);

        setupViews();
         return  manageEditBinding.getRoot();
    }

    private void setupViews() {

        binding.get().testButton.setOnClickListener(view -> {


            if (binding.get().websiteUrl.toString() == null || TextUtils.isEmpty(binding.get().websiteUrl.toString())) {
                binding.get().websiteUrl.setError("Enter a Valid Url");
            } else {
                testUrl = binding.get().websiteUrl.toString();
            }

            validUrlViewModel.isValidUrl(testUrl);

        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        validUrlViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(ValidUrlViewModel.class);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
