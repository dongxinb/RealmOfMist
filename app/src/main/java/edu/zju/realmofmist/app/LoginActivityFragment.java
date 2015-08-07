package edu.zju.realmofmist.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.smssdk.SMSSDK;
import edu.zju.realmofmist.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        SMSSDK.initSDK(getActivity(), "96cf4fe49b38", "2f3a5163ca2b1145ee6c4bbafc3b2bcd");

        return view;
    }
}
