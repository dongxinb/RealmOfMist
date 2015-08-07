package edu.zju.realmofmist.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.zju.realmofmist.R;
import edu.zju.realmofmist.adapter.RankingListAdapter;
import edu.zju.realmofmist.api.API;
import edu.zju.realmofmist.api.APICallback;
import edu.zju.realmofmist.model.User;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * A placeholder fragment containing a simple view.
 */
public class RankingActivityFragment extends Fragment {

    private ListView mRankingListView;
    private RankingListAdapter mAdapter;
    private PtrClassicFrameLayout mPtrFrame;

    public RankingActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);

        linkGUI2Val(rootView);
        populateList();
        setPtrFrame(rootView);
        return rootView;
    }


    private void linkGUI2Val (View rootView) {
        mRankingListView = (ListView) rootView.findViewById(R.id.RankingListView);
    }

    private void populateList() {
        // make sure list view knows how to inflate the GUI element

        RankingListAdapter.SetLayoutInflater(getActivity());

        //ListElementAdapter.initialzePretendData(getActivity().getAssets());

        mAdapter = new RankingListAdapter();

        mRankingListView.setAdapter(mAdapter);
    }

    private void setPtrFrame(View rootView){
        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
//                frame.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        updateData();
//                    }
//                }, 1800);
                API.getAreaList(User.getCurrentUser().getId(), new APICallback() {
                    @Override
                    public void onSuccess(JSONObject object) {

                    }

                    @Override
                    public void onSuccess(JSONArray array) {
                        mAdapter.setRankingList(array);
                        updateData();
                    }

                    @Override
                    public void onFailure(String description) {
                        Toast toast = Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT);
                        toast.show();
                        updateData();
                    }
                });
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
    }

    protected void updateData(){
        mPtrFrame.refreshComplete();
        Log.d("Ranking", "refresh complete ");
    }

}
