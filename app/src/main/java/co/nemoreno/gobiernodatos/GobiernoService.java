package co.nemoreno.gobiernodatos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GobiernoService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("jj37-fvz6.json")
    Call<ArrayList<TuristPlace>> getData();
}
