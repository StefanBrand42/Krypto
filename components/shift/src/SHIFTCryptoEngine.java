import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SHIFTCryptoEngine
{
    private static SHIFTCryptoEngine instance = new SHIFTCryptoEngine();

    public Port port;

    private SHIFTCryptoEngine()
    {
        port = new Port();
    }

    public static SHIFTCryptoEngine getInstance()
    {
        return instance;
    }



    private String encrypt(String message, File keyfile) {
        int key = readKeyfile(keyfile);
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char character = (char) (message.codePointAt(i) + key);
            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }


    private String decrypt(String message, File keyfile) {
        int key = readKeyfile(keyfile);
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char character = (char) (message.codePointAt(i) - key);
            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }

    private int readKeyfile(File keyfile) {
        int key = 0;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(keyfile));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.matches(".* \"key\": .*"))
                {
                    String[] split = currentLine.split(":");
                    key = Integer.parseInt(split[1].trim());
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return key;
    }

    public class Port implements IShiftEngine
    {

        public String decrypt(String message, File keyfile)
        {
            return SHIFTCryptoEngine.this.decrypt(message, keyfile);
        }

        public String encrypt(String message, File keyfile)
        {
            return SHIFTCryptoEngine.this.encrypt(message, keyfile);
        }
    }
}
