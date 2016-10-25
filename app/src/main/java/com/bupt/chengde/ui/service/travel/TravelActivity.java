package com.bupt.chengde.ui.service.travel;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bupt.chengde.base.BaseActivity;

import java.util.List;

/**
 * Created by joycezhao on 16/10/24.
 */

public class TravelActivity extends BaseActivity{
    private List<Fragment> fragmentList;
    private ViewPager viewPager;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}
