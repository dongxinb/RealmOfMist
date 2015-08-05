package edu.zju.realmofmist.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import edu.zju.realmofmist.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    FloatingActionsMenu mMenu;
    FloatingActionButton mMenuProfile;
    FloatingActionButton mMenuRanking;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        linkView(view);
        return view;
    }

    private void linkView(View view) {
        mMenu = (FloatingActionsMenu)view.findViewById(R.id.menu_actions);
        mMenuProfile = (FloatingActionButton)view.findViewById(R.id.menu_profile);
        mMenuRanking = (FloatingActionButton)view.findViewById(R.id.menu_ranking);

        View.OnClickListener menuItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (v == mMenuProfile) {
                    Log.d("Main", "Profile menu pressed.");
                }else if (v == mMenuRanking) {
                    intent = new Intent(getActivity(),RankingActivity.class);
                    startActivityForResult(intent, 1000);
                    Log.d("Main", "Ranking menu pressed.");
                }
            }
        };
        mMenuProfile.setOnClickListener(menuItemListener);
        mMenuRanking.setOnClickListener(menuItemListener);
    }
}
