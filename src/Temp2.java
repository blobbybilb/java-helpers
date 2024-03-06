// random CSA assignment, ignore

import java.security.SecureRandom;

public class Temp2 {
    public static void main(String[] args) {
        String randomString = gen();
        System.out.println("Random String: " + randomString);
    }

    public static String gen() {
        SecureRandom random = new SecureRandom();

        String symbols = "!@#$%^&*()_-+=<>?/[]{},.";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String sb = String.valueOf(
                getchar(symbols, random)) +
                getchar(symbols, random) +
                getchar(lowercase, random) +
                getchar(lowercase, random) +
                getchar(uppercase, random) +
                getchar(uppercase, random);

        char[] charArray = sb.toCharArray();
        for (int i = charArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = charArray[index];
            charArray[index] = charArray[i];
            charArray[i] = temp;
        }

        return new String(charArray);
    }

    private static char getchar(String characterSet, SecureRandom random) {
        int randomIndex = random.nextInt(characterSet.length());
        return characterSet.charAt(randomIndex);
    }
}
