package ti.vives.be.hitchlife.Models.Repository.LocalDataSource;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;
import ti.vives.be.hitchlife.Models.Repository.Entities.Log;

@Dao
public interface logDAO {

    @Query("SELECT * FROM log")
    List<Log> getAll();

    @Query("SELECT * FROM log WHERE contactId IN (:contactIds)")
    List<Log> findLogByContactIds(int[] contactIds);

    @Query("SELECT * FROM log WHERE id IN (:ids)")
    List<Log> findByids(int[] ids);

    @Insert
    long[] insertAll(Log... logs);

    @Delete
    void delete(Log log);

    @Update
    void update(Log log);
}
