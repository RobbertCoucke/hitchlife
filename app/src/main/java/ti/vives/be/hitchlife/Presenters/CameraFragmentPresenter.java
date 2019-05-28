package ti.vives.be.hitchlife.Presenters;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Deflater;

import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;
import ti.vives.be.hitchlife.Interfaces.CameraFragmentVP;
import ti.vives.be.hitchlife.Models.Repository.Repository;
import ti.vives.be.hitchlife.Models.Repository.RepositoryDataSource;
import ti.vives.be.hitchlife.Views.MainActivity;
import ti.vives.be.hitchlife.Views.SearchActivity;

import static android.content.ContentValues.TAG;

public class CameraFragmentPresenter implements CameraFragmentVP.Presenter {

    private CameraFragmentVP.View view;
    private Repository repository;
    private MainActivity activity;



    public CameraFragmentPresenter(CameraFragmentVP.View view, Repository repository,MainActivity activity){
        this.view = view;
        this.activity = activity;
        this.repository = repository;
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) /width;
        float scaleHeight = ((float) newHeight)/height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth,scaleHeight);

            return Bitmap.createBitmap(bitmap,0,0,width,height,matrix,false);




    }

    private Bitmap rotateBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        return Bitmap.createBitmap(bitmap,0,0,width,height,matrix,false);
    }
    private byte[] compressByteArray(byte[] bytes){

        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);

        compressor.setInput(bytes);
        compressor.finish();

        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
        byte[] buf = new byte[1024];
        while(!compressor.finished()){
            int count = compressor.deflate(buf);
            bos.write(buf,0,count);
        }
        try{
            bos.close();
        }catch (Exception e){

        }

        return bos.toByteArray();
    }


    @Override
    public void sendPic(Bitmap bitmapUnscaled) {
        Bitmap bitmap = rotateBitmap(bitmapUnscaled);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        byte[] uncompressedByteArray = stream.toByteArray();

        repository.getCarData(uncompressedByteArray, new RepositoryDataSource.getCarDataCallback() {
            @Override
            public void onSucces(String jsonData) {


                try {
                    JSONObject jObject = new JSONObject(jsonData);
                    JSONArray arr = jObject.getJSONArray("results");
                    JSONObject obj = arr.getJSONObject(0);
                    String licensPlate = obj.getString("plate");
                    Log log = new Log();
                    log.licensePlate = licensPlate;

                    //get DateTime
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                    String currentDateTime = sdf.format(new Date());
                    log.dateTime = currentDateTime;
                    Bitmap bitmapS = scaleBitmap(bitmapUnscaled,480,640);
                    Bitmap bitmp = rotateBitmap(bitmapS);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
                    byte[] byteArray = compressByteArray(stream.toByteArray());
                    log.setCarPic(byteArray);
                    //TODO preference screen for which contacts to send it to instead of all
                    List<Contact> contacts = repository.getAll();

                    if(contacts.size()!=0) {
                        int logId = 0;
                        for(Contact contact : contacts) {
                            log.contactId = contact.id;
                            logId = (int) repository.insertLog(log)[0];

                        }
                        view.giveData(logId, repository);
                    }else{
                        view.showToast("add a contact first please");
                    }
                }catch (Exception e){
                    view.showToast("cant find license plate, try taking a new picture");
                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                view.showToast("cant find license plate, try taking a new picture");
            }

            @Override
            public void onNetworkFailure() {
            }
        });


    }


}
