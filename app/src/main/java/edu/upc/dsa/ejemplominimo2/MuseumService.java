package edu.upc.dsa.ejemplominimo2;

import edu.upc.dsa.ejemplominimo2.models.Museums;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MuseumService {

    @GET("json/pag-ini/1/pag-fi/15")
    Call<Museums> getMuseums();

}
