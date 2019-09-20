package com.osvaldovillalobosperez.miaudiolibrosp77b;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        /*recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);



        AdaptadorLibros adp = new AdaptadorLibros(this,Libro.ejemploLibros());
        adp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = ((TextView)view.findViewById(R.id.titulo)).getText().toString();
                Toast.makeText(MainActivity.this,"se disparo el click item: "+item,Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adp);*/
    }
}
