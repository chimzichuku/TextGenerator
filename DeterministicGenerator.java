package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The deterministic generator generates words such that for any given word, there is only one possible output based
 * upon the most probable word in an input text. If a frequency is the same, the lexicographical order is used as the
 * tie-breaker
 * @author Chimzi Chuku and Charles Adair
 * @version April 21, 2025
 */
public class DeterministicGenerator extends TextGenerator {

    private HashMap<String, String> deterministicDictionary = new HashMap<>();

    public DeterministicGenerator() {
        super();
    }

    /**
     * Helper method to help train the algorithm to determine the sequence of words to be output
     * @param file the file to be read
     * @throws RuntimeException if the file can not be found
     */
    @Override
    protected void train(File file) {
        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
        }catch(FileNotFoundException e){
            throw new RuntimeException("File " + e + " can not be found");
        }
        scanner.useDelimiter("[^a-zA-Z'_0-9]+");
        String key = scanner.next().toLowerCase();
        String value;
        HashMap<String, HashMap<String, Integer>> allProbabilities = new HashMap<>();
        while (scanner.hasNext()) {
            value = scanner.next().toLowerCase();
            if (allProbabilities.get(key) == null) { // This seed has not appeared yet.
                HashMap<String, Integer> probabilities = new HashMap<>();
                probabilities.put(value, 1);
                probabilities.put("_MAX", 1);
                allProbabilities.put(key, probabilities);
                deterministicDictionary.put(key, value);
            }
            else { // Seed already exists in the hashmap
                HashMap<String, Integer> probabilities = allProbabilities.get(key);
                int frequency;
                if (probabilities.get(value) == null) { // Child does not exist yet
                    probabilities.put(value, 1);
                    frequency = 1;
                }else{
                    frequency = probabilities.get(value) + 1;
                    probabilities.put(value, frequency);
                }
                if (probabilities.get("_MAX") < frequency) {
                    probabilities.put("_MAX", frequency);
                    deterministicDictionary.put(key, value); // the value now has a larger frequency than the previous word,
                                                // so we update the deterministicDictionary
                }
                else if (probabilities.get(value).equals(probabilities.get("_MAX"))) {
                    if (deterministicDictionary.get(key).compareTo(value) >  0) {
                        deterministicDictionary.put(key, value);
                    }
                }
            }
            key = value;
        }
        scanner.close();
    }

    /**
     * Gets the correct sequence of words from the dictionary based on the highest probable word
     * connected to the next seed word
     * @param seed - word used as a starting point
     * @param howMany - indicates how many words to generate
     * @throws IllegalArgumentException if the seed is null
     * @return the array containing the correct sequence of words
     */
    @Override
    protected String[] getWords(String seed, int howMany) {
        if(seed == null){
            throw new IllegalArgumentException("Seed must not be null");
        }

        String[] output = new String[howMany];
        String nextSeed = seed.toLowerCase();
        output[0] = nextSeed;
        for(int i = 1; i < howMany; i++){
            if(deterministicDictionary.get(nextSeed) == null) {
                nextSeed = seed.toLowerCase();
            }
            else{
                nextSeed = deterministicDictionary.get(nextSeed);
            }
            output[i] = nextSeed;
        }
        deterministicDictionary.clear();
        return output;
    }
}
