package ti.vives.be.hitchlife.Presenters;

import android.telephony.SmsManager;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import ti.vives.be.hitchlife.Interfaces.InfoFragmentVP;
import ti.vives.be.hitchlife.Models.Repository.DataCallBack;
import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;
import ti.vives.be.hitchlife.Models.Repository.Repository;

public class InfoFragmentPresenter implements InfoFragmentVP.Presenter {

    private InfoFragmentVP.View view;
    private Repository rep;

    public InfoFragmentPresenter(InfoFragmentVP.View v,Repository rep){
        this.view = v;
        this.rep = rep;
    }




    @Override
    public void getLog(int logId) {
        Log log = rep.getLog(logId);
        view.showData(log);
    }

    @Override
    public void updateLog(Log log) {
        rep.updateLog(log);
    }

    @Override
    public void sendMessagesToAllContacts(Log log) {
        if (log != null){
            List<Contact> contacts = rep.getAll();
        SmsManager smsManager = SmsManager.getDefault();
        String message = "DateTime: " + log.dateTime+" Location: "+log.location+"license plate: "+log.licensePlate;


        for (Contact contact : contacts) {
            smsManager.sendTextMessage(contact.sms, null, message, null, null);
        }
        view.showToast("Message sent");
        }
    }
}
