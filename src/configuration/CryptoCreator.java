package configuration;

import gui.GUI;
import instructionParser.parser.EncryptMessage;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class CryptoCreator {
    private Object port;
    private Method cryptoVar;
    public GUI gui;

    public Object getPort() {
        return port;
    }

    public Method getCryptoVar() {
        return cryptoVar;
    }


    public void createCryptoMethod(String methodType) { // cryptoVar: decrypt or encrypt
        Object instance;

        try {
            URL[] urls = {new File(Configuration.instance.getAlgorithmPath(Configuration.instance.algorithms)).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptoCreator.class.getClassLoader());
            Class aClass = Class.forName("CryptoEngine" + Configuration.instance.algorithms.toString(), true, urlClassLoader);

            instance = aClass.getMethod("getInstance").invoke(null);
            port = aClass.getDeclaredField("port").get(instance);

            cryptoVar = port.getClass().getMethod(methodType, String.class,File.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createCrackerMethod(Algorithms algorithm) {
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
