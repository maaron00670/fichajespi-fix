package com.fichajespi.fichajespidestopapp.httpClient;

import java.io.IOException;

import com.fichajespi.fichajespidestopapp.entity.Fichaje;
import com.fichajespi.fichajespidestopapp.entity.NumeroEmpleado;
import feign.Feign;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

public class RequestSender {

  private static final String uri =
        System.getenv().getOrDefault("BACKEND_URL", "http://localhost:8080"); // ← CAMBIAR IP Y PUERTO A EL DE TU SERVER


  public Fichaje sendRequest(String numero) throws IOException, InterruptedException {

    NumeroEmpleado numEmpleado = new NumeroEmpleado(numero);

    String user = System.getenv("BACKEND_USER");
    String pass = System.getenv("BACKEND_PASS");

    Feign.Builder builder = Feign.builder()
                                 .encoder(new GsonEncoder())
                                 .decoder(new GsonDecoder());

    if (user != null && !user.isEmpty()
        && pass != null && !pass.isEmpty()) {

      RequestInterceptor authInterceptor =
          new BasicAuthRequestInterceptor(user, pass);

      builder.requestInterceptor(authInterceptor);
    }

    FeignController httpClient = builder.target(
        FeignController.class,
        uri
    );

    return httpClient.fichar(numEmpleado);
  }
}
