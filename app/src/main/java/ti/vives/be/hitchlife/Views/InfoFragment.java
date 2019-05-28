package ti.vives.be.hitchlife.Views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.work.Data;

import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ti.vives.be.hitchlife.Interfaces.InfoFragmentVP;
import ti.vives.be.hitchlife.Models.Repository.DataCallBack;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;
import ti.vives.be.hitchlife.Models.Repository.Repository;
import ti.vives.be.hitchlife.Presenters.InfoFragmentPresenter;
import ti.vives.be.hitchlife.R;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class InfoFragment extends BaseFragment implements InfoFragmentVP.View {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String LOG_ID = "jsonData";

    private static final int COARSE_LOCATION_REQUEST_CODE = 5;
    private static final int FINE_LOCATION_REQUEST_CODE = 6;

    private int logId;
    private InfoFragmentPresenter presenter;
    @BindView(R.id.licensPlate_txtView)
    TextView licensePlateTxtView;
    @BindView(R.id.location_txtView)
    TextView locationTxtView;
    @BindView(R.id.dateTime_txtView)
    TextView dateTimeTxtView;
    @BindView(R.id.info_textView) TextView testTxtView;


    public InfoFragment() {
        // Required empty public constructor
    }


    public static InfoFragment newInstance(int logId, Repository rep) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putInt(LOG_ID, logId);
        args.putParcelable("REP",rep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            logId = getArguments().getInt(LOG_ID);
        }
        presenter = new InfoFragmentPresenter(this,getArguments().getParcelable("REP"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, v);
        presenter.getLog(logId);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }


    @Override
    public void showData(Log log) {

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},COARSE_LOCATION_REQUEST_CODE);
//            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_REQUEST_CODE);
//        }
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        String currentLocation = "lat: " + location.getLatitude() + " long: " + location.getLongitude();
                        log.location = currentLocation;
                        locationTxtView.setText("location: "+currentLocation);
                        presenter.updateLog(log);
                        presenter.sendMessagesToAllContacts(log);
                    } else {
                        locationTxtView.setText("location unavailable");
                    }

                }
            });
        }catch (SecurityException e){
            android.util.Log.d(TAG, "showData: "+e.getMessage());
        }

        licensePlateTxtView.setText("Licenseplate: "+log.licensePlate);
        dateTimeTxtView.setText("dateTime: "+log.dateTime);


    }


}
