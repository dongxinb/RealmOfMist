package edu.zju.realmofmist.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;

import edu.zju.realmofmist.R;
import edu.zju.realmofmist.model.RankingModel;

/**
 * Created by Soa on 15/8/5.
 */
public class RankingListAdapter extends BaseAdapter {
    private int ContainerWidth = 0;

    private TextView mNameText;
    private TextView mAreaText;
    private TextView mRankingText;

    private Vector<RankingModel> mRankingModel;

    private static LayoutInflater sInflater = null;

    public static void SetLayoutInflater(Activity a) {
        sInflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 5;
//        if (mRankingModel == null)
//            return 0;
//        else
//            return mRankingModel.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View useView = view;
        if (null == useView)
            useView = sInflater.inflate(R.layout.ranking_list_element,null);

        mNameText = (TextView)useView.findViewById(R.id.name);
        mAreaText = (TextView)useView.findViewById(R.id.area);
        mRankingText = (TextView)useView.findViewById(R.id.ranking);

        return useView;
    }



}
