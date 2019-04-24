package jack.roomdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //查
    public void show(View view) {

//        User user1 = UserDatabase
//                .getInstance(MainActivity.this)
//                .getUserDao()
//                .getUserByName("name1");
//
//        Log.i("查 ", "user1: " + user1.toString());
//
//        User user2 = UserDatabase
//                .getInstance(MainActivity.this)
//                .getUserDao()
//                .getUserByName("张三");
//
//        Log.i("查 ", "user2: " + user2.toString());

        List<User> users = UserDatabase
                .getInstance(MainActivity.this)
                .getUserDao()
                .getAllUsers();

        Log.i("查 ", "users: " + users.toString());

    }

    //增
    public void add(View view) {
        User user=new User();
        user.setName("name1");
        user.setAge(18);
        UserDatabase
                .getInstance(MainActivity.this)
                .getUserDao()
                .insert(user);
    }

    //删
    public void delete(View view) {
        //先根据想更改的内容得到那一项的实体类,然后进行删除
        User user = queryByName("张三");
        UserDatabase
                .getInstance(this)
                .getUserDao()
                .delete(user);
    }

    //改
    public void update(View view) {
        User user = queryByName("name1");
        user.setName("张三");
        user.setAge(18);
        UserDatabase
                .getInstance(this)
                .getUserDao()
                .update(user);
    }

    private User queryByName(String name) {
        final User user = UserDatabase
                .getInstance(this)
                .getUserDao()
                .getUserByName(name);

        return user;
    }
}
