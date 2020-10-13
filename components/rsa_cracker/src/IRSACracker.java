import java.io.File;
import java.io.FileNotFoundException;

public interface IRSACracker {
    String decrypt(String message, File publicKey) throws FileNotFoundException;
}
