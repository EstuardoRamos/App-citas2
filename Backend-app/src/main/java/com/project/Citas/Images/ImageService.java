package com.project.Citas.Images;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Base64;


public class ImageService {
    
    public String convertImageToBase64(String imageUrl) {
        try {
            // 1. Obtener el InputStream de la imagen a través de la URL
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            // 2. Leer el InputStream y convertirlo en un array de bytes
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            // 3. Convertir el array de bytes en una cadena en formato Base64 usando java.util.Base64
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            // 4. Cerrar el InputStream y el ByteArrayOutputStream
            inputStream.close();
            byteArrayOutputStream.close();
            return base64Image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
