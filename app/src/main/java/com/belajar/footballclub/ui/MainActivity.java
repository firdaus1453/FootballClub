package com.belajar.footballclub.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.belajar.footballclub.R;
import com.belajar.footballclub.data.model.ResponseAllTeam;
import com.belajar.footballclub.data.model.TeamsItem;
import com.belajar.footballclub.data.remote.ApiClient;
import com.belajar.footballclub.data.remote.ApiInterface;
import com.belajar.footballclub.helper.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvTeam;
    SwipeRefreshLayout swipeRefresh;

    private List<TeamsItem> teamDataList;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisiasi widget
        rvTeam = findViewById(R.id.recycler_main);
        swipeRefresh = findViewById(R.id.swipe_main);

        // Merequest data ke API
        getData();

        // Pada saat swipe refresh mengambil data ulang
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(false);
            getData();
        });
    }

    private void getData() {
        // Show loading progress
        showProgress();

        // Mengambil data lewat internet dimulai
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseAllTeam> call = apiInterface.getAllTeam(Constant.LEAGUE_NAME);
        call.enqueue(new Callback<ResponseAllTeam>() {
            @Override
            public void onResponse(Call<ResponseAllTeam> call, Response<ResponseAllTeam> response) {
                // Menghilangkan loading progress
                progressDialog.dismiss();

                // Mengambil data dari API dan memasukkan ke dalam variable
                ResponseAllTeam teamResponse = response.body();

                teamDataList = teamResponse.getTeams();

                // Mensetting RecyclerView dan mengirim data yang sudah di dapatkan ke RecyclerView
                rvTeam.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvTeam.setAdapter(new MainAdapter(MainActivity.this, teamDataList));
            }

            @Override
            public void onFailure(Call<ResponseAllTeam> call, Throwable throwable) {
                // Menghilangkan loading progress
                progressDialog.dismiss();

                // Menampilkan pesan error
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        // Membuat progress dialog untuk menampilkan loading
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
