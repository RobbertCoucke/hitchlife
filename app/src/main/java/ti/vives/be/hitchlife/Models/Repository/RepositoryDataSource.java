package ti.vives.be.hitchlife.Models.Repository;

public abstract class RepositoryDataSource {

    public RepositoryDataSource(){

    }

    public interface getCarDataCallback{
        void onSucces(String jsonData);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    public abstract void getCarData(byte[] byteArray,getCarDataCallback callback);
}
