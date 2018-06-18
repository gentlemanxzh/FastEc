package com.example.latter.net;

import com.example.latter.app.ConfigType;
import com.example.latter.app.Latte;
import com.example.latter.delegates.LatteDelegate;
import com.example.latter.net.rx.RxRestService;
import com.example.latter.util.log.LatteLogger;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        private static final String BASE_URL = (String) Latte.getConfigurators().get(ConfigType.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .build();
    }



    /**
     * 进行OkHttpClient配置
     */
    private static final class OkHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = (ArrayList<Interceptor>) Latte.getConfigurators().get(ConfigType.INTERCEPTOR);
        private static OkHttpClient.Builder addInterceptor(){
            if (INTERCEPTORS!=null&&!INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            BUILDER.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LatteLogger.d(message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BASIC));

            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
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

    private static final class RxRestServiceHolder{
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);

    }

    public static RxRestService getRxRestService(){
        return RxRestServiceHolder.REST_SERVICE;
    }
}
