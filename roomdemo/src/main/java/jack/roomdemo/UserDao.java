package jack.roomdemo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-23
 * describe:创建Dao
 */

@Dao
public interface UserDao {

    //增
    @Insert
    void insert(User... users);

    //删除某一项
    @Delete
    void delete(User... users);

    //删全部
    @Query("DELETE FROM user")
    void deleteAll();

    //改
    @Update
    void update(User... users);

    //查全部
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    //根据字段查询
    @Query("SELECT * FROM User WHERE name= :name")
    User getUserByName(String name);

}
