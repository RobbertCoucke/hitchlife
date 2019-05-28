package ti.vives.be.hitchlife.Interfaces;

import android.provider.ContactsContract;

import ti.vives.be.hitchlife.Models.Repository.DataCallBack;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;

public interface LogFragmentVP {
    interface View{
        void setLogs(Log[] logs);

    }

    interface Presenter{
        void getAllLogs(DataCallBack callback);
    }
}
