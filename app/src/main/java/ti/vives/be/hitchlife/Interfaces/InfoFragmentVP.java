package ti.vives.be.hitchlife.Interfaces;

import java.time.LocalDateTime;

import ti.vives.be.hitchlife.Models.Repository.DataCallBack;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;

public interface InfoFragmentVP {
    interface View{
        void showData(Log log);
        void showToast(String message);
    }
    interface Presenter{

        void getLog(int logId);

        void updateLog(Log log);

        void sendMessagesToAllContacts(Log log);
    }
}
