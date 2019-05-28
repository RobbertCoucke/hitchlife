package ti.vives.be.hitchlife.Presenters;

import ti.vives.be.hitchlife.Interfaces.FragmentNavigation;
import ti.vives.be.hitchlife.Interfaces.MainActivityVPInterface;
import ti.vives.be.hitchlife.Views.BaseFragment;

public class MainActivityPresenter implements MainActivityVPInterface.Presenter, FragmentNavigation.Presenter {

    private MainActivityVPInterface.View view;

    public MainActivityPresenter(MainActivityVPInterface.View activityView){
        this.view = activityView;
    }

    @Override
    public void addFragment(BaseFragment fragment,boolean backstack) {
        view.setFragment(fragment,backstack);
    }

}
