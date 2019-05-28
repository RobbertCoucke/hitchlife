package ti.vives.be.hitchlife.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ti.vives.be.hitchlife.Interfaces.SearchActivityVP;
import ti.vives.be.hitchlife.Models.Repository.LocalDataSource.LocalDataSource;
import ti.vives.be.hitchlife.Models.Repository.RemoteDataSource.RemoteDataSource;
import ti.vives.be.hitchlife.Models.Repository.Repository;
import ti.vives.be.hitchlife.Presenters.MainActivityPresenter;
import ti.vives.be.hitchlife.Presenters.SearchActivityPresenter;
import ti.vives.be.hitchlife.R;

public class SearchActivity extends AppCompatActivity implements SearchActivityVP.View {

    private Repository repository;
    private SearchActivityPresenter presenter;
    @BindView(R.id.search_filter) EditText searchFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.addFragment(UserContactsFragment.newInstance(s.toString(),repository),false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        presenter = new SearchActivityPresenter(this);
        repository = new Repository(new RemoteDataSource(),new LocalDataSource(getApplicationContext()));
        //Intent intent = getIntent();
        presenter.addFragment(UserContactsFragment.newInstance(null,repository),true);

    }

    @Override
    public void onBackPressed() {
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments == 1) {
                finish();
            } else if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }

    }

    @Override
    public void setFragment(BaseFragment fragment, boolean backstack) {
        fragment.attachPresenter(presenter);
        if(backstack)
            getSupportFragmentManager().beginTransaction().replace(R.id.search_activity_container,fragment).addToBackStack(null).commitAllowingStateLoss();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.search_activity_container,fragment).commitAllowingStateLoss();
    }
}
