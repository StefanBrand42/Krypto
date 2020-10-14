package networkCompany;


import configuration.Configuration;
import crypto.AlgorithmsTyp;
import crypto.CryptoCreator;
import instructionParser.parser.DecryptMessage;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Cracker {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private Object port;

    public Future<String>  cracking (AlgorithmsTyp algoTyp, String message, RSAPublicKey pubKey) {
        return executor.submit(() -> {
            String crackedString = "Error: cracking failed.";
            Method crackMethod = null;
            RSAPublicKey shiftKey = null;
            try {
                switch (algoTyp) {
                    case RSA:
                        // Load component
                        crackMethod = createCrackerMethod("decrypt", algoTyp);
                        //createCrackerMethod(algoTyp);
                        // Decrypt message
                        //crackedString = (String) crackMethod.invoke(getPort(), message, pubKey); // publicKey wird benötigt
                        crackedString = crack(message, pubKey, crackMethod);
                        break;
                    case SHIFT:
                        // Load component
                        //createCrackerMethod(algoTyp);
                        crackMethod = createCrackerMethod("decrypt", algoTyp);
                        // Decrypt message
                        //crackedString = (String) crackMethod.invoke(getPort(), message); // publicKey wird nicht benötigt
                        crackedString = crack(message, shiftKey, crackMethod);
                        break;
                }
            }
            catch (Exception e) { e.printStackTrace(); }
            return crackedString;

        });
    }


    public Method createCrackerMethod(String methodType, AlgorithmsTyp algorithm) {
        Object instance;

        try {
            URL[] urls= {new File(Configuration.instance.getCrackerPath(algorithm)).toURI().toURL()};
            URLClassLoader urlCL = new URLClassLoader(urls, CryptoCreator.class.getClassLoader());
            Class classComponent = Class.forName(algorithm.toString()+"Cracker", true, urlCL);
            instance = classComponent.getMethod("getInstance").invoke(null);
            port = classComponent.getDeclaredField("port").get(instance);

            switch (algorithm) {
                case RSA:
                    //cryptoVar = port.getClass().getMethod("decrypt", String.class, RSAPublicKey.class);
                    return port.getClass().getMethod(methodType, String.class, RSAPublicKey.class);  // RSA benötigt message und public key
                case SHIFT:
                    //cryptoVar = port.getClass().getMethod("decrypt", String.class);
                    return port.getClass().getMethod(methodType, String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("--- exception");
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    private String crack(String message, RSAPublicKey publicKey, Method crackMethod)
    {
        try
        {
            //log("Starting decryption");
            if (publicKey != null) {
                return (String) crackMethod.invoke(port, message, publicKey);
            }
            return (String) crackMethod.invoke(port, message);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //log("Error while decryption: " + e.getMessage());
        }
        return null;
    }
}
