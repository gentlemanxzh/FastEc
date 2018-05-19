package com.example.latter.net;

import com.example.latter.app.ConfigType;
import com.example.latter.app.Latte;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author gentleman
 * @date 2018/5/14
 * @function 用来进行Retrofit配置
 */

public class RestCreator {

    public static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();

    public static final class ParamsHolder{
        public static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) Latte.getConfigurators().get(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .build();
    }



    /**
     * 进行OkHttpClient配置
     */
    private static final class OkHttpHolder{
        private static final int TIME_OUT = 60;

        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);

    }

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }
}
