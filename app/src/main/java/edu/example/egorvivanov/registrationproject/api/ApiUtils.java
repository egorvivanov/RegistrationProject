package edu.example.egorvivanov.registrationproject.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.IOException;

import edu.example.egorvivanov.registrationproject.BuildConfig;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private static Gson gson;
    private static AcademyApi academyApi;


    public static OkHttpClient getBasicClient(final String email, final String password, boolean createNewInstance) {
        if (createNewInstance || okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.authenticator(new Authenticator() {
                @Nullable
                @Override
                public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
                    String credential = Credentials.basic(email, password);
                    return response.request().newBuilder().header("Authorization", credential).build();
                }
            });

            if (!BuildConfig.BUILD_TYPE.contains("release")) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }

            okHttpClient = builder.build();
        }

        return okHttpClient;
    }


    public static Retrofit getRetrofit() {
        if (gson == null) {
            gson = new Gson();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    // необходимо для Interceptors
                    .client(getBasicClient("", "", false))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static AcademyApi getApi() {
        if (academyApi == null) {
            academyApi = getRetrofit().create(AcademyApi.class);
        }
        return academyApi;
    }
}