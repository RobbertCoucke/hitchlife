package ti.vives.be.hitchlife.Presenters;

import ti.vives.be.hitchlife.Interfaces.FragmentNavigation;
import ti.vives.be.hitchlife.Interfaces.MainActivityVPInterface;
import ti.vives.be.hitchlife.Interfaces.SearchActivityVP;
import ti.vives.be.hitchlife.Views.BaseFragment;
import ti.vives.be.hitchlife.Views.SearchActivity;

public class SearchActivityPresenter implements SearchActivityVP.Presenter, FragmentNavigation.Presenter{

    private SearchActivityVP.View view;

    public SearchActivityPresenter(SearchActivityVP.View view){
        this.view = view;
    }
    @Override
    public void addFragment(BaseFragment fragment, boolean backstack) {
        view.setFragment(fragment,backstack);
    }
}
