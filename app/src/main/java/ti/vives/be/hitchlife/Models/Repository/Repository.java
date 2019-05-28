package ti.vives.be.hitchlife.Models.Repository;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;
import ti.vives.be.hitchlife.Models.Repository.LocalDataSource.LocalDataSource;

public class Repository implements Parcelable {

    private RepositoryDataSource remoteDataSourceHitchLife;
    private LocalDataSource localDataSource;

    public Repository(RepositoryDataSource remoteDataSourceHitchLife,LocalDataSource localDataSource){
        this.remoteDataSourceHitchLife = remoteDataSourceHitchLife;
        this.localDataSource = localDataSource;
    }

    protected Repository(Parcel in) {
    }

    public static final Creator<Repository> CREATOR = new Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel in) {
            return new Repository(in);
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };

    public void getCarData(byte[] byteArray, final RepositoryDataSource.getCarDataCallback callback){
        remoteDataSourceHitchLife.getCarData(byteArray, new RepositoryDataSource.getCarDataCallback() {
            @Override
            public void onSucces(String jsonData) {
                callback.onSucces(jsonData);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onNetworkFailure() {
                callback.onNetworkFailure();
            }
        });
    }



    //LOGS

    public void updateLog(Log log){
        localDataSource.updateLog(log);
    }

    public long[] insertLog(Log... logs){
        return localDataSource.insertLog(logs);
    }

    public void deleteLog(int id){
        localDataSource.deleteLog(id);
    }

    public List<Log> getLogsByIds(int[] ids){
        return localDataSource.getLogsByIds(ids);
    }

    public List<Log> getAllLogs(){
        return localDataSource.getAllLogs();
    }

    public List<Log> getLogByContactIds(int[] contactIds){
        return localDataSource.getLogByContactIds(contactIds);
    }

    public Log getLog(int id){
        return localDataSource.getLog(id);
    }



    //CONTACTS

    public List<Contact> getAll(){
        return localDataSource.getAll();
    }

    public List<Contact> getContactsByIds(int[] contactIds){
        return localDataSource.getContactsByIds(contactIds);
    }

    public Contact getContact(int id){
        return localDataSource.getContact(id);
    }

    public Contact getContactByName(String first,String last){
        return localDataSource.getContactByName(first,last);
    }

    public void insertContacts(Contact... contact){
        localDataSource.insertContacts(contact);
    }

    public void deleteContactByid(int id){
        localDataSource.deleteContactByid(id);
    }

    public void deleteContact(Contact contact){
        localDataSource.deleteContact(contact);
    }






    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
