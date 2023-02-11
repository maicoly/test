package ec.fin.baustro.servicevu.security;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.drools.core.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * @author Banco del Austro
 * Clase para encriptar y desencriptar mensajes
 */
public class EncryptDecrypt256 {

    static int iterations = 1000;
    private static final String ALGORITHM = "AES";
    private static final String CIPHERS = "AES/CBC/PKCS5Padding";
    static int keySize = 256;

    /**
     * Desencriptar un mensaje, enviado desde la peticion web.
     *
     * @param message Mensaje representado en bytes
     * @param phrase Frase Secreta
     * @return String desencriptado
     * @throws Exception En caso de no soportar el Cipher PKCS5Padding
     */
    public static String decryptAES(byte[] message, String phrase) {
        try {
            String respuesta = "";
            String salt = new String(Arrays.copyOfRange(message, 0, 32));
            String iv = new String(Arrays.copyOfRange(message, 32, 64));
            String data = new String(Arrays.copyOfRange(message, 64, message.length));
            SecretKey key = generateKey(salt, phrase);
            Cipher cipher = Cipher.getInstance(CIPHERS);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(hex(iv)));
            respuesta = new String(cipher.doFinal(Base64.decodeBase64(data.getBytes())));
            return respuesta;
        } catch (Exception e) {
            System.err.println("Error " + e);
        }
        return null;
    }

    /**
     * Encriptar un mensaje, enviado desde la peticion web.
     *
     * @param message Mensaje representado en String
     * @param phrase  Frase Secreta
     * @return String encriptado
     * @throws Exception En caso de no soportar el Cipher PKCS5Padding
     */
    public static String encryptAES(String message, String phrase) {
        try {
            String salt = random(16);
            String iv = random(16);
            SecretKey key = generateKey(salt, phrase);
            Cipher cipher = Cipher.getInstance(CIPHERS);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(hex(iv)));
            byte[] encrypted = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            String code = Base64.encodeBase64String(encrypted).replaceAll("\r\n", StringUtils.EMPTY);
            return salt + iv + code;
        } catch (Exception e) {
            System.err.println("Error " + e);
        }
        return null;
    }

    /**
     * Genera un texto aleatorio de la longitud solicitada
     *
     * @param length Longitud solicitada
     * @return Texto aleatorio
     */
    private static String random(int length) {
        byte[] salt = new byte[length];
        new SecureRandom().nextBytes(salt);
        return hex(salt);
    }

    /**
     * Codificar un contenido, usando HEX
     *
     * @param bytes bytes a codificar
     * @return HEX codificado
     */
    public static String hex(byte[] bytes) {
        return new String(Hex.encode(bytes));
    }

    /**
     * Decodificar un contenido, usando HEX
     *
     * @param str Cadena a decodificar
     * @return HEX Decodificado
     */
    public static byte[] hex(String str) {
        return Hex.decode(str.getBytes());
    }

    /**
     * Metodo obtiene objeto ScretKey en base al Salt y Phrase
     *
     * @param salt Salt del password
     * @param passphrase Frase secreta
     * @return SecretKey resultante
     * @throws Exception en caso de no soportar el encoding PBKDF2WithHmacSHA1
     */
    private static SecretKey generateKey(String salt, String passphrase) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt), iterations, keySize);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM);
    }
}