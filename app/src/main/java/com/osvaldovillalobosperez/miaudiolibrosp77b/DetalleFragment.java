package com.osvaldovillalobosperez.miaudiolibrosp77b;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import java.io.IOException;

public class DetalleFragment extends Fragment implements View.OnTouchListener,
        MediaController.MediaPlayerControl {

    public static String ARG_ID_LIBRO = "id_libro";
    MediaController mediaController;

    /**
     * @param id    ID del Libro a utilizar.
     * @param vista Variable que compone el layaout.
     */
    private void ponInfoLibro(int id, View vista) {
        Libro libro = Libro.ejemploLibros().elementAt(id);
        ((TextView) vista.findViewById(R.id.titulo)).setText(libro.titulo);
        String guartarBookLibroName = libro.titulo;
        ((TextView) vista.findViewById(R.id.autor)).setText(libro.autor);
        ((ImageView) vista.findViewById(R.id.portada)).setImageResource(libro.recursoImagen);
        vista.setOnTouchListener(this);
        mediaController = new MediaController(getActivity()); // Establece una nueva instancia.

        Uri audio = Uri.parse(libro.urlAudio); // Uri que maneja la localización de archivos.
        String enviarUri = String.valueOf(audio);

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (preferencias.getBoolean("pref_autoreproducir", true)) {
            Intent serviceIntent = new Intent(getActivity().getApplicationContext(), ServicioLibros.class);
            serviceIntent.putExtra("inputExtra", enviarUri);
            serviceIntent.putExtra("bookName", guartarBookLibroName);
            ContextCompat.startForegroundService(getActivity(), serviceIntent);
        }
    }

    public void ponInfoLibro(int id) {
        ponInfoLibro(id, getView());

        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(getView().findViewById(R.id.fragment_detalle));
        mediaController.setPadding(0, 0, 0, 110);
        mediaController.setEnabled(true);
        mediaController.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_detalle,
                container, false);
        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt(ARG_ID_LIBRO);
            ponInfoLibro(position, vista);
        } else {
            ponInfoLibro(0, vista);
        }
        return vista;
    }


    ServicioLibros servicioLibros;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mediaController.show();
        return false;
    }


    @Override
    public void onStop() {
        mediaController.hide();
        try {
            /*mediaPlayer.stop();
            mediaPlayer.release();*/
            servicioLibros.mediaPlayer.stop();
            servicioLibros.mediaPlayer.release();
        } catch (Exception e) {
            Log.d("AudioLibros", "Error en MediaPlayer.stop()");
        }
        super.onStop();
    }

    @Override
    public void start() {
        //mediaPlayer.start();
        servicioLibros.mediaPlayer.start();
    }

    @Override
    public void pause() {
        //mediaPlayer.pause();
        servicioLibros.mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        //return mediaPlayer.getDuration();
        return servicioLibros.mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        try {
            //return mediaPlayer.getCurrentPosition();
            return servicioLibros.mediaPlayer.getCurrentPosition();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void seekTo(int i) {
        //mediaPlayer.seekTo(i);
        servicioLibros.mediaPlayer.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        //return mediaPlayer.isPlaying();
        return servicioLibros.mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onResume() {
        DetalleFragment detalleFragment = (DetalleFragment)
                getFragmentManager().findFragmentById(R.id.detalle_fragment);
        if (detalleFragment == null) {
            ((MainActivity) getActivity()).mostrarElementos(false);
        }
        super.onResume();
    }
}