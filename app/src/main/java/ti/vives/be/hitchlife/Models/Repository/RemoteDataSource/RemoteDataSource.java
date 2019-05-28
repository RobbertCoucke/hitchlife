package ti.vives.be.hitchlife.Models.Repository.RemoteDataSource;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import ti.vives.be.hitchlife.Models.Repository.RepositoryDataSource;


public class RemoteDataSource extends RepositoryDataSource {

    public RemoteDataSource() {
    }

    @Override
    public void getCarData(byte[] byteArray, getCarDataCallback callback) {
        new NetworkAsync(callback,byteArray).execute();
    }
}


class NetworkAsync extends AsyncTask<Void,Void,String>{
    private RepositoryDataSource.getCarDataCallback callback;
    private final String secret_key = "sk_9da5550b1046d2fe3d933695";
    private byte[] byteArray;

    public NetworkAsync(RepositoryDataSource.getCarDataCallback callback,byte[] byteArray){
        this.callback = callback;
        this.byteArray = byteArray;
    }

    @Override
    protected String doInBackground(Void... voids) {
        byte[] encoded = android.util.Base64.encode(byteArray, Base64.DEFAULT);
        URL url = null;
        try {
            url = new URL("https://api.openalpr.com/v2/recognize_bytes?recognize_vehicle=1&country=us&secret_key=" + secret_key);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection con = null;
        try {
            con = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpURLConnection http = (HttpURLConnection) con;
        try {
            http.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        http.setFixedLengthStreamingMode(encoded.length);
        http.setDoOutput(true);
        try (OutputStream os = http.getOutputStream()) {
            os.write(encoded);
            int status_code = http.getResponseCode();
            if (status_code == 200) {
                //read response
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String json_content = "";
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    json_content += inputLine;
                }
                return json_content;
            } else {
                System.out.println("fout in asynctask" + status_code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(String resp){
        if(resp==null)
            callback.onFailure(new Throwable());
        else
            callback.onSucces(resp);
    }


}
