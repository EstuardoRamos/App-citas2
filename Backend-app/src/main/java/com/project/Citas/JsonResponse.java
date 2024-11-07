package com.project.Citas;

public class JsonResponse {
    private int statusCode;
    private String mensaje;
    public JsonResponse(int statusCode, String mensaje) {
        this.statusCode = statusCode;
        this.mensaje = mensaje;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    
}
