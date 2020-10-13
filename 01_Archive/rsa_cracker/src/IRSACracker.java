import java.io.File;
import java.math.BigInteger;

public interface IRSACracker
{
    String decrypt(String message, File keyfile) throws InterruptedException;
}