package ru.developsdm.amproject.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.developsdm.amproject.R;
import ru.developsdm.amproject.app.AMApp;
import ru.developsdm.amproject.app.App;
import ru.developsdm.amproject.di.component.ApplicationComponent;
import ru.developsdm.amproject.di.component.DaggerMainComponent;
import ru.developsdm.amproject.di.component.MainComponent;
import ru.developsdm.amproject.di.module.MainModule;
import ru.developsdm.amproject.model.Catalogue;
import ru.developsdm.amproject.presenter.MainPresenter;
import ru.developsdm.amproject.utils.UtilLogging;
import ru.developsdm.amproject.view.contract.IMainView;
import ru.developsdm.amproject.view.fragment.BannerFragment;
import ru.developsdm.amproject.view.fragment.DividerFragment;
import ru.developsdm.amproject.view.fragment.MockFragment;
import ru.developsdm.amproject.view.fragment.NewsFragment;

/**
 * Main screen implementation.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class MainActivity extends AppCompatActivity implements IMainView {

    /**
     * Screen presenter
     */
    @Inject
    MainPresenter presenter;
    /**
     * Navigation Drawer Layout
     */
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    /**
     * Toolbar
     */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    /**
     * Navigation Menu
     */
    @BindView(R.id.navigationDrawer)
    NavigationView mNavigationDrawer;
    /**
     * Bottom Navigation View
     */
    @BindView(R.id.bottomNavigation)
    BottomNavigationView mBottomNavigationView;
    /**
     * Scroll view
     */
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    ActionBarDrawerToggle drawerToggle;

    private Unbinder unbinder;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    List<Fragment> activeFragments = new ArrayList<>();

    public static final String TAG_CATALOGUE_DIVIDER = "catalogue_divider";
    public static final String TAG_CATALOGUE_BANNERS = "catalogue_banners";
    public static final String TAG_CATALOGUE_THEMATIC_SETS = "catalogue_thematic_sets";
    public static final String TAG_CATALOGUE_NEWS = "catalogue_news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        // activity init
        ApplicationComponent applicationComponent = ((AMApp) getApplication()).getApplicationComponent();
        MainComponent component = DaggerMainComponent.builder()
                .applicationComponent(applicationComponent)
                .mainModule(new MainModule(this))
                .build();
        component.inject(this);

        // Set a Toolbar to replace the ActionBar.
        mToolbar.setTitle(App.EMPTY);
        setSupportActionBar(mToolbar);

        // Find our drawer view
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout.addDrawerListener(drawerToggle);

        // Find our drawer view
        // Setup drawer view
        setupDrawerContent(mNavigationDrawer);

        fragmentManager = getSupportFragmentManager();
        showCatalogueUI();

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        removeActiveFragments();
                        switch (item.getItemId()) {
                            case R.id.action_catalogues:
                                presenter.openCatalogue();
                                break;
                            case R.id.action_sets:
                                presenter.openSets();
                                break;
                            case R.id.action_sales:
                                presenter.openSales();
                                break;
                            case R.id.action_shops:
                                presenter.openShops();
                                break;
                            case R.id.action_profile:
                                presenter.openProfile();
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        presenter.releaseResources();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachedViewIsShown();
    }

    @Override
    protected void onStop() {
        presenter.attachedViewIsHidden();
        super.onStop();
    }

    private void showCatalogueUI() {


        scrollView.scrollTo(0, 0);
    }

    /**
     * Remove all active fragments from container, doesn't matter how many.
     */
    private void removeActiveFragments() {
        if (!activeFragments.isEmpty()) {
            transaction = fragmentManager.beginTransaction();
            for (Fragment activeFragment : activeFragments) {
                transaction.remove(activeFragment);
            }
            activeFragments.clear();
            transaction.commit();
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid mToolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_buy:
                // User chose the "Buy" action
                presenter.buy();
                return true;
            case R.id.action_about:
                // User chose the "About" action
                presenter.about();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Show toast
        UtilLogging.toast(this, menuItem.getTitle().toString());
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showCatalogueView(Catalogue catalogue) {

        removeActiveFragments();

        if (catalogue == null)
            return;

        String title;
        Fragment fragmentDivider;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // another fragment
        Fragment fragmentBanners = BannerFragment.newInstance(catalogue.getBanners());
        transaction.add(R.id.main_container, fragmentBanners, TAG_CATALOGUE_BANNERS);
        activeFragments.add(fragmentBanners);
        // divider
        fragmentDivider = DividerFragment.newInstance();
        transaction.add(R.id.main_container, fragmentDivider, TAG_CATALOGUE_DIVIDER + System.nanoTime() /* unique */);
        activeFragments.add(fragmentDivider);
        // another fragment
        title = getString(R.string.catalogue_thematic_sets_title);
        Fragment fragmentThematicSets = NewsFragment.newInstance(title, catalogue.getThemeSets());
        transaction.add(R.id.main_container, fragmentThematicSets, TAG_CATALOGUE_THEMATIC_SETS);
        activeFragments.add(fragmentThematicSets);
        // divider
        fragmentDivider = DividerFragment.newInstance();
        transaction.add(R.id.main_container, fragmentDivider, TAG_CATALOGUE_DIVIDER + System.nanoTime() /* unique */);
        activeFragments.add(fragmentDivider);
        // another fragment
        title = getString(R.string.catalogue_news_title);
        Fragment fragmentNews = NewsFragment.newInstance(title, catalogue.getNews());
        transaction.add(R.id.main_container, fragmentNews, TAG_CATALOGUE_NEWS);
        activeFragments.add(fragmentNews);
        // commit everything
        transaction.commit();

        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        }, 10);
    }

    @Override
    public void showMockView(String title, String tag) {
        removeActiveFragments();

        Fragment fragment = MockFragment.newInstance(title);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, fragment, tag);
        activeFragments.add(fragment);
        transaction.commit();
    }
}
