import networkCompany.RSAPublicKey;

import java.io.File;
import java.io.FileNotFoundException;

public interface IRSACracker {
    String decrypt(String message, RSAPublicKey publicKey) throws FileNotFoundException;
}
