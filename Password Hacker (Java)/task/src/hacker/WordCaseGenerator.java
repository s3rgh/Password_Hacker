package hacker;

import java.util.ArrayList;
import java.util.List;

public class WordCaseGenerator {

    public static List<String> generateCases(String word) {
        List<String> results = new ArrayList<>();
        generateCasesHelper(word.toCharArray(), 0, "", results);
        return results.stream().distinct().toList();
    }

    private static void generateCasesHelper(char[] wordArray, int index, String current, List<String> results) {
        // Base case: if we are at the end of the word, add to results
        if (index == wordArray.length) {
            results.add(current);
            return;
        }

        // Get the current character
        char currentChar = wordArray[index];

        // Generate cases for the current character (lowercase and uppercase)
        generateCasesHelper(wordArray, index + 1, current + Character.toLowerCase(currentChar), results);
        generateCasesHelper(wordArray, index + 1, current + Character.toUpperCase(currentChar), results);
    }
}

