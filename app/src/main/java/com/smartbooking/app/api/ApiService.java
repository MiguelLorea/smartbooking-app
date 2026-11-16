package com.smartbooking.app.api;

import com.smartbooking.app.model.LoginRequest;
import com.smartbooking.app.model.RegistroRequest;
import com.smartbooking.app.model.Reserva;
import com.smartbooking.app.model.TokenResponse;
import com.smartbooking.app.model.Usuario;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/auth/register/")
    Call<Usuario> registro(@Body RegistroRequest datos);

    @POST("api/auth/login/")
    Call<TokenResponse> login(@Body LoginRequest datos);

    @POST("api/auth/refresh/")
    Call<TokenResponse> refresh(@Body Map<String, String> datos);

    @POST("api/auth/logout/")
    Call<Void> logout(@Body Map<String, String> datos);

    @GET("api/perfil/")
    Call<Usuario> perfil();

    @PUT("api/perfil/")
    Call<Usuario> editarPerfil(@Body Map<String, String> datos);

    @GET("api/reservas/")
    Call<List<Reserva>> reservas();

    @POST("api/reservas/")
    Call<Reserva> crearReserva(@Body Reserva reserva);

    @GET("api/reservas/{id}/")
    Call<Reserva> detalleReserva(@Path("id") int id);

    @PUT("api/reservas/{id}/")
    Call<Reserva> editarReserva(@Path("id") int id, @Body Reserva reserva);

    @DELETE("api/reservas/{id}/")
    Call<Void> borrarReserva(@Path("id") int id);

    @GET("api/usuarios/")
    Call<List<Usuario>> usuarios();

    @PATCH("api/usuarios/{id}/")
    Call<Usuario> actualizarUsuario(@Path("id") int id, @Body Map<String, Object> datos);
}
