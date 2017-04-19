package ru.developsdm.amproject.model.web;

import retrofit2.Call;
import retrofit2.http.POST;
import ru.developsdm.amproject.model.DataRoot;

/**
 * REST API.
 * <p/>
 * Created by Daniil Savelyev in 2017.
 */
public interface RestAPI {
    @POST("catalog")
    Call<DataRoot> getData();
}
