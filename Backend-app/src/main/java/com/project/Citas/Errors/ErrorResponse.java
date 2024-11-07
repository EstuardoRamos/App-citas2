package com.project.Citas.Errors;

public class ErrorResponse {
    private String mensaje;
    private int statusCode;
    public ErrorResponse(String mensaje, int statusCode) {
        this.mensaje = mensaje;
        this.statusCode = statusCode;
    }
    public String getMensaje() {
        return mensaje;
    }
    public int getStatusCode() {
        return statusCode;
    }
    

}
