package co.nemoreno.gobiernodatos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    String baseUrl;
    Retrofit retrofit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> municipios = new ArrayList<>();
        baseUrl = "https://datos.gov.co/resource/";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GobiernoService service = retrofit.create(GobiernoService.class);
        Call<ArrayList<TuristPlace>> places= service.getData();
        context=this;
        places.enqueue(new Callback<ArrayList<TuristPlace>>() {
            @Override
            public void onResponse(Call<ArrayList<TuristPlace>> call, Response<ArrayList<TuristPlace>> response) {
                if (response != null && response.body() !=null)
                {
                    ArrayList<TuristPlace> turistPlaces = response.body();
                    turistPlaces.forEach((place)->{
                        Log.i("APP",place.getNombremunicipio());
                        Log.i("APP",place.getNombresitio());
                        Log.i("APP","--------------------");
                        municipios.add(place.getNombremunicipio());
                    });
                    PlaceAdapter adapter= new PlaceAdapter(turistPlaces);
                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                    recyclerView.addItemDecoration(new DividerItemDecoration(context,  LinearLayoutManager.VERTICAL));
                    LinearLayoutManager layoutManager = new  LinearLayoutManager(context);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TuristPlace>> call, Throwable t) {
            }
        });
    }
}