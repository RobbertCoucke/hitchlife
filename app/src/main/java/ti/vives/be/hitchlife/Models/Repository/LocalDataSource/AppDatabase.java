package ti.vives.be.hitchlife.Models.Repository.LocalDataSource;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;

@Database(entities = {Contact.class, Log.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract contactDAO contactDAO();
    public abstract logDAO logDAO();

}
