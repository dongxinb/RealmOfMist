package edu.zju.realmofmist.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.zju.realmofmist.R;
import edu.zju.realmofmist.model.User;
import edu.zju.realmofmist.view.RoundImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends Fragment {

    RoundImageView userIconImage;
    TextView userNameText;
    TextView phoneNumberText;
    TextView areaText;
    TextView rankingText;

    public ProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        linkView(view);
        return view;
    }

    public void linkView(View view){
        userIconImage = (RoundImageView)view.findViewById(R.id.userIconImage);
        userNameText = (TextView)view.findViewById(R.id.userNameText);
        phoneNumberText = (TextView)view.findViewById(R.id.userPhoneText);
        areaText = (TextView)view.findViewById(R.id.userAreaText);
//        rankingText = (TextView)view.findViewById(R.id.userRankingText);

        phoneNumberText.setText("");
        try {
            areaText.setText(String.format("%d", (int) User.getCurrentUser().getArea()));
//            phoneNumberText.setText(User.getCurrentUser().getName());
            userNameText.setText(User.getCurrentUser().getName());
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}

