package com.osvaldovillalobosperez.miaudiolibrosp77b;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class SelectorFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdaptadorLibros adaptadorLibros;
    Vector<Libro> vectorLibros;
    AppCompatActivity actividad;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            this.actividad = (AppCompatActivity) context;
            vectorLibros = Libro.ejemploLibros();
            adaptadorLibros = new AdaptadorLibros(this.actividad, vectorLibros);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_selector, container, false);
        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad, 2));
        recyclerView.setAdapter(adaptadorLibros);
        adaptadorLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(actividad, "Seleccionando elemento: " +
                        recyclerView.getChildAdapterPosition(view), Toast.LENGTH_SHORT).show();

                String t = ((TextView) view.findViewById(R.id.titulo)).getText().toString();

                String t2 = vectorLibros.elementAt(recyclerView.getChildAdapterPosition(view)).titulo;

                Toast.makeText(actividad, "Seleccionado el elemento: " + t + ", " + t2,
                        Toast.LENGTH_SHORT).show();*/

                ((MainActivity) actividad).mostrarDetalle(
                        recyclerView.getChildAdapterPosition(view));
            }
        });
        return v;
    }
}