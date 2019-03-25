package com.demo.ui.uiapplication.aspectj;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.demo.ui.uiapplication.R;
import com.slib.hooktrack.IgnoreSuperMethod;

/**
 * Created by Vincent.Lei on 2019/2/22.
 * Title：
 * Note：
 */
public class AspectFragment extends Fragment {
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aspect, null);
        listView = view.findViewById(R.id.lv);
        listView.setAdapter(new Adapter());
        return view;
    }

    @IgnoreSuperMethod
    @Override
    public void onResume() {
        super.onResume();
    }

    @IgnoreSuperMethod
    @Override
    public void onPause() {
        super.onPause();
    }

    @IgnoreSuperMethod
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @IgnoreSuperMethod
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private class Adapter extends BaseAdapter implements View.OnClickListener {
        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.adapter_item_aspect_test, null);
                convertView.findViewById(R.id.iv).setOnClickListener(this);
                convertView.findViewById(R.id.tv).setOnClickListener(this);
            }
            return convertView;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
