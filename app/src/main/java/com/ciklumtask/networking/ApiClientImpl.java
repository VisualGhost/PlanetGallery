package com.ciklumtask.networking;

import com.ciklumtask.BuildConfig;
import com.ciklumtask.util.DebugLogger;
import com.facebook.stetho.server.http.HttpStatus;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClientImpl implements ApiClient {

    private static final String TAG = ApiClientImpl.class.getSimpleName();
    private static final String BASE_URL = BuildConfig.BASE_URL;

    @Override
    public ParsedModel call(int page) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(getUrlForPage(page)).build();
        ResponseData responseData = null;
        try {
            Response response = client.newCall(request).execute();


            if (response.isSuccessful()) {
                Reader in = response.body().charStream();
                BufferedReader reader = new BufferedReader(in);

                responseData = new Gson().fromJson(reader, ResponseData.class);
                reader.close();

            } else if (response.code() == HttpStatus.HTTP_NOT_FOUND) {
                return new ParsedModelImpl(true);
            }
        } catch (IOException e) {
            DebugLogger.e(TAG, e.toString());
        }

        return new ParsedModelImpl(responseData);
    }

    private String getUrlForPage(int page) {
        return String.format(BASE_URL, page);
    }
}
