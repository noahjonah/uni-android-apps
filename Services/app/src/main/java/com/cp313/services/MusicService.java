package com.cp313.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the media player
        mediaPlayer = MediaPlayer.create(this, R.raw.kendricklamar_notlikeus);  // Your music file in res/raw/
        mediaPlayer.setLooping(true); // Keep playing in a loop
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create a notification for foreground service
        Notification notification = new NotificationCompat.Builder(this, "MUSIC_CHANNEL")
                .setContentTitle("Playing Music")
                .setContentText("Your music is playing in the background")
                .setSmallIcon(R.drawable.baseline_library_music_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        // Start foreground service with notification
        startForeground(1, notification);

        // Start playing the music
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

        return START_STICKY; // Restart service if it gets killed
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Not a bound service
    }
}
