package hcmute.nhom13.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class FoodDAO extends SQLiteOpenHelper {

    public FoodDAO(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void InsertFood(int id_res, String name, Double price,String description, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO food VALUES(null,'"+id_res+"',?,?,?,?)";
        SQLiteStatement statement =database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(2,name);
        statement.bindDouble(3,price);
        statement.bindString(4,description);
        statement.bindBlob(5,image);

        statement.executeInsert();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
