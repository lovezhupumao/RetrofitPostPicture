package com.example.administrator.retrofitpostpicture;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/14.
 */
public interface Api {

    /**
     * http://image.ideayapai.com/upload?defectType=0&perunit=1
     * @param defectType
     * @param perunit
     * @return
     */
    @Multipart

    @POST("upload")
    Observable<PictureModel> getPictureCheck(@Query("defectType") int defectType,
                                             @Query("perunit") double perunit,
                                             @Part("file\"; filename=\"image.png\"") RequestBody file);

}
