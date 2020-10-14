package crypto;

import configuration.Configuration;
import gui.GUI;
import logging.ILogging;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class CryptoCreator  implements  ICryptoCreator{
    private Object port;
    public GUI gui;

    private ILogging logging = null;

    public String encryptMessage(String message, String algo, String key)
    {
        log("Check if algorithm "+algo +" exists.");
        if (getAlgoTypFromName(algo)== null){
            log("Algorithm "+algo +" does not exist.");
            return null;
        }else {
            log("Algorithm "+algo +" exists.");
        }

        log("Creating Method of encryption dynamically out of component.");
         Method cryptMethode = cryptoMethCreate("encrypt", getAlgoTypFromName(algo));

        log("Dynamically loading the corresponding keyfile.");
        String encryptedMess = crypto(message, new File(Configuration.instance.keyfileDirectory + key +".json"), cryptMethode, "encryption");
        log("Connecting was successful.");
        if (encryptedMess != null) {
            log("Encryption of message: " + message + " to " + encryptedMess + " was successful.");
        }
        logging= null;
        return encryptedMess;
    }


    public String decryptMessage(String message, String algo, String key)
    {
        log("Check if algorithm "+algo +" exists.");
        if (getAlgoTypFromName(algo)== null){
            log("Algorithm "+algo +" does not exist.");
            return null;
        }else {
            log("Algorithm "+algo +" exists.");
        }
        log("Creating Method of decryption dynamically out of component.");
        Method cryptMethode = cryptoMethCreate("decrypt", getAlgoTypFromName(algo));
        log("Dynamically loading the corresponding keyfile.");

        String decryptedMess = crypto(message, new File(Configuration.instance.keyfileDirectory + key +".json"), cryptMethode, "decryption");
        log("Connecting was successful.");
        if (decryptedMess != null) {
            log("Decryption of message: " + message + " to " + decryptedMess + " was successful.");
        }
        logging= null;
        return decryptedMess;
    }


    public AlgorithmsTyp getAlgoTypFromName(String algoTyp){
        String test = algoTyp.toUpperCase();
        String test2 = AlgorithmsTyp.SHIFT.toString();
        if (algoTyp.toUpperCase().equals(AlgorithmsTyp.RSA.toString())){
            return AlgorithmsTyp.RSA;
        }else if (algoTyp.toUpperCase().equals(AlgorithmsTyp.SHIFT.toString())){
            return  AlgorithmsTyp.SHIFT;
        }else {
            return  null;
        }
    }


    public Method cryptoMethCreate(String methodType, AlgorithmsTyp algorithmsTyp) { // cryptoVar: decrypt or encrypt
        Object instance;
        try {
            URL[] urls = {new File(Configuration.instance.getAlgorithmPath(algorithmsTyp)).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptoCreator.class.getClassLoader());

            Class classComponent = Class.forName(algorithmsTyp.toString()+"CryptoEngine", true, urlClassLoader);
            instance = classComponent.getMethod("getInstance").invoke(null);
            port = classComponent.getDeclaredField("port").get(instance);
            return port.getClass().getMethod(methodType, String.class,File.class);
        }
        catch (Exception e) {
            System.out.println("--- exception");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String crypto(String message, File key, Method cryptMethode, String enDeCrypt)
    {
        log("Keyfile:  "+ Configuration.instance.keyfileDirectory + key +".json");
        try
        {
            log("Start of "+enDeCrypt+".");
            log("Connecting the corresponding component dynamically via the port. ");
            return (String) cryptMethode.invoke(port, message, key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log("Error while "+enDeCrypt+" : " + e.getMessage()+".");
        }
        return null;
    }


    public void setLogging(ILogging logging) {
        this.logging = logging;

    }

    private void log(String textLog) {
        if (logging != null) {
            logging.createWriteLog(textLog);
        }
    }
}
