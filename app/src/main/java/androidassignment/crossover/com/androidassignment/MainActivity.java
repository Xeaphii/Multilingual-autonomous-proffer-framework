package androidassignment.crossover.com.androidassignment;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    DatabaseHelper db;
    // Tab titles
    private String[] tabs = {"Active", "Won", "Lost"};
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    private static final int CHECK_ACTIVITY = 1;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(getApplicationContext());

        // Initilization
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        ///actionBar.setBackgroundDrawable(new ColorDrawable(0xff000000));
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        //Bot Creation
        db.InsertUser(new UserProfile("bot@gmail.com", "Bot", Password.getHash("12345")));
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                db.RandomBid();
            }
        }, 2000, 5000);


    }

    @Override
    protected void onPause() {
        super.onPause();

        timer.cancel();
        timer = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                db.RandomBid();
            }
        }, 2000, 5000);
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_add_item:
                Intent AddItemIntent = new Intent(getApplicationContext(), AddAuctionItem.class);
                startActivity(AddItemIntent);
                // search action
                return true;
            case R.id.action_log_out:
                prefs.edit().putString("is_initialized", "0").commit();
                prefs.edit().putString("user_name", "").commit();
                Intent signin = new Intent(getApplicationContext(), SignIn.class);
                startActivity(signin);
                finish();
                // search action
                return true;
            case R.id.action_change_password:

                Intent changePassword = new Intent(getApplicationContext(), ChangePassword.class);
                startActivityForResult(changePassword, CHECK_ACTIVITY);

                // search action
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHECK_ACTIVITY) {
            if (resultCode == RESULT_CANCELED) {
                Intent signin = new Intent(getApplicationContext(), SignIn.class);
                startActivity(signin);
                finish();
            }
        }
    }
}
