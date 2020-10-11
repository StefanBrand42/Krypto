package crypto;

public interface ICryptoCreator {
    String encryptMessage(String message, String algo, String key);
    String decryptMessage(String message, String algo, String key);

    AlgorithmsTyp getAlgoTypFromName(String algoTyp);
}
