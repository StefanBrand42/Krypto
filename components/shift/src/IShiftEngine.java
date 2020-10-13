import java.io.File;

public interface IShiftEngine {
    String decrypt(String message, File keyFile);
    String encrypt(String message, File keyFile);
}