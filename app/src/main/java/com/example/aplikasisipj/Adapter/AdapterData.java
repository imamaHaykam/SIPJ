package com.example.aplikasisipj.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasisipj.API.APIRequestData;
import com.example.aplikasisipj.API.RetroServer;
import com.example.aplikasisipj.Activity.TambahDetailActivity;
import com.example.aplikasisipj.Activity.UpdateActivity;
import com.example.aplikasisipj.AdminActivity;
import com.example.aplikasisipj.Model.DataModel;
import com.example.aplikasisipj.Model.ResponseModel;
import com.example.aplikasisipj.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private Context ctx;
    private List<DataModel> listData;
    private List<DataModel> listSIPJ;
    private int idSIPJ;

    public AdapterData(Context ctx, List<DataModel> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listData.get(position);

        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvNama.setText(dm.getNama());
        holder.tvTanggal.setText(dm.getTanggal());
        holder.tvAlamat.setText(dm.getAlamat());
        holder.tvFasilitas.setText(dm.getFasilitas());
        holder.tvStatus.setText(dm.getStatus());
    }

    @Override
    public int getItemCount() {
        if (listData == null) {
            return 0;
        } else {
            return listData.size();
        }
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvId, tvNama, tvTanggal, tvAlamat, tvFasilitas, tvStatus;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvFasilitas = itemView.findViewById(R.id.tv_fasilitas);
            tvStatus = itemView.findViewById(R.id.tv_status);

            itemView.setOnLongClickListener(view -> {
                AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                dialogPesan.setMessage("Pilih Operasi yang Akan dilakukan");
                dialogPesan.setTitle("Perhatian");
                dialogPesan.setIcon(R.mipmap.ic_launcher_round);
                dialogPesan.setCancelable(true);

                idSIPJ = Integer.parseInt(tvId.getText().toString());

                dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteData();
                        dialogInterface.dismiss();
                        Handler hand = new Handler();
                        hand.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((AdminActivity) ctx).retrieveData();
                            }
                        }, 500);
                    }
                });
//                dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        getData();
//                        dialogInterface.dismiss();
//                    }
//                });

                dialogPesan.show();

                return false;
            });

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), TambahDetailActivity.class);
                intent.putExtra("TAMBAH", listData.get(getBindingAdapterPosition()));
                itemView.getContext().startActivity(intent);
            });
        }

        private void deleteData() {
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(idSIPJ);

            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : " + kode + " | Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void getData() {
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> ambilData = ardData.ardGetData(idSIPJ);

            ambilData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listSIPJ = response.body().getData();

                    int varIdSIPJ = listSIPJ.get(0).getId();
                    String varNamaSIPJ = listSIPJ.get(0).getNama();
                    String varTanggalSIPJ = listSIPJ.get(0).getTanggal();
                    String varAlamatSIPJ = listSIPJ.get(0).getAlamat();
                    String varFasilitasSIPJ = listSIPJ.get(0).getFasilitas();
                    String varStatusSIPJ = listSIPJ.get(0).getStatus();


                    //Toast.makeText(ctx, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                    Intent send = new Intent(ctx, UpdateActivity.class);
                    send.putExtra("xId", varIdSIPJ);
                    send.putExtra("xNama", varNamaSIPJ);
                    send.putExtra("xTanggal", varTanggalSIPJ);
                    send.putExtra("xAlamat", varAlamatSIPJ);
                    send.putExtra("xFasilitas", varFasilitasSIPJ);
                    send.putExtra("xStatus", varStatusSIPJ);
                    ctx.startActivity(send);

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
