package com.getcapacitor.myapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.getcapacitor.BridgeFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentActivity extends AppCompatActivity implements BridgeFragment.OnFragmentInteractionListener {
  private TextView mTextMessage;
  private ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment);
    BottomNavigationView navView = findViewById(R.id.nav_view);
    mTextMessage = findViewById(R.id.message);
    mViewPager = findViewById(R.id.pager);
    buildViewPager();
    navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
  }


  class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
      super(manager);
    }
    @Override
    public Fragment getItem(int position) {
      return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
      return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
      mFragmentList.add(fragment);
    }

  }

  private void buildViewPager() {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    BridgeFragment page1 = new BridgeFragment();
    adapter.addFragment(page1);
    BridgeFragment page2 = new BridgeFragment();
    adapter.addFragment(page2);
    //BridgeFragment page3 = new BridgeFragment();
    //adapter.addFragment(page3);

    MyVideoFragment tab3 = new MyVideoFragment();
    adapter.addFragment(tab3);

    mViewPager.setAdapter(adapter);
  }


  @Override
  public void onFragmentInteraction(Uri uri) {

  }


  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          mTextMessage.setText(R.string.title_home);
          mViewPager.setCurrentItem(0);
          return true;
        case R.id.navigation_dashboard:
          mTextMessage.setText(R.string.title_dashboard);
          mViewPager.setCurrentItem(1);
          return true;
        case R.id.navigation_notifications:
          mTextMessage.setText(R.string.title_notifications);
          mViewPager.setCurrentItem(2);
          return true;
      }
      return false;
    }
  };
}
