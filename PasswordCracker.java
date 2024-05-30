import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class PasswordCracker {
    public static String hashType;
    public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter a password that has been hashed. Recommended: Use a common password.");
        String hashedPassword = sc.nextLine();

        System.out.println("Please enter the type of hashing used for this password. Supported: MD5, SHA-1, SHA-256, SHA-384, SHA-512, MD2");
        hashType = sc.nextLine();

        System.out.println("Please enter a .txt file path of commonly used passwords. Recommended: Use include file.");
        String filePath = sc.nextLine();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String password;

            while((password = br.readLine()) != null){
                String hashedAttempt = hashPassword(password);
                if(hashedAttempt.equals(hashedPassword)){
                    System.out.println("Password has been cracked. The password is: " + password);
                    return;
                }
            }
        }

        System.out.println("Password was not found in the list of included passwords.");
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(hashType);
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hash){
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
