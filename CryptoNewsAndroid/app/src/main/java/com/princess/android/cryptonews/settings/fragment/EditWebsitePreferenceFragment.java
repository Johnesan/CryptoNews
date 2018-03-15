package com.princess.android.cryptonews.settings.fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.binding.FragmentDataBindingComponent;
import com.princess.android.cryptonews.commons.AutoClearedValue;
import com.princess.android.cryptonews.databinding.FragmentManageEditBinding;
import com.princess.android.cryptonews.settings.Activity.ManageBlogSettings;
import com.princess.android.cryptonews.settings.viewmodel.ValidUrlViewModel;
import com.princess.android.cryptonews.util.PreferenceUtils;
import com.princess.android.cryptonews.util.ShowAlert;

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

    boolean savedItem = false;

    public  fragmentInteractionListener listener;

    ShowAlert alert = new ShowAlert();

    public EditWebsitePreferenceFragment() {
    }

    public static EditWebsitePreferenceFragment newInstance(String argument){
        EditWebsitePreferenceFragment editWebsitePreferenceFragment = new EditWebsitePreferenceFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL_KEY, argument);
        editWebsitePreferenceFragment.setArguments(bundle);
        return  editWebsitePreferenceFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof fragmentInteractionListener) {
            listener = (fragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;

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

        //This code disables the save Button
        binding.get().saveButton.setClickable(false);
        binding.get().saveButton.setEnabled(false);

        binding.get().websiteUrl.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.get().websiteUrl.setOnEditorActionListener((textView, i, keyEvent) -> {
            if(i == EditorInfo.IME_ACTION_DONE){
                startTest();
                hideKeyBoard();
                return true;
        }
        return false;

        });

        //This code makes the test when the Test Button is Clicked
        binding.get().testButton.setOnClickListener((View view) -> startTest());

        //Save the new data to the appropriate preference
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

            //this wil notify the activity, that the user has saved his data because we want to restart the previous activity to show the changes
            listener.onBackPressed(savedItem);
            alert.showAlertDialog(getActivity(),
                    "Successful",
                    "Your Settings has been successful saved!!",
                    true);
        });

        //if the user Decides to edit the Edit Text after saving, disable the save button to avoid saving wrong data
        binding.get().websiteUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //This code disables the save Button
                binding.get().saveButton.setClickable(false);
                binding.get().saveButton.setEnabled(false);

            }
        });

    }

    private void startTest() {

        //Check if the user entered anything in the textField
        if (TextUtils.isEmpty(binding.get().websiteUrl.getText().toString())) {

            //Tell the user to enter correct url
            binding.get().websiteUrl.setError("Enter a Url in the specified Format");
        } else {
            //get The url from the ui
            testUrl = binding.get().websiteUrl.getText().toString();
            if (isValidUrl(testUrl)) {

                //save the url to sharedpreferences
                preferenceUtils.storeTestUrl(testUrl);

                //show User Dialog
                startDialog();


                validUrlViewModel.isValidUrl().observe(this, news -> {
                    //This will only be called if the url returns data
                    //this way we can know if the url is supported
                    if (news == null || news.size() == 0){
                        progressDialog.dismiss();
                        alert.showAlertDialog(getActivity(),
                                "UnSuccessful",
                                "Url is not Supported",
                                true);
                    }else {
                        //dismiss the url and enable the save button , so user can new url
                        progressDialog.dismiss();
                        binding.get().saveButton.setClickable(true);
                        binding.get().saveButton.setEnabled(true);

                        alert.showAlertDialog(getActivity(),
                                "Successful",
                                "This website has been tested and it meets the requirements to fetch its blog feed. You can now save it",
                                true);
                    }

                });


            }
        }

    }

    private void startDialog() {
        progressDialog =  new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Checking Validity of Url");
        progressDialog.show();
    }

    private void hideKeyBoard(){
        // Check if no view has focus:
        if (getActivity() != null){

            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);}

            }
        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //creating instances of the viewModel
        validUrlViewModel = ViewModelProviders.of(this, viewModelFactory).get(ValidUrlViewModel.class);
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
                 //if the user did not save, just go back to where he came from without changing anything
                 NavUtils.navigateUpFromSameTask(getActivity());
             }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Util to check if the value entered is a valid Url
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

    public interface  fragmentInteractionListener{
        void onBackPressed(boolean isSaved);
    }


}
