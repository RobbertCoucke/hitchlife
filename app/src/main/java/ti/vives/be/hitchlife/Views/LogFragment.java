package ti.vives.be.hitchlife.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ti.vives.be.hitchlife.Adapters.ContactAdapter;
import ti.vives.be.hitchlife.Adapters.LogAdapter;
import ti.vives.be.hitchlife.Interfaces.CameraFragmentVP;
import ti.vives.be.hitchlife.Interfaces.LogFragmentVP;
import ti.vives.be.hitchlife.Models.Repository.DataCallBack;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;
import ti.vives.be.hitchlife.Models.Repository.Repository;
import ti.vives.be.hitchlife.Presenters.CameraFragmentPresenter;
import ti.vives.be.hitchlife.Presenters.LogFragmentPresenter;
import ti.vives.be.hitchlife.R;


public class LogFragment extends BaseFragment implements LogFragmentVP.View {

    private static final String ARG_PARAM1 = "param1";


    private LogFragmentPresenter presenter;
    @BindView(R.id.logs_recycler_view)
    RecyclerView logRV;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Log[] logs;

    public LogFragment() {
        // Required empty public constructor
    }

    public static LogFragment newInstance(Repository rep) {
        LogFragment fragment = new LogFragment();
        Bundle args = new Bundle();
        args.putParcelable("REP",rep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        this.presenter= new LogFragmentPresenter(this,getArguments().getParcelable("REP"));
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.getAllLogs(new DataCallBack() {
            @Override
            public void onSucces(Object data) {
                logs = (Log[]) data;
                mAdapter = new LogAdapter(logs);
                logRV.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onNetworkFailure() {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_log, container, false);
        ButterKnife.bind(this,v);
        presenter.getAllLogs(new DataCallBack() {
            @Override
            public void onSucces(Object data) {
                logs = (Log[]) data;
                logRV.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getActivity());
                logRV.setLayoutManager(layoutManager);
                mAdapter = new LogAdapter(logs);
                logRV.setAdapter(mAdapter);
                logRV.addItemDecoration(new DividerItemDecoration(logRV.getContext(),DividerItemDecoration.VERTICAL));
            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onNetworkFailure() {

            }
        });

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

    public void setLogs(Log[] logs){this.logs = logs;}

}
