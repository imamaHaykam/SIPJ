package com.example.aplikasisipj.API;

import com.example.aplikasisipj.Model.ResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ResponseModel> ardRetrieveData();

    @Multipart
    @POST("create.php")
    Call<ResponseModel> ardcreateData(
            @Part("Nama") RequestBody Nama,
            @Part("Tanggal") RequestBody Tanggal,
            @Part("Alamat") RequestBody Alamat,
            @Part("Fasilitas") RequestBody Fasilitas,
            @Part("Status") RequestBody Status,
            @Part MultipartBody.Part Image
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel> ardDeleteData(
            @Field("Id") int id
    );

    @FormUrlEncoded
    @POST("get.php")
    Call<ResponseModel> ardGetData(
            @Field("Id") int id
    );

    @Multipart
    @POST("update.php")
    Call<ResponseModel> ardUpdateData(
            @Part("Id") RequestBody Id,
            @Part("Nama") RequestBody Nama,
            @Part("Tanggal") RequestBody Tanggal,
            @Part("Alamat") RequestBody Alamat,
            @Part("Fasilitas") RequestBody Fasilitas,
            @Part("Status") RequestBody Status,
            @Part MultipartBody.Part Image
    );
}
