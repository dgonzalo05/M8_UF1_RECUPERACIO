package org.dgonzalo.m8_uf1_recuperacio;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements Comentari_Fragment.ComentariListener, AddComentari.addComentarioListener, Login_Fragment.LoginListener {

    ArrayList<Comentari> comentaris;
    ImageView imageView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        // Firebase de base de datos
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("comentaris");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comentari comentari = dataSnapshot.getValue(Comentari.class);
                if (comentari != null){
                    comentaris.add(comentari);
                    Comentari_Fragment lista_fragment = (Comentari_Fragment) getSupportFragmentManager().findFragmentByTag("Lista");
                    if (lista_fragment != null){
                        lista_fragment.addComentari(comentari);
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_camera:
                displayList();
                return true;
            case R.id.toolbar_user:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Login_Fragment()).commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }



    @Override
    public void añadirComentario(String comentari, String recomanada) {
        myRef.child("lista").push().setValue(new Comentari(comentari,recomanada));
    }

    private void displayList() {
        comentaris = new ArrayList<Comentari>();

        myRef.child("lista").removeEventListener(childEventListener);
        Fragment fragment = new Comentari_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, "Lista").commit();
        myRef.child("lista").addChildEventListener(childEventListener);


    }

    @Override
    public void add_comentari_view() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddComentari()).commit();
    }

    @Override
    public void login(String username, String password) {

        MyBBDD_Helper dbHelper = new MyBBDD_Helper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(MyBBDD_Schema.EntradaBBDD.COLUMNA1,username);
        values.put(MyBBDD_Schema.EntradaBBDD.COLUMNA2, password);
        db.insert(MyBBDD_Schema.EntradaBBDD.TABLE_NAME, null, values);
    }

    public class MyBBDD_Schema {
        static final String SQLCreate =
                "CREATE TABLE "+ EntradaBBDD.TABLE_NAME + " (" +
                        "_ID INTEGER PRIMARY KEY, " +
                        EntradaBBDD.COLUMNA1 + " TEXT, " +
                        EntradaBBDD.COLUMNA2 + " TEXT) ";
        static final String SQLUpgrade =
                "DROP TABLE IF EXISTS "+ EntradaBBDD.TABLE_NAME;
        private MyBBDD_Schema(){};
        public class EntradaBBDD {
            static final String TABLE_NAME = "USUARIS";
            static final String COLUMNA1 = "USERNAME";
            static final String COLUMNA2 = "PASSWORD";
        }
    }

    public class MyBBDD_Helper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "usuaris.db";

        public MyBBDD_Helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MyBBDD_Schema.SQLCreate);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(MyBBDD_Schema.SQLUpgrade);
            onCreate(db);
        }
    }


}
