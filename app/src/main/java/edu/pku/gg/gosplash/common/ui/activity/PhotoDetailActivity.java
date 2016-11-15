package edu.pku.gg.gosplash.common.ui.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.thin.downloadmanager.DownloadManager;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;

import edu.pku.gg.gosplash.R;
import edu.pku.gg.gosplash.common.data.PhotoDetails;
import edu.pku.gg.gosplash.common.service.PhotoService;

import edu.pku.gg.gosplash.common.ui.CircleImageView;
import edu.pku.gg.gosplash.common.ui.GoToast;
import edu.pku.gg.gosplash.common.utils.TypefaceUtils;
import rx.Subscriber;


public class PhotoDetailActivity extends AppCompatActivity{
    private ThinDownloadManager downloadManager;

    private String id;
    private String avatarUrl;
    private String downloadUrl;
    private int downloadId;

    private KenBurnsView photo;
    private Subscriber subscriber;
    private TextView photoTitle;
    private TextView photoDes;
    private TextView photoSize;
    private CircleImageView avatar;
    private Button downloadButton;
    private Button cancelButton;

    private ProgressBar mProgress;
    private RelativeLayout mProgressLayout;

    MyDownloadDownloadStatusListenerV1
            myDownloadStatusListener = new MyDownloadDownloadStatusListenerV1();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        Intent intent=getIntent();

        id = intent.getStringExtra("id");
        avatarUrl = intent.getStringExtra("avatar");


        init();
    }

    private void init(){
        downloadManager = new ThinDownloadManager(1);

        photo = (KenBurnsView) findViewById(R.id.photo);
        photoTitle = (TextView)findViewById(R.id.photo_title);
        photoDes = (TextView)findViewById(R.id.photo_des);
        TypefaceUtils.setTypeface(this, photoDes);
        photoSize = (TextView)findViewById(R.id.photo_size);
        avatar = (CircleImageView)findViewById(R.id.avatar);
        mProgress = (ProgressBar)findViewById(R.id.progressBar);
        mProgressLayout = (RelativeLayout)findViewById(R.id.progress_layout);

        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadManager.cancel(downloadId);
                mProgressLayout.setVisibility(View.INVISIBLE);
            }
        });
        downloadButton = (Button)findViewById(R.id.download_btn);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downloadManager.query(downloadId) == DownloadManager.STATUS_NOT_FOUND) {
                    mProgressLayout.setVisibility(View.VISIBLE);
                    File sdCard = Environment.getExternalStorageDirectory();
                    String folder = sdCard.getAbsolutePath() + "/Gosplash" ;
                    File dir = new File(folder);
                    Log.d("gaoge",folder);
                    if (!dir.exists()) {
                        if (dir.mkdirs()) {
                        }
                    }
                    Uri destinationUri = Uri.parse(dir+"/"+id+".JPG");
                    final DownloadRequest downloadRequest = new DownloadRequest(Uri.parse(downloadUrl))
                            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                            .setStatusListener(myDownloadStatusListener);
                    downloadId = downloadManager.add(downloadRequest);
                }
            }
        });

    }

    public void getAPhoto(String id){

        subscriber = new Subscriber<PhotoDetails>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(PhotoDetails photoDetails) {
                loadImage(photoDetails);
            }
        };
        PhotoService.getInstance().getAPhoto(subscriber, id);

    }

    @Override
    public void onStart(){
        super.onStart();
        getAPhoto(id);
    }

    private void loadImage(PhotoDetails photoDetails){
        downloadUrl = photoDetails.links.download;
        Glide.with(this).load(photoDetails.urls.regular).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                return false;
            }
        }).crossFade(1000).into(photo);
        Glide.with(this).load(avatarUrl).into(avatar);
        if(photoDetails.location !=null) {
            photoTitle.setText("Taken in " + photoDetails.location.city + ", " + photoDetails.location.country);
        }else{
            photoTitle.setText("Taken in unknown location");
        }
        photoDes.setText("by "+photoDetails.user.username+", on " + photoDetails.created_at.split("T")[0]);
        photoSize.setText(photoDetails.width+"x"+photoDetails.height);
    }



    class MyDownloadDownloadStatusListenerV1 implements DownloadStatusListenerV1 {

        @Override
        public void onDownloadComplete(DownloadRequest request) {
            final int id = request.getDownloadId();
          if (id == downloadId) {
                mProgressLayout.setVisibility(View.INVISIBLE);
              GoToast.makeText(getApplicationContext(), GoToast.ToastType.DOWNLOAD_SUCCESS, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onDownloadFailed(DownloadRequest request, int errorCode, String errorMessage) {
            final int id = request.getDownloadId();
            if (id == downloadId) {
                GoToast.makeText(getApplicationContext(), GoToast.ToastType.DOWNLOAD_FAILED, Toast.LENGTH_SHORT).show();
                mProgressLayout.setVisibility(View.INVISIBLE);
                mProgress.setProgress(0);
            }
        }

        @Override
        public void onProgress(DownloadRequest request, long totalBytes, long downloadedBytes, int progress) {
            int id = request.getDownloadId();
            if (id == downloadId) {
                mProgress.setProgress(progress);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(mProgressLayout.getVisibility() == View.VISIBLE){
            downloadManager.cancel(downloadId);
            mProgressLayout.setVisibility(View.INVISIBLE);
        }else{
            finish();
        }
    }





}
