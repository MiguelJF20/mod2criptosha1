package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Util {
    /**
     * Convierte una cadena a su hash SHA-1 (hexadecimal)
     */
    public static String toSha1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = md.digest(input.getBytes());
            
            // Convertir bytes a hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar SHA-1", e);
        }
    }

    // Método para verificar contraseña (comparar con hash almacenado)
    public static boolean verificarContraseña(String input, String hashAlmacenado) {
        return toSha1(input).equals(hashAlmacenado);
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        String contraseñaPlana = "1234";
        String hash = toSha1(contraseñaPlana);
        
        System.out.println("Contraseña original: " + contraseñaPlana);
        System.out.println("SHA-1: " + hash);
        System.out.println("Verificación: " + verificarContraseña(contraseñaPlana, hash));
    }
}