package logging;

import configuration.Configuration;
import crypto.AlgorithmsTyp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logging implements ILogging {
    private String filePathName;

    public Logging(String enDeCrypt, AlgorithmsTyp algo) {
        long unixSeconds = System.currentTimeMillis() / 1000L;
        this.filePathName = Configuration.instance.logfileDirectory + enDeCrypt + "_" + algo.toString().toLowerCase() + "_" + unixSeconds + ".txt";
    }

    public void createWriteLog(String input) {
        // create new logfile
        File file = new File(filePathName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // write to logfile
        try {
            FileWriter writer = new FileWriter(filePathName, true);
            writer.write(input + "\n");
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
