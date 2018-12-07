package com.demo.ui.uiapplication.viewpagetransformer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.ui.uiapplication.R;

/**
 * Created by Vincent.Lei on 2018/7/13.
 * Title：
 * Note：
 */
public class PageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welcome11, null);
    }
}
