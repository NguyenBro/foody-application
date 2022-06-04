package hcmute.nhom13.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class RestaurantDAO extends SQLiteOpenHelper {
    public RestaurantDAO(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
}
