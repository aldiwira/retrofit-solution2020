package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.adapter.RecipeAdapter;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.Recipe;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    private RecyclerView view;
    private RecipeAdapter adapter;
    private List<Recipe> recipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipes = new ArrayList<>();
        setup_rv();
        loader();
    }

    public void setup_rv(){
        view = findViewById(R.id.rv_recipe);
        adapter = new RecipeAdapter(this, recipes);
        view.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);
    }
    public void loader(){
        recipes.clear();
        adapter.notifyDataSetChanged();
        fetch_recipe();
    }

    public void snackbarmaker(String arg){
        Snackbar.make(getWindow().getDecorView().getRootView(), arg, Snackbar.LENGTH_SHORT).show();
    }
    public void fetch_recipe(){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Envelope<List<Recipe>>> call = apiInterface.getRecipe();
        call.enqueue(new Callback<Envelope<List<Recipe>>>() {
            @Override
            public void onResponse(Call<Envelope<List<Recipe>>> call, Response<Envelope<List<Recipe>>> response) {
                if (response.isSuccessful()){
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        int id = response.body().getData().get(i).getId();
                        String nama_resep = response.body().getData().get(i).getNama_resep();
                        String deskripsi = response.body().getData().get(i).getDeskripsi();
                        String bahan = response.body().getData().get(i).getBahan();
                        String langkah = response.body().getData().get(i).getLangkah_pembuatan();
                        String foto = response.body().getData().get(i).getFoto();
                        recipes.add(new Recipe(id, nama_resep, deskripsi, bahan, langkah, foto));
                        adapter.notifyDataSetChanged();
                    }
                    snackbarmaker(response.body().getData().get(0).getNama_resep());
                }
            }

            @Override
            public void onFailure(Call<Envelope<List<Recipe>>> call, Throwable t) {
                snackbarmaker("Error Connection");
            }
        });
    }
}
