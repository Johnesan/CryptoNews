package com.princess.android.cryptonews.settings.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.binding.FragmentDataBindingComponent;
import com.princess.android.cryptonews.commons.AutoClearedValue;
import com.princess.android.cryptonews.databinding.FragmentManageEditBinding;
import com.princess.android.cryptonews.settings.viewmodel.ValidUrlViewModel;
import com.princess.android.cryptonews.util.PreferenceUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.princess.android.cryptonews.settings.fragment.ManageBlogWebsiteFragment.URL_KEY;
import static com.princess.android.cryptonews.util.PreferenceUtils.FIRST_URL;
import static com.princess.android.cryptonews.util.PreferenceUtils.FOURTH_URL;
import static com.princess.android.cryptonews.util.PreferenceUtils.SECOND_URL;
import static com.princess.android.cryptonews.util.PreferenceUtils.THIRD_URL;

/**
 * Created by numb3rs on 3/6/18.
 */

public class EditWebsitePreferenceFragment extends DaggerFragment {


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    PreferenceUtils preferenceUtils;

    ValidUrlViewModel validUrlViewModel;

    String testUrl;


    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<FragmentManageEditBinding> binding;

    Bundle b;


    public EditWebsitePreferenceFragment() {
    }

    public static EditWebsitePreferenceFragment newInstance(String argument){
        EditWebsitePreferenceFragment editWebsitePreferenceFragment = new EditWebsitePreferenceFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL_KEY, argument);
        editWebsitePreferenceFragment.setArguments(bundle);
        return  editWebsitePreferenceFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentManageEditBinding manageEditBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_manage_edit, container,false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, manageEditBinding);


        b = getArguments();
        setupViews();
         return  manageEditBinding.getRoot();
    }

    private void setupViews() {

        String value  = b.getString(URL_KEY);

        if (value != null) {

            switch (value){
                case  FIRST_URL:
                    binding.get().websiteUrl.setText(preferenceUtils.getFirstUrl());
                    break;
                case SECOND_URL:
                    binding.get().websiteUrl.setText(preferenceUtils.getSecondUrl());
                    break;
                case THIRD_URL:
                    binding.get().websiteUrl.setText(preferenceUtils.getThirdUrl());
                    break;
                case  FOURTH_URL:
                    binding.get().websiteUrl.setText(preferenceUtils.getFourthUrl());
                    break;
                default:
                    break;
            }

        }

        binding.get().saveButton.setClickable(false);

        binding.get().testButton.setOnClickListener(view -> {


            if (TextUtils.isEmpty(binding.get().websiteUrl.getText().toString())) {
                binding.get().websiteUrl.setError("Enter a Url in the specified Format");
            } else {
                testUrl = binding.get().websiteUrl.getText().toString();
                if (isValidUrl(testUrl)) {
                    preferenceUtils.storeTestUrl(testUrl);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setView(R.layout.empty_item);
                    builder.setCancelable(false);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    if (validUrlViewModel.isValidUrl()){

                        alertDialog.dismiss();
                    } else {

                        alertDialog.setCancelable(true);
                        alertDialog.dismiss();
                        binding.get().websiteUrl.setError("Url Not Valid");

                    }


                }
            }





        });
        binding.get().saveButton.setOnClickListener(view -> Toast.makeText(getContext(), "This Feature is Coming Soon", Toast.LENGTH_SHORT));
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

    private  boolean isValidUrl(String url){
        boolean check;

        if (URLUtil.isValidUrl(url)){
            check = true;
        }
        else {
            check = false;
        }


        return  check;
    }


}
