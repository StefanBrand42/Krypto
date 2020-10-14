import java.io.File;

public interface IRSACryptoEngine {
    String decrypt(String message, File keyfile);
    String encrypt(String message, File keyfile);
}