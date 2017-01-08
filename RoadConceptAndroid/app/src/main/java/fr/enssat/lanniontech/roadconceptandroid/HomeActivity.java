package fr.enssat.lanniontech.roadconceptandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.enssat.lanniontech.roadconceptandroid.Entities.Map;
import fr.enssat.lanniontech.roadconceptandroid.Utilities.Constants;
import fr.enssat.lanniontech.roadconceptandroid.Utilities.OnNeedLoginListener;
import fr.enssat.lanniontech.roadconceptandroid.Utilities.RoadConceptMapInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends NavigationDrawerActivity implements OnNeedLoginListener {

    private static final int GET_MAP_LIST_REQUEST_CODE = 1500;

    //@BindView(R.id.swipeRefreshLayoutHome) SwipeRefreshLayout swipeRefreshLayout;
    RoadConceptMapInterface roadConceptMapInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"Oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        roadConceptMapInterface = getRetrofitService(RoadConceptMapInterface.class);
        //swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimary));
        //swipeRefreshLayout.setEnabled(true);
        setTitle("Mes cartes");
        getMapList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor memes = sharedPreferences.edit();
            memes.remove(Constants.SHARE_COOKIE);
            memes.apply();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getMapList(){
        Call<List<Map>> mapList = roadConceptMapInterface.getMapList();
        mapList.enqueue(new Callback<List<Map>>() {
            @Override
            public void onResponse(Call<List<Map>> call, Response<List<Map>> response) {
                if (response.isSuccessful()){
                    for (Map map:
                         response.body()) {
                        Log.d(TAG,map.toString());
                    }
                } else {
                    if (response.code() == 401){
                        Log.d(TAG,"401,try");
                        refreshLogin(HomeActivity.this,GET_MAP_LIST_REQUEST_CODE);
                    } else {
                        displayNetworkErrorDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Map>> call, Throwable t) {
                displayNetworkErrorDialog();
            }
        });
    }


    @Override
    public void onNeedLogin(int code, boolean result) {
        switch (code){
            case GET_MAP_LIST_REQUEST_CODE:
                Log.d(TAG,"onNeedLogin, GET_MAP_LIST_REQUEST_CODE");
                if (result) {
                    Log.d(TAG,"RESULT OK");
                    getMapList();
                } else {
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
        }
    }
}
