package ti.vives.be.hitchlife.Models.Repository.LocalDataSource;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import java.util.List;

import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;

public class LocalDataSource {
    private final String DB_NAME = "db_hitchlife";

    private AppDatabase appDatabase;
    public LocalDataSource(Context context){
        appDatabase = Room.databaseBuilder(context,AppDatabase.class,DB_NAME).allowMainThreadQueries().build();
    }

    //LOGS

    public long[] insertLog(Log... logs){

        return appDatabase.logDAO().insertAll(logs);

    }

    public void deleteLog(int id){
        int[] ids = new int[1];
        ids[0]=id;
        final List<Log> log = getLogsByIds(ids);
        if(log.get(0)!=null){
           new AsyncTask<Void,Void,Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    appDatabase.logDAO().delete(log.get(0));
                    return null;
                }
            }.execute();

        }
    }

    public void updateLog(Log log){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids){
                appDatabase.logDAO().update(log);
                return null;
            }
        }.execute();
    }

    public List<Log> getLogsByIds(int[] ids){
        return appDatabase.logDAO().findByids(ids);
    }

    public List<Log> getAllLogs(){


        return appDatabase.logDAO().getAll();
    }

    public List<Log> getLogByContactIds(int[] contactIds){
        return appDatabase.logDAO().findLogByContactIds(contactIds);
    }

    public Log getLog(int id){
        int[] logs = new int[1];
        logs[0]=id;
        return getLogsByIds(logs).get(0);
    }



    //CONTACTS

    public List<Contact> getAll(){
        return appDatabase.contactDAO().getAll();
    }

    public List<Contact> getContactsByIds(int[] contactIds){
        return appDatabase.contactDAO().loadAllByIds(contactIds);
    }

    public Contact getContact(int id){
        int[] contacts = new int[1];
        contacts[0]=id;
        return getContactsByIds(contacts).get(0);
    }

    public Contact getContactByName(String first,String last){
        return appDatabase.contactDAO().findByName(first,last);
    }

    public void insertContacts(Contact... contact){
        appDatabase.contactDAO().insertAll(contact);
    }

    public void deleteContactByid(int id){
        appDatabase.contactDAO().Delete(getContact(id));
    }

    public void deleteContact(Contact contact){
        appDatabase.contactDAO().Delete(contact);
    }

}
