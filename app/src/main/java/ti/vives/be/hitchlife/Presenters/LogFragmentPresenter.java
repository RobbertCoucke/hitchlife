package ti.vives.be.hitchlife.Presenters;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.Inflater;

import ti.vives.be.hitchlife.Interfaces.LogFragmentVP;
import ti.vives.be.hitchlife.Models.Repository.DataCallBack;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;
import ti.vives.be.hitchlife.Models.Repository.Repository;

public class LogFragmentPresenter implements LogFragmentVP.Presenter {
    private LogFragmentVP.View view;
    private Repository repository;

    public LogFragmentPresenter(LogFragmentVP.View view, Repository rep){
        this.view = view;
        this.repository = rep;
    }

    @Override
    public void getAllLogs(DataCallBack callback) {
        //fill recyclerview with callback
        List<Log> list = repository.getAllLogs();
        Log[] logs = list.toArray(new Log[list.size()]);
        for(Log log :logs){
            log.setCarPic(decodeByteArray(log.getCarPic()));
        }
        callback.onSucces(logs);
    }

    private byte[] decodeByteArray(byte[] arr){
        ByteArrayOutputStream baos = null;
        Inflater inflr = new Inflater();
        inflr.setInput(arr);
        baos = new ByteArrayOutputStream();
        byte[] tmp = new byte[4*1024];
        try{
            while(!inflr.finished()){
                int size = inflr.inflate(tmp);
                baos.write(tmp,0,size);
            }
        }catch (Exception e){

        }finally {
            try{
                if(baos!=null)
                    baos.close();
            }catch (Exception e){

            }
        }
        return baos.toByteArray();
    }
}
