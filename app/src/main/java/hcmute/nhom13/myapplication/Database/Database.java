package hcmute.nhom13.myapplication.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import hcmute.nhom13.myapplication.IntroActivity;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Truy van khong tra ve ket qua
    public void QueryData(String s){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(s);
    }


    //Truy van tra ve ket qua
    public Cursor getData(String s){
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();
        return sqLiteDatabase.rawQuery(s,null);
    }

    public void InsertRestaurant(String name, String address,Double distance,Double rating, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO restaurant VALUES(null,?,?,?,?,?)";
        SQLiteStatement statement =database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,address);
        statement.bindDouble(3,distance);
        statement.bindDouble(4,rating);
        statement.bindBlob(5,image);

        statement.executeInsert();

    }

    public void InsertFood(int id_res, String name, Double price,String description, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO food VALUES(null,'"+id_res+"',?,?,?,?)";
        SQLiteStatement statement =database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindDouble(2,price);
        statement.bindString(3,description);
        statement.bindBlob(4,image);

        statement.executeInsert();

    }

    public void InsertUser(String phone, String name, String password,String birth, String email){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO user VALUES(?,?,?,?,?)";
        SQLiteStatement statement =database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,phone);
        statement.bindString(2,name);
        statement.bindString(3,password);
        statement.bindString(4,birth);
        statement.bindString(5,email);

        statement.executeInsert();

    }

    public void InsertFoodToCart(String iduser,int idfood,int quantity){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO cart VALUES(?,?,?)";
        SQLiteStatement statement =database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,iduser);
        statement.bindLong(2,idfood);
        statement.bindLong(3,quantity);


        statement.executeInsert();

    }

    public void InsertBill(String iduser,Double price,Double fee,Double voucher,String note,String date,String time,String status){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO bill VALUES(null,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement =database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,iduser);
        statement.bindDouble(2,price);
        statement.bindDouble(3,fee);
        statement.bindDouble(4,voucher);
        statement.bindString(5,note);
        statement.bindString(6,date);
        statement.bindString(7,time);
        statement.bindString(8,status);

        statement.executeInsert();

    }



    public void InsertDesBill(int idbill,int idfood,int quantity){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO desBill VALUES(null,?,?,?)";
        SQLiteStatement statement =database.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1,idbill);
        statement.bindLong(2,idfood);
        statement.bindLong(3,quantity);
        statement.executeInsert();

    }

    public void InsertNotification(String iduser,String type,Double total,String date,String time){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO notification VALUES(null,?,?,?,?,?)";
        SQLiteStatement statement =database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,iduser);
        statement.bindString(2,type);
        statement.bindDouble(3,total);
        statement.bindString(4,date);
        statement.bindString(5,time);

        statement.executeInsert();

    }



    public void deleteFoodtoCart(String id_user,int id_food){
        IntroActivity.database.QueryData("DELETE FROM cart WHERE id_user='"+id_user+"' AND id_food='"+id_food+"'" );
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
