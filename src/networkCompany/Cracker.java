package networkCompany;


import configuration.Configuration;
import crypto.AlgorithmsTyp;
import crypto.CryptoCreator;

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
    private Method cryptoVar;

    public Future<String>  cracking (AlgorithmsTyp algoTyp, String message, RSAPublicKey pubKey) {
        return executor.submit(() -> {
            String crackedString = "Error: cracking failed.";
            try {
                switch (algoTyp) {
                    case RSA:
                        // Load component
                        createCrackerMethod(algoTyp);
                        // Decrypt message & generating File
                        //File publicKey = new File(Configuration.instance.keyfileDirectory + pubKey);
                        crackedString = (String) getCryptoVar().invoke(getPort(), message, pubKey); // publicKey wird benötigt
                        break;
                    case SHIFT:
                        // Load component
                        createCrackerMethod(algoTyp);
                        // Decrypt message
                        crackedString = (String) getCryptoVar().invoke(getPort(), message); // publicKey wird nicht benötigt
                        // Test Timeout -----
                        //Thread.sleep(31000);
                        break;
                }
            }
            catch (Exception e) { e.printStackTrace(); }
            return crackedString;

        });



    }


    private void createCrackerMethod(AlgorithmsTyp algorithm) {
        Object instance;

        try {
            URL[] urls= {new File(Configuration.instance.getCrackerPath(algorithm)).toURI().toURL()};
            URLClassLoader urlCL = new URLClassLoader(urls, CryptoCreator.class.getClassLoader());
            Class aClass = Class.forName(algorithm.toString()+"Cracker", true, urlCL);
            instance = aClass.getMethod("getInstance").invoke(null);
            port = aClass.getDeclaredField("port").get(instance);

            switch (algorithm) {
                case RSA:
                    //cryptoVar = port.getClass().getMethod("decrypt", String.class, File.class); // RSA benötigt message und public key
                    cryptoVar = port.getClass().getMethod("decrypt", String.class, RSAPublicKey.class);
                    break;
                case SHIFT:
                    cryptoVar = port.getClass().getMethod("decrypt", String.class);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getPort() {
        return port;
    }

    public Method getCryptoVar() {
        return cryptoVar;
    }
}
