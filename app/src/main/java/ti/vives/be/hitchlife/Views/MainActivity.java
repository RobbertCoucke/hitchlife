package ti.vives.be.hitchlife.Views;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ti.vives.be.hitchlife.Interfaces.MainActivityVPInterface;
import ti.vives.be.hitchlife.Models.Repository.LocalDataSource.LocalDataSource;
import ti.vives.be.hitchlife.Models.Repository.RemoteDataSource.RemoteDataSource;
import ti.vives.be.hitchlife.Models.Repository.Repository;
import ti.vives.be.hitchlife.Presenters.MainActivityPresenter;
import ti.vives.be.hitchlife.R;

public class MainActivity extends AppCompatActivity implements MainActivityVPInterface.View, NavigationView.OnNavigationItemSelectedListener {

    private Repository repository;
    private MainActivityPresenter presenter;
    private ActionBarDrawerToggle toggle;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainActivityPresenter(this);
        repository = new Repository(new RemoteDataSource(),new LocalDataSource(getApplicationContext()));
        BaseFragment frag = CameraFragment.newInstance(repository);
        presenter.addFragment(frag,true);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        navView.setNavigationItemSelectedListener(this);




    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.activity_main_drawer,menu);
//        return true;
//    }

    @Override
    public void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newconfig){
        super.onConfigurationChanged(newconfig);
        toggle.onConfigurationChanged(newconfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void setFragment(BaseFragment fragment, boolean backstack){
        fragment.attachPresenter(presenter);
        if(backstack)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commitAllowingStateLoss();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {


            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        displaySelectedScreen(menuItem.getItemId());

        return true;
    }

    private void displaySelectedScreen(int itemId){
        BaseFragment frag = null;
        switch(itemId){
            case R.id.contacts_menuItem:
                frag = ContactFragment.newInstance(repository);
            break;
            case R.id.logs_menuItem:
                frag = LogFragment.newInstance(repository);
                break;
            case R.id.camera_menuItem:
                frag = CameraFragment.newInstance(repository);
                break;
        }

        if(frag !=null){

            presenter.addFragment(frag,true);

        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
