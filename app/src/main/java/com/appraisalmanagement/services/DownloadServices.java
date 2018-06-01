package com.appraisalmanagement.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.appraisalmanagement.R;
import com.appraisalmanagement.models.Download;
import com.appraisalmanagement.network.RestClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by nineleaps on 31/5/18.
 */

public class DownloadServices  extends IntentService{

    String url,fileName;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    public DownloadServices(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        fileName = intent.getStringExtra("fileName");
        url = intent.getStringExtra("downloadUrl");

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.avd_hide_password_1)
                .setContentTitle(fileName)
                .setContentText("Downloading File")
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());
        initDownload(url);
    }

    private void initDownload(String url) {

        Call<ResponseBody> request = RestClient.getApplicationData().downloadPdfs(url);
        try {
            if (null!=request.clone().execute().body()){
                downloadFile(request.clone().execute().body());
            }
        } catch (IOException e) {
            Log.e("Exception", String.valueOf(e));
        }
    }


    private void downloadFile(ResponseBody body) throws IOException {

        int count;
        byte data[] = new byte[1024 * 4];
        double fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024* 8);
        File mentorwiseDirectory=new File(Environment.getExternalStorageDirectory()+"/MentorWISE");
        if (!(mentorwiseDirectory.exists() && mentorwiseDirectory.isDirectory())){
            mentorwiseDirectory.mkdirs();
        }
        File outputFile = new File(mentorwiseDirectory, fileName);
        OutputStream output = new FileOutputStream(outputFile);
        double total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;
        while ((count = bis.read(data)) != -1) {

            total += count;
            double totalFileSize = (fileSize / (Math.pow(1024, 2)));
            double current = total / (Math.pow(1024, 2));

            double progress = (current * 1000);

            long currentTime = System.currentTimeMillis() - startTime;

            Download download = new Download();
            download.setTotalFileSize(totalFileSize);
            if (currentTime > 1000* timeCount) {


                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendNotification(download);
                timeCount++;
            }

            output.write(data, 0, count);
        }
        onDownloadComplete(outputFile);
        output.flush();
        output.close();
        bis.close();

    }
    private void sendNotification(Download download) {

        int progressSize = (int) (download.getProgress());
        int totalSize = (int) (download.getTotalFileSize() * 1024);

        sendIntent(download);
        notificationBuilder.setProgress(totalSize, progressSize, false);
        notificationBuilder.setContentText("Downloading file " + progressSize + "/" + totalSize + "KB");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(Download download) {

        Intent intent = new Intent("message_progress");
        intent.putExtra("download", download);
        LocalBroadcastManager.getInstance(DownloadServices.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(File file) {

        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        Uri uri = FileProvider.getUriForFile(
                this,
                this.getApplicationContext()
                        .getPackageName() + ".provider", file);


        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setDataAndType(uri, getMimeType(file.getPath()));
        intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("File Downloaded");
        notificationBuilder.setContentIntent(contentIntent);
        notificationManager.notify(0, notificationBuilder.build());

    }

    /**
     * Method to get the file mime type
     *
     * @param url file path or whatever suitable URL you want
     * @return Mime type
     */
    public String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

}
