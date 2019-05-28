package ti.vives.be.hitchlife.Models.Repository.LocalDataSource;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;

@Dao
public interface contactDAO {

    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE id IN (:contactIds)")
    List<Contact> loadAllByIds(int[] contactIds);

    @Query("SELECT * FROM contact WHERE first_name LIKE :first AND "+
    "last_name LIKE :last LIMIt 1")
    Contact findByName(String first, String last);

    @Insert
    void insertAll(Contact... contacts);

    @Delete
    void Delete(Contact contact);
}
