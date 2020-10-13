package crypto;

import configuration.Configuration;
import gui.GUI;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class CryptoCreator  implements  ICryptoCreator{
    private Object port;
    public GUI gui;

    public String encryptMessage(String message, String algo, String key)
    {
        if (getAlgoTypFromName(algo)== null){
            return null;
        }
        //log("Creating encryption method at runtime from component");
         Method cryptMethode = cryptoMethCreate("encrypt", getAlgoTypFromName(algo));

        //log("Detected encryption algorithm '" + algo + "'");
        String encryptedMess = crypto(message, new File(Configuration.instance.keyfileDirectory + key +".json"),cryptMethode);
        if (encryptedMess != null) {
            //log("Successfully encrypted message '" + message + "' to '" + encryptedMessage + "'");
        }
        return encryptedMess;
    }


    public String decryptMessage(String message, String algo, String key)
    {
        if (getAlgoTypFromName(algo)== null){
            return null;
        }
        //log("Creating decryption method at runtime from component");
        Method cryptMethode= cryptoMethCreate("decrypt", getAlgoTypFromName(algo));

        //log("Detected decryption algorithm '" + algo + "'");
        String decryptedMess = crypto(message, new File(Configuration.instance.keyfileDirectory + key +".json"),cryptMethode);
        if (decryptedMess != null) {
            //log("Successfully decrypted message '" + message + "' to '" + decryptedMessage + "'");
        }
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

            Class clazz = Class.forName(algorithmsTyp.toString()+"CryptoEngine", true, urlClassLoader);
            instance = clazz.getMethod("getInstance").invoke(null);
            port = clazz.getDeclaredField("port").get(instance);
            return port.getClass().getMethod(methodType, String.class,File.class);
        }
        catch (Exception e) {
            System.out.println("--- exception");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String crypto(String message, File key, Method cryptMethode)
    {
        try
        {
            //log("Starting decryption");
            return (String) cryptMethode.invoke(port, message, key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //log("Error while decryption: " + e.getMessage());
        }
        return null;
    }


}
