package logging;

import configuration.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public enum GetLatestLog {
    instance;

    public String getLatestLog() {
        File[] logs = new File(Configuration.instance.logfileDirectory).listFiles();

        if (logs != null) {
            File lastLogfile = null;

            for (File log : logs) {
                if (log.isFile()) {
                    if (lastLogfile == null || getTimeFromFile(log) > getTimeFromFile(lastLogfile)) {
                        lastLogfile = log;
                    }
                }
            }
            try {
                String latestLogPath = lastLogfile.getPath();
                byte[] logContent = Files.readAllBytes(Paths.get(latestLogPath));
                StringBuilder sB = new StringBuilder();
                sB.append("Latest Logfile: ").append(latestLogPath).append("\n");
                sB.append(new String(logContent, StandardCharsets.UTF_8));
                return sB.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return "There are no logfiles.";
        }
    }
    private long getTimeFromFile(File logFile) {
        String logName = logFile.getName().substring(0, logFile.getName().lastIndexOf('.'));
        String[] section = logName.split("_");
        return Long.parseLong(section[2]);
    }
}
