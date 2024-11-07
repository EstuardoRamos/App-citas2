package com.project.Citas.Utils;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Component
public class verificacionA2fCache {
    private Cache<String,String> cache;
    
    public verificacionA2fCache(){
        this.cache = Caffeine.newBuilder()
            .expireAfterWrite(300,TimeUnit.SECONDS)
            .build();
    }

    public void guardarCodigo(String correo, String codigo) {
        cache.put(correo, codigo);
    }

     // Obtener el código 2FA por el correo
     public String obtenerCodigo(String correo) {
        return cache.getIfPresent(correo);
    }

    // Eliminar el código una vez usado
    public void eliminarCodigo(String correo) {
        cache.invalidate(correo);
    }
}
