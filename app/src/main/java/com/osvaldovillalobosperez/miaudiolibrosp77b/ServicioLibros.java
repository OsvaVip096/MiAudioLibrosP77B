package com.osvaldovillalobosperez.miaudiolibrosp77b;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class ServicioLibros extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    Uri obtenerDireccion;
    boolean libroEnCurso = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (libroEnCurso == false) {
            libroEnCurso = true;
            String input = intent.getStringExtra("inputExtra");
            String bookName = intent.getStringExtra("bookName");
            obtenerDireccion = Uri.parse(input);
            createNotificationChannel();
            Intent notificationIntent = new Intent(ServicioLibros.this, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    ServicioLibros.this,
                    0,
                    notificationIntent,
                    0
            );

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("MiAudioLibrosP77B")
                    .setContentText("Libro en reproducción: " + bookName)
                    .setSmallIcon(R.drawable.libro_peq)
                    .setContentIntent(pendingIntent)
                    .build();

            startForeground(1, notification);

            try {
                StartAudio();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return START_STICKY;
        } else {
            StopAudio();
            String input = intent.getStringExtra("inputExtra");
            String bookName = intent.getStringExtra("bookName");
            obtenerDireccion = Uri.parse(input);
            createNotificationChannel();
            Intent notificationIntent = new Intent(ServicioLibros.this, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    ServicioLibros.this,
                    0,
                    notificationIntent,
                    0
            );

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Mi Audio Libros P77B")
                    .setContentText("Libro en reproducción: " + bookName)
                    .setSmallIcon(R.drawable.libro_peq)
                    .setContentIntent(pendingIntent)
                    .build();

            startForeground(1, notification);

            try {
                StartAudio();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return START_STICKY;
        }
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final IBinder binder = new MiBinder();

    public ServicioLibros() {
    }

    public class MiBinder extends Binder {
        public ServicioLibros getService() {
            return ServicioLibros.this;
        }
    }

    public IBinder getBinder() {
        return binder;
    }


    private static String TAG = "ForegroundService";
    MediaPlayer mediaPlayer;
    private boolean currentlySendingtAudio = false;

    public void StartAudio() throws IOException {
        Log.d("MENSAJEIMPORTANTE", "Comenzó a reproducirce audio");
        currentlySendingtAudio = true;

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(getApplicationContext(), obtenerDireccion);
        mediaPlayer.prepare();
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
    }

    public void StopAudio() {
        Log.d("MENSAJEIMPORTANTE", "Se detuvo la reproducción de audio");
        currentlySendingtAudio = false;
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    /*public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        this.stopSelf();
    }*/
}