package com.smartbooking.app.api;

import android.content.Context;

import com.smartbooking.app.utils.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String path = original.url().encodedPath();
        Request.Builder builder = original.newBuilder();
        boolean publico = path.contains("/api/auth/login")
                || path.contains("/api/auth/register")
                || path.contains("/api/auth/refresh");
        if (!publico) {
            String token = SessionManager.getAccess(context);
            if (token != null && !token.isEmpty()) {
                builder.header("Authorization", "Bearer " + token);
            }
        }
        return chain.proceed(builder.build());
    }
}
