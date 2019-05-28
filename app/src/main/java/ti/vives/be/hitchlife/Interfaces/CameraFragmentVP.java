package ti.vives.be.hitchlife.Interfaces;

import android.graphics.Bitmap;

import ti.vives.be.hitchlife.Models.Repository.Repository;

public interface CameraFragmentVP {
    interface View{
        void giveData(int logId, Repository repository);
        void showToast(String message);

    }
    interface Presenter{
        void sendPic(Bitmap bitmap);
    }
}
