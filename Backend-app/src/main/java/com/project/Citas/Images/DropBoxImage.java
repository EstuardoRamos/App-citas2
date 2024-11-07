package com.project.Citas.Images;

import java.io.IOException;
import java.io.InputStream;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadBuilder;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.CreateSharedLinkWithSettingsErrorException;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class DropBoxImage {
    private static final String ACCES_TOKEN = "sl.B_2pFS-QJlkZcJ_jgEwEND3qIKg2a72qYhqCDHHdMicpFdnDKAeKtDtddPU3rsp5q40gxMFYkhvMTMOFSJP5WqlRZAhPdIf4fguKwv3igT6MrWkVY-84g2zmflPG5XQEOatk4O8gtiZ-laEl_PMQ1AU";
    private static DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/API").build();
    private static DbxClientV2 client = new DbxClientV2(config, ACCES_TOKEN);

    public static String Cargar(MultipartFile Imagen) {
        try {
            String dropboxPath = "/Imagenes/" + Imagen.getOriginalFilename();
            InputStream inputStream = Imagen.getInputStream();
            
            UploadBuilder uploadBuilder = client.files().uploadBuilder(dropboxPath);
            uploadBuilder.withClientModified(new Date());
            uploadBuilder.withMode(WriteMode.ADD);
            uploadBuilder.withAutorename(true);
            
            try {
                // Subir la imagen
                uploadBuilder.uploadAndFinish(inputStream);
                inputStream.close();
                
                // Crear o recuperar el enlace compartido
                SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(dropboxPath);
                String sharedUrl = sharedLinkMetadata.getUrl();
                
                // Modificar la URL para que sea un enlace de descarga directa
                String downloadUrl = sharedUrl.replace("www.dropbox.com", "dl.dropboxusercontent.com").replace("?dl=0", "");
                return downloadUrl;
    
            } catch (CreateSharedLinkWithSettingsErrorException e) {
                // Manejar el caso en que el enlace ya exista
                if (e.errorValue.isSharedLinkAlreadyExists()) {
                    SharedLinkMetadata existingLinkMetadata = e.errorValue.getSharedLinkAlreadyExistsValue().getMetadataValue();
                    String existingUrl = existingLinkMetadata.getUrl();
                    
                    // Modificar la URL existente
                    return existingUrl.replace("www.dropbox.com", "dl.dropboxusercontent.com").replace("?dl=0", "");
                } else {
                    e.printStackTrace();
                    throw new RuntimeException("Error al crear o recuperar el enlace compartido.");
                }
            } catch (DbxException | IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
