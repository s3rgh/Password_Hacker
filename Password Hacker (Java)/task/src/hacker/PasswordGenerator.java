package hacker;

import java.util.ArrayList;
import java.util.List;

public class PasswordGenerator {
    public static List<String> generatePasswords(int count) {
        List<String> passwords = new ArrayList<>();
        int length = 1;  // start from 1
        while (passwords.size() < count) {
            generatePasswordsOfLength(length, passwords);
            length++;
        }
        return passwords;
    }

    private static void generatePasswordsOfLength(int length, List<String> passwords) {
        int maxNumber = (int) Math.pow(26 + 10, length); // variants
        for (int i = 0; i < maxNumber; i++) {
            StringBuilder password = new StringBuilder();
            int number = i;

            for (int j = 0; j < length; j++) {
                int remainder = number % (26 + 10);
                number /= (26 + 10);

                if (remainder < 26) {
                    password.append((char) ('a' + remainder));
                } else {
                    password.append(remainder - 26); // 0-9 for numbers
                }
            }
            passwords.add(password.reverse().toString());
        }
    }
}
