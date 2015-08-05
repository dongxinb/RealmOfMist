package edu.zju.realmofmist.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.zju.realmofmist.R;
import edu.zju.realmofmist.adapter.RankingListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class RankingActivityFragment extends Fragment {

    private ListView mRankingListView;
    private RankingListAdapter adapter;

    public RankingActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);

        linkGUI2Val(rootView);
        populateList();

        return rootView;
    }


    private void linkGUI2Val (View rootView) {
        mRankingListView = (ListView) rootView.findViewById(R.id.RankingListView);
    }

    private void populateList() {
        // make sure list view knows how to inflate the GUI element

        RankingListAdapter.SetLayoutInflater(getActivity());

        //ListElementAdapter.initialzePretendData(getActivity().getAssets());

        adapter = new RankingListAdapter();

        mRankingListView.setAdapter(adapter);
    }

}
