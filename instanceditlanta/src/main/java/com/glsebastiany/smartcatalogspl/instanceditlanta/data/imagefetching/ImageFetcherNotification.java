package com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching;


import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


public class ImageFetcherNotification{

    private static final String NOTIFICATION_TAG = "ImageDownloader";
    private static final String TITLE = "Fazendo download de imagens";

    private int imagesProcessed = 0;
    private int neededImages = 0;

    private final NotificationCompat.Builder mBuilder;
    private final NotificationManagerCompat mNotificationManager;

    public ImageFetcherNotification(Context context){

        mNotificationManager = NotificationManagerCompat.from(context);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(android.R.drawable.stat_sys_download)
                .setOngoing(true)
                .setLocalOnly(true)
                .setContentTitle(TITLE)
                .setShowWhen(false)
                .setContentText("Conectando com o servidor")
                .setTicker("Iniciando download de imagens");
    }

    public void start() {
        notify(mBuilder.build());
    }

    public void progress(int progress, int quantity) {

        mBuilder.setProgress(100, progress, false)
                .setNumber(quantity)
                .setOngoing(true)
                .setContentText("Atualizando");

        notify(mBuilder.build());
    }

    public void finalSucess() {
        mBuilder.setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setOngoing(false)
                .setShowWhen(true)
                .setAutoCancel(true)
                .setProgress(0, 0, false)
                .setTicker("Download completo")
                .setContentText(null)
                .setContentTitle("Download completo");

        notify(mBuilder.build());
    }

    public void finalError() {
        mBuilder.setSmallIcon(android.R.drawable.stat_notify_error)
                .setOngoing(false)
                .setShowWhen(true)
                .setAutoCancel(true)
                .setProgress(0, 0, false)
                .setNumber(0)
                .setTicker("Houve um erro na atualização")
                .setContentText(null)
                .setContentTitle("Houve um erro na atualização");

        notify(mBuilder.build());
    }

    public void cancel() {
        mNotificationManager.cancel(NOTIFICATION_TAG, 0);
    }



    private void notify( Notification notification) {
        mNotificationManager.notify(NOTIFICATION_TAG, 0, notification);
    }





    public void informStatus() {
        if (neededImages == 0){
            progress(100, imagesProcessed);
        } else {
            Log.d(NOTIFICATION_TAG, "Processed " + imagesProcessed * 100 / neededImages + "% of images.");
            progress(imagesProcessed * 100 / neededImages, imagesProcessed);
        }
    }

    public void setNeededImages(int neededImages) {
        this.neededImages = neededImages;
    }

    public void increaseProcessedImages(){
        imagesProcessed++;
    }
}