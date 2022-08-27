package com.example.app_votantes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    private EditText ed1, ed2, ed3, ed4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        ed3 = (EditText) findViewById(R.id.ed3);
        ed4 = (EditText) findViewById(R.id.ed4);
    }

    public void insertar(View v){
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db_votante", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        long cedula = Long.parseLong(ed1.getText().toString());
        String nombreApellido = ed2.getText().toString();
        String nombreColegio = ed3.getText().toString();
        int numeroMesa = Integer.parseInt(ed4.getText().toString());

        ContentValues registro = new ContentValues();
        registro.put("cedula", cedula);
        registro.put("nombreApellido", nombreApellido);
        registro.put("nombreColegio", nombreColegio);
        registro.put("numeroMesa", numeroMesa);

        bd.insert("votante", null, registro);
        bd.close();

        ed1.setText("");
        ed2.setText("");
        ed3.setText("");
        ed4.setText("");

        Toast.makeText(this, "Se cargaron los datos de la persona.", Toast.LENGTH_SHORT).show();
    }

    public void consultar(View v) {
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db_votante", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        long cedula = Long.parseLong(ed1.getText().toString());

        String sqlConsulta = String.format("SELECT * FROM votante WHERE cedula = %s", cedula);
        Log.d("Resultado consulta: ", sqlConsulta);

        Cursor fila = bd.rawQuery(sqlConsulta, null);

        if (fila.moveToFirst()) {
            ed2.setText(fila.getString(1));
            ed3.setText(fila.getString(2));
            ed4.setText(String.valueOf(fila.getInt(3)));
        } else {
            Toast.makeText(this, "No existe una persona con dicha cédula", Toast.LENGTH_SHORT).show();
        }

        bd.close();
    }

    public void borrar(View v) {
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db_votante", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        long cedula = Long.parseLong(ed1.getText().toString());

        int cantidad = bd.delete("votante", "cedula = " + cedula, null);

        if (cantidad == 1) {
            Toast.makeText(this, "Se borró la persona con dicho documento.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe una persona con dicho documento.", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizar(View v) {
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db_votante", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        long cedula = Long.parseLong(ed1.getText().toString());
        String nombreApellido = ed2.getText().toString();
        String nombreColegio = ed3.getText().toString();
        int numeroMesa = Integer.parseInt(ed4.getText().toString());

        ContentValues registro = new ContentValues();
        registro.put("nombreApellido", nombreApellido);
        registro.put("nombreColegio", nombreColegio);
        registro.put("numeroMesa", numeroMesa);

        int cantidad = bd.update("votante", registro, "cedula = " + cedula, null);

        bd.close();

        if (cantidad == 1) {
            Toast.makeText(this, "Se actualizó la persona con dicho documento.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe una persona con dicho documento.", Toast.LENGTH_SHORT).show();
        }
    }

}
