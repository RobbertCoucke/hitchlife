package ti.vives.be.hitchlife.Views;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ti.vives.be.hitchlife.Adapters.ContactAdapter;
import ti.vives.be.hitchlife.Interfaces.CameraFragmentVP;
import ti.vives.be.hitchlife.Models.Repository.RemoteDataSource.RemoteDataSource;
import ti.vives.be.hitchlife.Presenters.CameraFragmentPresenter;
import ti.vives.be.hitchlife.R;
import ti.vives.be.hitchlife.Models.Repository.Repository;

public class CameraFragment extends BaseFragment implements CameraFragmentVP.View, SurfaceHolder.Callback {

    private static final int CAMERA_REQUEST_CODE = 1;
    private CameraFragmentPresenter presenter;
    @BindView(R.id.surfaceView) SurfaceView surfaceView;
    @BindView(R.id.capture_button) Button captureBtn;
    private SurfaceHolder surfaceHolder;
    private static final int GET_FROM_GALLERY = 3;
    private Camera camera;
    private Camera.PictureCallback jpegCallback;

    public CameraFragment() {
        // Required empty public constructor
    }

    public static CameraFragment newInstance(Repository repository) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("REP",repository);
        CameraFragment fragment = new CameraFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter = new CameraFragmentPresenter(this,getArguments().getParcelable("REP"),(MainActivity)getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        ButterKnife.bind(this,view);
        surfaceHolder = surfaceView.getHolder();

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
        }else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
                //startGallery();
            }
        });

        jpegCallback = new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                Bitmap decodeBitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                presenter.sendPic(decodeBitmap);
                camera.startPreview();
            }
        };
        return view;
    }






    private void captureImage(){
        camera.takePicture(null,null,jpegCallback);
    }

    private void startGallery() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        //Detects request codes
//        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
//            Uri selectedImage = data.getData();
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
//
//                presenter.sendPic(bitmap);
//            } catch (FileNotFoundException e) {
//
//                e.printStackTrace();
//            } catch (IOException e) {
//
//                e.printStackTrace();
//            }
//        }
//    }

    public void giveData(int logId,Repository rep){
        navigationPresenter.addFragment(InfoFragment.newInstance(logId,rep),true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        camera = Camera.open();
        Camera.Parameters parameters;
        parameters = camera.getParameters();
        camera.setDisplayOrientation(90);
        parameters.setPreviewFrameRate(30);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(parameters);
        try {
            camera.setPreviewDisplay(surfaceHolder);

        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void onPause() {
        super.onPause();
        camera.release();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    surfaceHolder.addCallback(this);
                    surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                }else{
                    Toast.makeText(getContext(),"Please provide the permission ", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}
