package ti.vives.be.hitchlife.Interfaces;

import ti.vives.be.hitchlife.Views.BaseFragment;

public interface MainActivityVPInterface {
    interface View{
        void setFragment(BaseFragment fragment,boolean backstack);
    }

    interface Presenter{

    }
}
