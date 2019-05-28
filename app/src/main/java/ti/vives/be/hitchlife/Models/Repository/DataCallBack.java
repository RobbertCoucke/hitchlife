package ti.vives.be.hitchlife.Models.Repository;

public interface DataCallBack {
    void onSucces(Object data);

    void onFailure(Throwable throwable);

    void onNetworkFailure();
}
