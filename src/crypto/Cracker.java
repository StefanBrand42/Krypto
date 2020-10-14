package crypto;


import configuration.Configuration;

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
                        // Crack message
                        crackedString = crack(message, pubKey, crackMethod); // publicKey wird benötigt
                        break;
                    case SHIFT:
                        // Load component
                        crackMethod = createCrackerMethod("decrypt", algoTyp);
                        // Crack message
                        crackedString = crack(message, shiftKey, crackMethod); // publicKey wird nicht benötigt
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
                    return port.getClass().getMethod(methodType, String.class, RSAPublicKey.class);  // RSA benötigt message und public key
                case SHIFT:
                    return port.getClass().getMethod(methodType, String.class); // SHIFT benötigt nur message
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
            if (publicKey != null) {
                return (String) crackMethod.invoke(port, message, publicKey); // RSA
            }
            return (String) crackMethod.invoke(port, message); // SHIFT
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
