package ti.vives.be.hitchlife.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;
import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;
import ti.vives.be.hitchlife.Models.Repository.LocalDataSource.LocalDataSource;
import ti.vives.be.hitchlife.Models.Repository.RemoteDataSource.RemoteDataSource;
import ti.vives.be.hitchlife.Models.Repository.Repository;
import ti.vives.be.hitchlife.R;


public class UserContactsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts.LOOKUP_KEY,
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                        ContactsContract.Contacts.DISPLAY_NAME,


    };

    private final static int[] TO_IDS = {android.R.id.text1};

    private static final int CONTACT_ID_INDEX = 0;
    private static final int CONTACT_KEY_INDEX = 1;

//    @SuppressLint("InlinedApi")
//            private static final String SELECTION = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
//            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
//            ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";

    @SuppressLint("InlinedApi")
    private static String SELECTION;

    private String searchString = "robbe";
    private String[] selectionArgs = {searchString};

    private Repository rep;




    ListView contactsList;
    long contactId;
    String contactKey;
    Uri contactUri;

    private SimpleCursorAdapter cursorAdapter;

    public UserContactsFragment() {
        // Required empty public constructor
    }

    public static UserContactsFragment newInstance(String s,Repository rep) {
        UserContactsFragment fragment = new UserContactsFragment();
        Bundle args = new Bundle();
        args.putString("SEARCH",s);
        args.putParcelable("REP",rep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.rep = getArguments().getParcelable("REP");



    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        contactsList = (ListView) getView().findViewById(R.id.contacts_listview_test);

        cursorAdapter = new SimpleCursorAdapter(getActivity(),R.layout.contacts_list_item,null,FROM_COLUMNS,TO_IDS,0);

        contactsList.setAdapter(cursorAdapter);


        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();

                cursor.moveToPosition(position);

                contactId = cursor.getLong(CONTACT_ID_INDEX);
                contactKey = cursor.getString(CONTACT_KEY_INDEX);
                contactUri = ContactsContract.Contacts.getLookupUri(contactId,contactKey);

                Contact contact = new Contact();
                contact.firstName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contact.sms = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contact.lastName = "";
                rep.insertContacts(contact);
                Toast.makeText(getContext(),"Contact added",Toast.LENGTH_SHORT).show();
            }
        });

        searchString = getArguments().getString("SEARCH");


        getLoaderManager().initLoader(0,null,this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_contacts, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }



    @Override
    public void onDetach() {
        super.onDetach();

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        if(searchString == null || searchString.equals("")){
            SELECTION =
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME) +
                    "<>''" + " AND " + ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1";
            return new CursorLoader(getActivity(), ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PROJECTION,SELECTION,null,null);
        }
        SELECTION = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";

            selectionArgs[0] = "%" + searchString + "%";


        return new CursorLoader(getActivity(),ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PROJECTION,SELECTION,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }


}
