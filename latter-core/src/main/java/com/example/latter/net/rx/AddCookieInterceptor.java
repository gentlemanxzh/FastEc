package com.example.latter.net.rx;

import android.support.annotation.NonNull;

import com.example.latter.util.storage.LatterPreference;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Gentleman on 2018/7/1.
 * cookie拦截器
 */

public final class AddCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        Observable
                .just(LatterPreference.getCustomAppProfile("cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String cookie) throws Exception {
                        //给原生API附带上WebView拦截下来的Cookie
                        builder.addHeader("Cookie",cookie);
                    }
                });
        return chain.proceed(builder.build());
    }
}
