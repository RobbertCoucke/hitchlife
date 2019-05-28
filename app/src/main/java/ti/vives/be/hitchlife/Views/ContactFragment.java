package ti.vives.be.hitchlife.Views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ti.vives.be.hitchlife.Adapters.ContactAdapter;
import ti.vives.be.hitchlife.Interfaces.ContactFragmentVP;
import ti.vives.be.hitchlife.Interfaces.OnItemClickListener;
import ti.vives.be.hitchlife.Models.Repository.DataCallBack;
import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;
import ti.vives.be.hitchlife.Models.Repository.Repository;
import ti.vives.be.hitchlife.Presenters.ContactFragmentPresenter;
import ti.vives.be.hitchlife.R;


public class ContactFragment extends BaseFragment implements ContactFragmentVP.View {



    private ContactFragmentPresenter presenter;
    @BindView(R.id.add_Contact_btn)
    Button addContactBtn;

    @BindView(R.id.contacts_recycler_view)
    RecyclerView contactRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Contact[] contacts;

    public ContactFragment() {
        // Required empty public constructor
    }


    public static ContactFragment newInstance(Repository rep) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putParcelable("REP",rep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter = new ContactFragmentPresenter(this, getArguments().getParcelable("REP"));



    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.getAllContacts();
        mAdapter = new ContactAdapter(contacts, new OnItemClickListener() {
            @Override
            public void onItemCLick(Contact contact,int position) {
                onContactClick(contact,position);
            }
        });
        contactRecyclerView.setAdapter(mAdapter);
    }

    public void setContacts(Contact[] contacts){
        this.contacts = contacts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this,v);
        presenter.getAllContacts();
        contactRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());

        contactRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ContactAdapter(contacts, new OnItemClickListener() {
            @Override
            public void onItemCLick(Contact contact,int position) {
                onContactClick(contact,position);
            }
        });
        contactRecyclerView.setAdapter(mAdapter);
        contactRecyclerView.addItemDecoration(new DividerItemDecoration(contactRecyclerView.getContext(),DividerItemDecoration.VERTICAL));


        //TODO put button function in toolbar instead of floating
        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    public void onContactClick(Contact contact,int position){
        presenter.deleteContact(contact);
        Contact[] tmp = new Contact[contacts.length-1];
        int j=0;
        for(int i=0;i<contacts.length;i++){
            if(i !=position){
                tmp[j] = contacts[i];
                j++;
            }

        }
        contacts = tmp;
        contactRecyclerView.removeViewAt(position);
        mAdapter = new ContactAdapter(contacts, new OnItemClickListener() {
            @Override
            public void onItemCLick(Contact contact,int position) {
                onContactClick(contact,position);
            }
        });
        contactRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
