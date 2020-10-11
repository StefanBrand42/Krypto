package crypto;

public interface ICryptoCreator {
    String encryptMessage(String message, String algo, String key);
    String decryptMesagge(String message, String algo, String key);
}
