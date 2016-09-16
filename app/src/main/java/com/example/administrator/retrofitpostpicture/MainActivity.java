package com.example.administrator.retrofitpostpicture;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ImageView src;
    private ImageView res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btntext=(Button)findViewById(R.id.btn_text);
        src=(ImageView)findViewById(R.id.src);
        res=(ImageView)findViewById(R.id.res);
        Picasso.with(this).load(new File(Environment.getExternalStorageDirectory()+"/33.png")).into(src);
        btntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("http://apis.juhe.cn/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                final Api phone=retrofit.create(Api.class);
                phone.getPhoneNumBerInfo("15123339336", "2f121eb8bf260e938df638ec3cc2e5d4")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PhoneNumInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("++++++++",e.getMessage());
                    }

                    @Override
                    public void onNext(PhoneNumInfo phoneNumInfo) {
                           Log.i("++++++++",phoneNumInfo.getResult().getCity());
                    }
                });
            }
        });
        Button btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("http://image.ideayapai.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                Api phone=retrofit.create(Api.class);
                File file=new File(Environment.getExternalStorageDirectory()+"/33.png");
                final RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
                phone.getPictureCheck(0,1,requestFile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<PictureModel>() {
                            @Override
                            public void onCompleted() {
                            }
                            @Override
                            public void onError(Throwable e) {
                                Log.e("**************", "----------****");
                            }

                            @Override
                            public void onNext(PictureModel pictureModel) {
//                                    String path="http://www.ideayapai.com/Application/Home/View/default/Public/images/dd.jpg";
//                                Picasso.with(MainActivity.this).load(path).into(res);
                                Log.i("result",pictureModel.getImageGrayUrl());
                                Glide.with(MainActivity.this).load(pictureModel.getImageGrayUrl()).into(res);
                            }
                        });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
