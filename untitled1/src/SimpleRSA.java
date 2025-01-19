import java.math.BigInteger;
import java.util.Scanner;

public class SimpleRSA {

    // Генерация пары ключей RSA на основе введенных чисел p и q
    public static BigInteger[] generateRSAKeyPair(BigInteger p, BigInteger q) {
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(3);

        // Проверка взаимной простоты e и phi
        while (!e.gcd(phi).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.TWO);
        }

        BigInteger d = e.modInverse(phi);

        return new BigInteger[]{e, d, n};
    }

    // Шифрование сообщения с использованием RSA
    public static BigInteger encryptRSA(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    // Расшифрование сообщения с использованием RSA
    public static BigInteger decryptRSA(BigInteger encryptedMessage, BigInteger d, BigInteger n) {
        return encryptedMessage.modPow(d, n);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод простых чисел p и q
        System.out.print("Введите простое число p: ");
        BigInteger p = new BigInteger(scanner.nextLine());

        System.out.print("Введите простое число q: ");
        BigInteger q = new BigInteger(scanner.nextLine());

        // Генерация ключей
        BigInteger[] keyPair = generateRSAKeyPair(p, q);
        BigInteger e = keyPair[0];
        BigInteger d = keyPair[1];
        BigInteger n = keyPair[2];

        // Вывод ключей в консоль
        System.out.println("Public Key: (" + e + ", " + n + ")");
        System.out.println("Private Key: (" + d + ", " + n + ")");

        // Сообщение для шифрования
        System.out.print("Введите сообщение (число) для шифрования: ");
        BigInteger message = new BigInteger(scanner.nextLine());

        // Шифрование и расшифрование с использованием RSA
        BigInteger encryptedMessage = encryptRSA(message, e, n);
        BigInteger decryptedMessage = decryptRSA(encryptedMessage, d, n);

        // Вывод результатов шифрования и расшифрования
        System.out.println("Original Message: " + message);
        System.out.println("Encrypted RSA Message: " + encryptedMessage);
        System.out.println("Decrypted RSA Message: " + decryptedMessage);

        scanner.close();
    }
}
