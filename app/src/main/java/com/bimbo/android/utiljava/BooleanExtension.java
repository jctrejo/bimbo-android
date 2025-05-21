package com.bimbo.android.utiljava;

import static com.bimbo.android.common.Constants.VALUE_6;

import android.util.Patterns;

/**
 * Clase utilitaria que proporciona métodos estáticos para validar datos relacionados con autenticación.
 */
public class BooleanExtension {

    /**
     * Valida si un email tiene un formato correcto.
     *
     * @param email Cadena de texto con el email a validar.
     * @return {@code true} si el email es válido, {@code false} en caso contrario.
     */
    public static boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Valida si una contraseña cumple con los requisitos mínimos.
     * En este ejemplo, la contraseña debe tener al menos 6 caracteres.
     *
     * @param password Cadena de texto con la contraseña a validar.
     * @return {@code true} si la contraseña es válida, {@code false} en caso contrario.
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= VALUE_6;
    }

    /**
     * Valida las credenciales de usuario comparando con valores predefinidos.
     * <p>
     * En un caso real, esta lógica debería verificar contra una base de datos o servicio de autenticación.
     * </p>
     *
     * @param username Nombre de usuario a validar.
     * @param password Contraseña a validar.
     * @return {@code true} si las credenciales coinciden con las esperadas, {@code false} en caso contrario.
     */
    public static boolean isValidCredentials(String username, String password) {
        return "admin@gmail.com".equals(username) && "qwerty123".equals(password);
    }
}
