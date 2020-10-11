package crypto;

import configuration.Configuration;
import gui.GUI;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class CryptoCreator  implements  ICryptoCreator{
    private Object port;
    private Method cryptoVar;
    public GUI gui;

    public Object getPort() {
        return port;
    }

    public Method getCryptoVar() {
        return cryptoVar;
    }

    public String encryptMessage(String message, String algo, String key)
    {

        /*if (!chooseAlgorithm(algo))
        {
            return null;
        }*/

        if (getAlgoTypFromName(algo)== null){
            return null;
        }
        //log("Creating encryption method at runtime from component");
        createCryptoMethod("encrypt", getAlgoTypFromName(algo));

        //log("Detected encryption algorithm '" + algo + "'");
        String encryptedMessage = cryption(message, new File(Configuration.instance.keyfileDirectory + key +".json"));
        if (!encryptedMessage.equals("")) {
            //log("Successfully encrypted message '" + message + "' to '" + encryptedMessage + "'");
        }
        return encryptedMessage;
    }

    public String decryptMesagge(String message, String algo, String key)
    {
        /*if (!chooseAlgorithm(algo))
        {
            return null;
        }*/
        if (getAlgoTypFromName(algo)== null){
            return null;
        }
        //log("Creating decryption method at runtime from component");
        createCryptoMethod("decrypt", getAlgoTypFromName(algo));

        //log("Detected decryption algorithm '" + algo + "'");
        String decryptedMessage = cryption(message, new File(Configuration.instance.keyfileDirectory + key +".json"));
        if (!decryptedMessage.equals("")) {
            //log("Successfully decrypted message '" + message + "' to '" + decryptedMessage + "'");
        }
        return decryptedMessage;
    }


// andern
/*
    private boolean chooseAlgorithm(String algorithm)
    {
        switch (algorithm.toLowerCase()) {
            case "rsa":
                Configuration.instance.algorithmsTyp = AlgorithmsTyp.RSA;
                break;
            case "shift":
                Configuration.instance.algorithmsTyp = AlgorithmsTyp.SHIFT;
                break;
            default:
                return false;
        }
        return true;
    }
    */


    public AlgorithmsTyp getAlgoTypFromName(String algoTyp){
        String test =algoTyp.toUpperCase();
        String test2 = AlgorithmsTyp.SHIFT.toString();
        if (algoTyp.toUpperCase().equals(AlgorithmsTyp.RSA.toString())){
            return AlgorithmsTyp.RSA;
        }else if (algoTyp.toUpperCase().equals(AlgorithmsTyp.SHIFT.toString())){
            return  AlgorithmsTyp.SHIFT;
        }else {
            return  null;
        }
    }



    public void createCryptoMethod(String methodType, AlgorithmsTyp algorithmsTyp) { // cryptoVar: decrypt or encrypt
        Object instance;

        try {
            // Stefan Pfad so erstellen wie IM LHC Detector damit nichtt gleich wie Loesung !!!!!!!!!!!!!
            // keine Methode gerAlgoPath
            // siehe Decetor LHC
            //URL[] urls = {new File(Configuration.instance.getAlgorithmPath(Configuration.instance.algorithmsTyp)).toURI().toURL()};
            URL[] urls = {new File(Configuration.instance.getAlgorithmPath(algorithmsTyp)).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptoCreator.class.getClassLoader());
            // Klasse namen umbebenen!!!!
           // Class aClass = Class.forName("CryptoEngine" + Configuration.instance.algorithmsTyp.toString(), true, urlClassLoader);
            Class aClass = Class.forName("CryptoEngine" + algorithmsTyp.toString(), true, urlClassLoader);
            instance = aClass.getMethod("getInstance").invoke(null);
            port = aClass.getDeclaredField("port").get(instance);
            // Name Varibale aenderen
            cryptoVar = port.getClass().getMethod(methodType, String.class,File.class);
        }
        catch (Exception e) {
            System.out.println("--- exception");
            System.out.println(e.getMessage());
        }
    }

    public void createCrackerMethod(AlgorithmsTyp algorithm) {
        Object instance;
        URL[] urls = null;
        try {
            urls = new URL[]{new File(Configuration.instance.getCrackerPath(algorithm)).toURI().toURL()};
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            URLClassLoader urlCL = new URLClassLoader(urls, CryptoCreator.class.getClassLoader());
            Class aClass = Class.forName(algorithm.toString() + "Cracker", true, urlCL);

            instance = aClass.getMethod("getInstance").invoke(null);
            port = aClass.getDeclaredField("port").get(instance);
            cryptoVar = port.getClass().getMethod(CryptoVar.DECRYPT.toString().toLowerCase(), String.class, File.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String cryption(String message, File key)
    {
        try
        {
            //log("Starting decryption");
            return (String) cryptoVar.invoke(port, message, key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //log("Error while decryption: " + e.getMessage());
        }
        return "";
    }
}
