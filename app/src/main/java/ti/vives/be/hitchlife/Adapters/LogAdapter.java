package ti.vives.be.hitchlife.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import ti.vives.be.hitchlife.Interfaces.OnItemClickListener;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;
import ti.vives.be.hitchlife.Models.Repository.LocalDataSource.LocalDataSource;
import ti.vives.be.hitchlife.Models.Repository.RemoteDataSource.RemoteDataSource;
import ti.vives.be.hitchlife.Models.Repository.Repository;
import ti.vives.be.hitchlife.R;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.MyViewHolder> {

    private Log[] logs;


    public LogAdapter(Log[] logs){
        this.logs = logs;
    }

    public LogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_recyclerview_item,null);
        LogAdapter.MyViewHolder viewHolder = new LogAdapter.MyViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.licensePLate.setText(logs[i].licensePlate);
        holder.location.setText(logs[i].location);
        holder.contact.setText(logs[i].contactId+"");
        holder.dateTime.setText(logs[i].dateTime);
        if(logs[i].getCarPic()!=null) {
            //todo handle bitmaps better -> Glide library https://github.com/bumptech/glide
            Bitmap bitmap = BitmapFactory.decodeByteArray(logs[i].getCarPic(), 0, logs[i].getCarPic().length);
            if (bitmap != null)
                holder.carPic.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return logs.length;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.log_dateTime_textView)
        TextView dateTime;
        @BindView(R.id.log_carpic_imageView)
        ImageView carPic;

        @BindView(R.id.log_licenseplate_textView)
        TextView licensePLate;

        @BindView(R.id.log_location_textView)
        TextView location;

        @BindView(R.id.log_contact_textView)
        TextView contact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


    }
}
