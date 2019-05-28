package ti.vives.be.hitchlife.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ti.vives.be.hitchlife.Interfaces.OnItemClickListener;
import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;
import ti.vives.be.hitchlife.R;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {



    private Contact[] contacts;
    private OnItemClickListener listener;



    public ContactAdapter(Contact[] contacts,OnItemClickListener listener){
        this.contacts = contacts;
        this.listener = listener;
    }

    public ContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_recyclerview_item,null);
        ContactAdapter.MyViewHolder viewHolder = new ContactAdapter.MyViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.contactName.setText(contacts[i].getFirstName());
        holder.contactNumber.setText(contacts[i].getSms());
        holder.bind(contacts[i],listener,i);
    }

    @Override
    public int getItemCount() {
        return contacts.length;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.contact_name_textView) TextView contactName;
        @BindView(R.id.contact_number_textView) TextView contactNumber;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(final Contact contact,final OnItemClickListener listener,int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemCLick(contact,position);
                }
            });
        }

    }
}
