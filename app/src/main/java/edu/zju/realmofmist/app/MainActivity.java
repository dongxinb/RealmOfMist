package edu.zju.realmofmist.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import edu.zju.realmofmist.R;

public class MainActivity extends AppCompatActivity {

    FloatingActionsMenu mMenu;
    FloatingActionButton mMenuProfile;
    FloatingActionButton mMenuRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkView();
    }

    private void linkView() {
        mMenu = (FloatingActionsMenu)findViewById(R.id.menu_actions);
        mMenuProfile = (FloatingActionButton)findViewById(R.id.menu_profile);
        mMenuRanking = (FloatingActionButton)findViewById(R.id.menu_ranking);

        View.OnClickListener menuItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mMenuProfile) {
                    Log.d("Main", "Profile menu pressed.");
                }else if (v == mMenuRanking) {
                    Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                    startActivityForResult(intent, 1000);
                    Log.d("Main", "Ranking menu pressed.");
                }
            }
        };
        mMenuProfile.setOnClickListener(menuItemListener);
        mMenuRanking.setOnClickListener(menuItemListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
