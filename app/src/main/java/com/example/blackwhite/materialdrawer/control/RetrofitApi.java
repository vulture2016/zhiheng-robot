package com.example.blackwhite.materialdrawer.control;


import com.example.blackwhite.materialdrawer.entity.InfoEntity;
import com.example.blackwhite.materialdrawer.entity.LoginEntity;
import com.example.blackwhite.materialdrawer.entity.LogoutEntity;
import com.example.blackwhite.materialdrawer.entity.MessageEntity;
import com.example.blackwhite.materialdrawer.entity.RecordEntity;
import com.example.blackwhite.materialdrawer.entity.RegisterLoginEntity;
import com.example.blackwhite.materialdrawer.entity.SysInfoEntity;
import com.example.blackwhite.materialdrawer.entity.TokenEntity;
import com.example.blackwhite.materialdrawer.entity.UpdateEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitApi {

    // 请求图灵API接口，获得问答信息
//    @GET("api")
//    Observable<MessageEntity> postMessageInfo(@Query("key") String key, @Query("info") String info);
//    @POST("DealData")
//    Observable<MessageEntity> postMessageInfo(@Query("input_data") String info, @Query("token") String token);

    @FormUrlEncoded
    @POST("DealData")
    Observable<MessageEntity> postMessageInfo(@Field("input_data") String input_data, @Field("token") String token);

    @FormUrlEncoded
    @POST("Register")
    Observable<RegisterLoginEntity> postRegisterInfo(@Field("username") String username, @Field("password")
            String password, @Field("password2") String password2);

    @FormUrlEncoded
    @POST("LoginUser")
    Observable<RegisterLoginEntity> postLoginInfo(@Field("username") String username, @Field("password")
            String password);

    @POST("LoginUser")
    Observable<RegisterLoginEntity> postLoginInfo(@Body LoginEntity entity);

    @FormUrlEncoded
    @POST("isLogin")
    Observable<TokenEntity> postIsLoginInfo(@Field("token") String token);

    @FormUrlEncoded
    @POST("OutLogin")
    Observable<LogoutEntity> postLogoutInfo(@Field("token") String token);

    @FormUrlEncoded
    @POST("DealData")
    Observable<RecordEntity> postRecordInfo(@Field("input_data") String input_data, @Field("token") String token);

    @FormUrlEncoded
    @POST("SendUserData")
    Observable<InfoEntity> getUserInfo(@Field("token") String token);

    @FormUrlEncoded
    @POST("updateUser")
    Observable<UpdateEntity> updateUserInfo(@Field("token") String token, @Field("nickname") String nickname,
                                            @Field("phone") int phone, @Field("gender") String gender,
                                            @Field("local") String local, @Field("email") String email);

    @FormUrlEncoded
    @POST("getMsgFromOther")
    Observable<SysInfoEntity> getSysInfo(@Field("token") String token);
}
