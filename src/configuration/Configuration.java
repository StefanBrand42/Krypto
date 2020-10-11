package configuration;

import crypto.Algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public enum Configuration {
    instance;

    // common
    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");
    public Algorithms algorithms = Algorithms.RSA;  // default Algorithm

    // database
    public final String dataDirectory = userDirectory + fileSeparator + "hsqldb" + fileSeparator;
    public final String databaseFile = dataDirectory + "datastore.db";
    public final String driverName = "jdbc:hsqldb:";
    public final String username = "sa";
    public final String password = "";

    // components
    public String componentDirectory = userDirectory + fileSeparator + "components";

    // keyfiles
    public String keyfileDirectory = userDirectory + fileSeparator + "keys" + fileSeparator;

    // logfiles
    public String logfileDirectory = userDirectory + fileSeparator + "logfiles";

    public List<String> getAlgorithmFileNames() {
        List<String> filenames = new ArrayList<>();
        String path = componentDirectory;
        try {
            Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .forEach((f)->{
                        String file = f.toString();
                        if( file.endsWith(".jar")){
                            String fName = f.getFileName().toString().toLowerCase();
                            filenames.add(fName.substring(0,fName.length()-4));}
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filenames;
    }


    public List<String> getAlgoTypsFromFileNames() {
        List<String> list = getAlgorithmFileNames();
        List<String> listAlgoTyp = new ArrayList<>();


        for (String algo : list) {
            // Without crackers
            if (!algo.endsWith("cracker")) {
                listAlgoTyp.add(algo);
            }
            //logger.log("found algo: "+algo);
        }
        return listAlgoTyp;
    }


    public boolean checkIfAlgoExist(String algoTyps){
        List<String> list = getAlgoTypsFromFileNames();
        for (String algo:list) {
            if (algo.equalsIgnoreCase(algoTyps)){
                return  true ;
            }


        }
        return false;
    }


    public List<String> getKeyFileNames() {
        List<String> filenames = new ArrayList<>();
        String path = keyfileDirectory;

        try {
            Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .forEach((f)->{
                        String file = f.toString();
                        if( file.endsWith(".json")){
                            String fName = f.getFileName().toString().toLowerCase();
                            filenames.add(fName.substring(0,fName.length()-5));}
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filenames;
    }


    public boolean checkIfKeyFileNameExist(String keyFileName){
        List<String> list = getKeyFileNames();
        for (String key:list) {
            if (key.equalsIgnoreCase(keyFileName)) {
                return  true ;
            }


        }
        return false;
    }







    public String getAlgorithmPath(Algorithms algorithm) {
        String path = componentDirectory;
        switch (algorithm) {
            case SHIFT:
                path += fileSeparator + algorithm.toString().toLowerCase() + fileSeparator + "jar" + fileSeparator + "Shift.jar";
                break;
            case RSA:
                path += fileSeparator + algorithm.toString().toLowerCase() + fileSeparator + "jar" + fileSeparator + "RSA.jar";
                break;
            default:
                path = "ERROR";
        }
        return path;
    }

    public String getCrackerPath(Algorithms algorithm) {
        String path = componentDirectory;
        switch (algorithm) {
            case SHIFT:
                path += fileSeparator + algorithm.toString().toLowerCase() + "_cracker" + fileSeparator + "jar" + fileSeparator + "ShiftCracker.jar";
                break;
            case RSA:
                path += fileSeparator + algorithm.toString().toLowerCase() + "_cracker" + fileSeparator + "jar" + fileSeparator + "RSACracker.jar";
                break;
            default:
                path = "ERROR";
        }
        return path;
    }
}