package com.example.rasvob.zlotuwky.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rasvob.zlotuwky.R;

import java.nio.Buffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private Runnable visaCallback;
    private Runnable officeCallback;

    public void setVisaCallback(Runnable visaCallback) {
        this.visaCallback = visaCallback;
    }

    public void setOfficeCallback(Runnable officeCallback) {
        this.officeCallback = officeCallback;
    }

    @BindView(R.id.btn_sheet_download_visa)
    Button btnVisa;

    @BindView(R.id.btn_sheet_kantor)
    Button btnOffice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_sheet_download_visa)
    public void visaClicked() {
        visaCallback.run();
        dismiss();
    }

    @OnClick(R.id.btn_sheet_kantor)
    public void ownRateClicked() {
        officeCallback.run();
        dismiss();
    }
}
