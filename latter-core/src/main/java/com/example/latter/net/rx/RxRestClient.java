package com.example.latter.net.rx;

import android.content.Context;

import com.example.latter.net.HttpMethod;
import com.example.latter.net.RestCreator;
import com.example.latter.ui.loader.LatteLoader;
import com.example.latter.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author gentleman
 * @date 2018/5/13
 * @function 用来创建建造者模式, Retrofit网络请求时的参数配置
 */

public class RxRestClient {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    private final File FILE;

    public RxRestClient(String URL,
                        WeakHashMap<String, Object> params,
                        RequestBody body,
                        File file,
                        LoaderStyle loaderStyle,
                        Context context) {
        this.URL = URL;
        PARAMS = params;
        this.BODY = body;
        this.FILE = file;
        this.LOADER_STYLE = loaderStyle;
        this.CONTEXT = context;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }


    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;

        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            default:
                break;
        }

        return observable;
    }



    public final Observable<String> get() {
      return   request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (BODY == null) {
           return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                //post原始数据是params必须要为空
                throw new RuntimeException("params must be null");
            }
           return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (BODY == null) {
          return   request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                //post原始数据是params必须要为空
                throw new RuntimeException("params must be null");
            }
           return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
      return   request(HttpMethod.DELETE);
    }

    public final Observable<String> upload(){
       return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download(){

        return RestCreator.getRxRestService().download(URL,PARAMS);

    }


}
