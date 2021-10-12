package com.example.aplikasisipj.API;

import com.example.aplikasisipj.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ResponseModel> ardRetrieveData();

    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseModel> ardcreateData(
            @Field("Nama") String Nama,
            @Field("Tanggal") String Tanggal,
            @Field("Alamat") String Alamat,
            @Field("Fasilitas") String Fasilitas,
            @Field("Status") String Status
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

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModel> ardUpdateData(
            @Field("Id") int id,
            @Field("Nama") String Nama,
            @Field("Tanggal") String Tanggal,
            @Field("Alamat") String Alamat,
            @Field("Fasilitas") String Fasilitas,
            @Field("Status") String Status
    );
}
