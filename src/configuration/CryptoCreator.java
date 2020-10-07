package configuration;

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

    public Object getPort() {
        return port;
    }

    public Method getCryptoVar() {
        return cryptoVar;
    }

    public void createCryptoMethod(Algorithms algorithm, CryptoVar cryptoVar) {
        Object instance;
        URL[] urls = null;
        try {
            urls = new URL[]{new File(Configuration.instance.getCrackerPath(algorithm)).toURI().toURL()};
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            urls = new URL[]{new File(Configuration.instance.getAlgorithmPath(algorithm)).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptoCreator.class.getClassLoader());
            Class clazz = Class.forName(algorithm.toString(), true, urlClassLoader);

            instance = clazz.getMethod("getInstance").invoke(null);
            port = clazz.getDeclaredField("port").get(instance);
            this.cryptoVar = port.getClass().getMethod(cryptoVar.toString().toLowerCase(), String.class, File.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createCrackerMethod(Algorithms algorithm) {
        Object instance;
        URL[] urls = null;
        try {
            urls = new URL[]{new File(Configuration.instance.getCrackerPath(algorithm)).toURI().toURL()};
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptoCreator.class.getClassLoader());
            Class clazz = Class.forName(algorithm.toString() + "Cracker", true, urlClassLoader);

            instance = clazz.getMethod("getInstance").invoke(null);
            port = clazz.getDeclaredField("port").get(instance);
            cryptoVar = port.getClass().getMethod(CryptoVar.DECRYPT.toString().toLowerCase(), String.class, File.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Map<String, String> params = new HashMap();
    public String getParam(String name) {
        if (this.params.containsKey(name))
            return this.params.get(name);
        else
            return null;
    }
}
