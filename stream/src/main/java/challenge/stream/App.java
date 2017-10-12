package challenge.stream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The stream reader class
 * <p>
 * This class implements a reader for an array of chars.
 * The stream can only be read once.
 */
public class App {

    /**
     * The list of vowels
     */
    private static List<String> vowels = Arrays.asList("a", "e", "i", "o", "u");

    /**
     * This method finds the first vowel, after a consonant that antecedent a vowel, that not repeated it self in the stream
     *
     * @param stream the reader
     */
    public static char firstChar(IStream stream) {
        boolean vowel = false;
        boolean consonant = false;
        char found = Character.MIN_VALUE;
        Map<Character, Integer> vowelsFound = new HashMap<>();
        Map<Character, Integer> eligibleVowels = new LinkedHashMap<>();

        while (stream.hasNext()) {
            String c = String.valueOf(stream.getNext());

            if(vowels.contains(c.toLowerCase())) {
                vowelsFound.merge(c.toLowerCase().charAt(0), 1, Integer::sum);
                if(vowel && consonant) {
                    eligibleVowels.merge(c.charAt(0), 1, Integer::sum);
                }
                vowel = true;
            } else {
                if(consonant) {
                    vowel = false;
                    consonant = false;
                } else if(vowel) {
                    consonant = true;
                }
            }

        }

        for(Map.Entry<Character, Integer> v : eligibleVowels.entrySet()) {
            if(vowelsFound.get(Character.toLowerCase(v.getKey())) == 1) {
                found = v.getKey();
                break;
            }
        }

        return found;
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Argument expected :(");
        } else {
            IStream stream = new Stream(args[0]);
            char c = firstChar(stream);

            if (c != Character.MIN_VALUE) {
                System.out.println("First char founded: " + c);
            } else {
                System.out.println("No matches were found :(");
            }
        }
    }

}
