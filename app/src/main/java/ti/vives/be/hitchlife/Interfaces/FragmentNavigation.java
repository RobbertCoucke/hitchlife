package ti.vives.be.hitchlife.Interfaces;

import ti.vives.be.hitchlife.Views.BaseFragment;

public interface FragmentNavigation {
    interface View{
        void attachPresenter(Presenter presenter);
    }

    interface Presenter{
        void addFragment(BaseFragment fragment,boolean backstack);
    }
}
