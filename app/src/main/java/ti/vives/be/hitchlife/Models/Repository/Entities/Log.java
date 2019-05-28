package ti.vives.be.hitchlife.Models.Repository.Entities;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.net.URI;
import java.time.LocalDateTime;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Contact.class,
                                   parentColumns = "id",
                                  childColumns = "contactId",
                                  onDelete = CASCADE ))
public class Log {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="dateTime")
    public String dateTime;

    @ColumnInfo(name="contactId")
    public int contactId;

    @ColumnInfo(name="licensePlate")
    public String licensePlate;

    @ColumnInfo(name="carInfo")
    public String carInfo;

    @ColumnInfo(name="location")
    public String location;

    @ColumnInfo(name="carPic", typeAffinity = ColumnInfo.BLOB)
    public byte[] carPic;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getCarPic() {
        return carPic;
    }

    public void setCarPic(byte[] carPic) {
        this.carPic = carPic;
    }
}
