package com.example.app_votantes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteHelper extends SQLiteOpenHelper {

    public AdminSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // String sql to create table
        String sqlString = "CREATE TABLE votante(" +
                "cedula INTEGER NOT NULL PRIMARY KEY, " +
                "nombre_apellido VARCHAR(100) NOT NULL, " +
                "nombre_colegio VARCHAR(100) NOT NULL, " +
                "numero_mesa INTEGER NOT NULL" +
                ")";

        db.execSQL(sqlString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        String sqlDropTable = "DROP TABLE IF EXISTS votante";
        String sqlCreateTable = "CREATE TABLE votante(" +
                "cedula INTEGER NOT NULL, " +
                "nombre_apellido VARCHAR(100) NOT NULL, " +
                "nombre_colegio VARCHAR(100) NOT NULL, " +
                "numero_mesa INTEGER NOT NULL" +
                ")";

        db.execSQL(sqlDropTable);
        db.execSQL(sqlCreateTable);
    }
}
