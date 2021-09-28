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
}
