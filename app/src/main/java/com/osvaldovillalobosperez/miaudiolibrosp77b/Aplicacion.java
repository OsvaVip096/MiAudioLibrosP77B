package com.osvaldovillalobosperez.miaudiolibrosp77b;

import android.app.Application;

import java.util.Vector;

public class Aplicacion extends Application {
    private Vector<Libro> vectorLibros;
    private AdaptadorLibros adaptador;


    @Override
    public void onCreate() {
        super.onCreate();
        vectorLibros = Libro.ejemploLibros();
        adaptador = new AdaptadorLibros(this, vectorLibros);

/*        Saldo misaldo =
                Saldo.getInstancia();

        misaldo.inicializa(this);
        misaldo.putSaldo(100);

        misaldo.getSaldo();*/


    }
    public AdaptadorLibros getAdaptador() {
        return adaptador;
    }
    public Vector<Libro> getVectorLibros() {
        return vectorLibros;
    }
}