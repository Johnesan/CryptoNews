package com.princess.android.cryptonews.settings.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.binding.FragmentDataBindingComponent;
import com.princess.android.cryptonews.commons.AutoClearedValue;
import com.princess.android.cryptonews.databinding.FragmentManageEditBinding;
import com.princess.android.cryptonews.settings.Activity.ManageBlogSettings;
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

    ProgressDialog progressDialog;


    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<FragmentManageEditBinding> binding;

    Bundle b;

    boolean savedItem;


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
                    binding.get().websiteTitle.setText(preferenceUtils.getFirstTitle());
                    binding.get().websiteUrl.setText(preferenceUtils.getFirstUrl());
                    break;
                case SECOND_URL:
                    binding.get().websiteTitle.setText(preferenceUtils.getSecondTitle());
                    binding.get().websiteUrl.setText(preferenceUtils.getSecondUrl());
                    break;
                case THIRD_URL:
                    binding.get().websiteTitle.setText(preferenceUtils.getThirdTitle());
                    binding.get().websiteUrl.setText(preferenceUtils.getThirdUrl());
                    break;
                case  FOURTH_URL:
                    binding.get().websiteTitle.setText(preferenceUtils.getFourthTitle());
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
                    progressDialog =  new ProgressDialog(getContext());
                    progressDialog.setCancelable(false);


                    if (validUrlViewModel.isValidUrl()){

                        progressDialog.dismiss();

                    } else {

                      progressDialog.dismiss();

                    }


                }
            }

        });
        binding.get().saveButton.setOnClickListener(view -> {

            switch (value){
                case  FIRST_URL:
                    preferenceUtils.storeFirstUrl(binding.get().websiteUrl.getText().toString());
                    preferenceUtils.storeFirstTitle(binding.get().websiteTitle.getText().toString());
                    break;
                case SECOND_URL:
                    preferenceUtils.storeSecondTitle(binding.get().websiteUrl.getText().toString());
                    preferenceUtils.storeSecondUrl(binding.get().websiteTitle.getText().toString());
                    break;
                case THIRD_URL:
                    preferenceUtils.storeThirdUrl(binding.get().websiteUrl.getText().toString());
                    preferenceUtils.storeThirdTitle(binding.get().websiteTitle.getText().toString());
                    break;
                case  FOURTH_URL:
                    preferenceUtils.storeFourthUrl(binding.get().websiteUrl.getText().toString());
                    preferenceUtils.storeFourthTitle(binding.get().websiteTitle.getText().toString());
                    break;
                default:
                    break;
            }

        savedItem = true;
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        validUrlViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(ValidUrlViewModel.class);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
         if (id == android.R.id.home) {
            //This method returns the User to the same state of the previous  Activity

             if (savedItem){
                 Intent  intent = new Intent(getActivity(), ManageBlogSettings.class);
                 startActivity(intent);
             }
             else {
                 NavUtils.navigateUpFromSameTask(getActivity());
             }


            return true;
        }

        return super.onOptionsItemSelected(item);
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
