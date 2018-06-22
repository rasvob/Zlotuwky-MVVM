package com.example.rasvob.zlotuwky.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rasvob.zlotuwky.R;
import com.example.rasvob.zlotuwky.activities.main.MainActivityViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class SetOwnRateFragmentDialog extends DialogFragment {
    MainActivityViewModel vm;

    @BindView(R.id.input_own_rate)
    EditText rate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_own_rate, container, false);
        ButterKnife.bind(this, view);
        vm = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainActivityViewModel.class);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @OnClick(R.id.btn_own_rate_ok)
    public void onClicked() {
        String rateStr = rate.getText().toString();
        boolean res = vm.setRate(vm.getOfficeRate(), rateStr);

        if (!res) {
            rate.setError(getString(R.string.bad_input));
            return;
        }

        dismiss();
    }

    @OnClick(R.id.btn_own_rate_cancel)
    public void cancelClicked() {
        dismiss();
    }
}
