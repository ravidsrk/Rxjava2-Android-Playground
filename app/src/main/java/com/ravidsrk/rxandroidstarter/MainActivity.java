package com.ravidsrk.rxandroidstarter;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private static String URL = "https://droidcon.in/_themes/droidcon2015/img/logo.png";

    private EditText mEditText;
    private Button mButton;
    private ImageView mImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.image);

        // TODO Create a basic Observable - Our Observable emits "Hello, world!" then completes.

        // TODO Create a Subscriber to consume the data

        // TODO Hook Observable and Subscriber each other using subscribe():

        // TODO Create another Observable using .just for shorter version

        // TODO Create action to consume the .just Observable

        // TODO Hook Observable and Action each other using subscribe():

        // TODO Modify the consumed data

        // TODO Modify the consumed data through operator

        // TODO More operator

        // TODO Iterate an array list

        // TODO Observable.from operator

        // TODO Observable.flatMap operator

        // TODO Instead of the verbose setOnClickListener

        // TODO Log mButton clicked

        // TODO Download the image from URL using AsyncTask

        // TODO Download the image from URL using RXJava

        // TODO Observe text changes on an EditText (RxBinding)

        // TODO Filter text changes on an EditText (RxBinding)
    }

    Observable<List<String>> getUrls(String text) {
        List<String> urlsList = new ArrayList<>();
        urlsList.add(text+ "- http://google.com");
        urlsList.add(text+ "- http://droidcon.in");
        urlsList.add(text+ "- http://jsfoo.in");
        urlsList.add(text+ "- http://hasgeek.tv");
        urlsList.add(text+ "- http://metarefresh.in");
        urlsList.add(text+ "- http://facebook.com");
        urlsList.add(text+ "- http://foursquare.com");

        return Observable.just(urlsList);
    }

    Observable<String> getTitle(String URL) {
        return Observable.just(URL.substring(13));
    }

    private List<String> getInstalledApps() {
        List<String> installedapplist = new ArrayList<>();
        PackageManager packageManager=this.getPackageManager();
        List<ApplicationInfo> appsList = packageManager.getInstalledApplications(0);

        Iterator<ApplicationInfo> it=appsList.iterator();
        while(it.hasNext()){
            ApplicationInfo pk=(ApplicationInfo)it.next();

            String appname = packageManager.getApplicationLabel(pk).toString();
            Log.d(TAG,appname);
            installedapplist.add(appname);
        }

        return installedapplist;
    }


    @Nullable
    private Bitmap getBitmap() {
        Bitmap map = null;

        map = downloadImage(URL);

        return map;
    }

    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }

    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }

    public Observable<Bitmap> getBitmapObservable(){

        return Observable.defer(new Func0<Observable<Bitmap>>() {
            @Override
            public Observable<Bitmap> call() {
                return Observable.just(getBitmap());
            }
        });
    }

    private class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            return getBitmap();
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            mImageView.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it


        // Makes HttpURLConnection and returns InputStream

    }
}
