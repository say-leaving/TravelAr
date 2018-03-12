package com.luoye.say.travelar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Arrays;

import fragment.CameraFragment;
import fragment.HomeFragment;
import fragment.MyFragment;
import fragment.PlanFragment;

public class MainActivity extends AppCompatActivity {

    private static int currIndex = 0;
    private RadioGroup group;

    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();
        initDate();
        initView();
    }

    private void initDate() {
        currIndex=0;
        fragmentTags=new ArrayList<>(Arrays.asList("HomeFragment","PlanFragment","CameraFragment","MyFragment"));
    }

    private void initView() {
        group = findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        currIndex = 0;
                        break;
                    case R.id.foot_bar_plan:
                        currIndex = 1;
                        break;
                    case R.id.foot_bar_camera:
                        currIndex = 2;
                        break;
                    case R.id.foot_bar_user:
                        currIndex = 3;
                        break;
                }
                showFragment();
            }
        });
        showFragment();
    }

    private void showFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new HomeFragment();
            case 1:
                return new PlanFragment();
            case 2:
                return new CameraFragment();
            case 3:
                return new MyFragment();
            default:
                return null;
        }
    }
}
