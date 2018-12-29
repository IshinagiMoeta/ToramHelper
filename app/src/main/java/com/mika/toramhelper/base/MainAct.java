package com.mika.toramhelper.base;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mika.toramhelper.R;
import com.mika.toramhelper.enchantment.EAssistantFragment;
import com.mika.toramhelper.enchantment.EDetailsFragment;
import com.mika.toramhelper.enchantment.ESimulatorFragment;
import com.mika.toramhelper.skill.SDetailsFragment;
import com.mika.toramhelper.skill.SPointFragment;
import com.mika.toramhelper.skill.SkillsComboFragment;
import com.mika.toramhelper.strategy.StrategyFragment;

import butterknife.BindView;

public class MainAct extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment mContent = null;
    private MenuItem priMenuItem;

    private FragmentManager mFragmentMan = getSupportFragmentManager();
    private SDetailsFragment sDetailsFragment;
    private SPointFragment sPointFragment;
    private SkillsComboFragment skillsComboFragment;
    private EDetailsFragment eDetailsFragment;
    private EAssistantFragment eAssistantFragment;
    private ESimulatorFragment eSimulatorFragment;
    private StrategyFragment strategyFragment;

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();
        initFragment();
        initView();
        initSelect();
    }

    private void initSelect() {
        FragmentTransaction transaction = mFragmentMan.beginTransaction().setCustomAnimations(
                android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.add(R.id.content_frame, eAssistantFragment).commit();
        mContent = eAssistantFragment;
        priMenuItem = navigationView.getMenu().getItem(2).getSubMenu().getItem(1);
        priMenuItem.setChecked(true);
    }

    private void initFragment() {
        strategyFragment = new StrategyFragment();
        sDetailsFragment = new SDetailsFragment();
        sPointFragment = new SPointFragment();
        skillsComboFragment = new SkillsComboFragment();
        eAssistantFragment = new EAssistantFragment();
        eDetailsFragment = new EDetailsFragment();
        eSimulatorFragment = new ESimulatorFragment();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        Resources resource = getBaseContext().getResources();
        ColorStateList csl = resource.getColorStateList(R.color.nav_color);
        navigationView.setItemTextColor(csl);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //TODO setting
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setChecked(true);//设置选型被选中
        if (priMenuItem != null) {
            priMenuItem.setChecked(false);
        }

        switch (id) {
            case R.id.menu_strategy:
                //TODO 论坛攻略
                switchContent(strategyFragment);
                break;
            case R.id.skills_for_details:
                //TODO 技能详情
                switchContent(sDetailsFragment);
                break;
            case R.id.skills_point_assistant:
                //TODO 加点助手
                switchContent(sPointFragment);
                break;
            case R.id.skills_combo_simulator:
                //TODO 连击模拟
                switchContent(skillsComboFragment);
                break;
            case R.id.enchantment_for_details:
                //TODO 附魔简介
                switchContent(eDetailsFragment);
                break;
            case R.id.enchantment_assistant:
                //TODO 附魔计算
                switchContent(eAssistantFragment);
                break;
            case R.id.enchantment_simulator:
                //TODO 附魔模拟
                switchContent(eSimulatorFragment);
                break;
        }

        priMenuItem = item;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchContent(Fragment to) {
        if (mContent != to) {
            FragmentTransaction transaction = mFragmentMan.beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(mContent).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;
        }
    }
}
