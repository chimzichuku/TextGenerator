package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * The random generator generates words such that each word that is connected to the seed word has the possibility
 * of being added to the final sequence of words, but the algorithm gives a higher weight to the words with a higher
 * probability (words that show up more in the document have a higher probability of getting chosen)
 * @author Charles Adair and Chimzi Chuku
 * @version April 21, 202
 */
public class RandomGenerator extends TextGenerator{
    
    private HashMap<String, HashMap<Integer, String>> randomDictionary = new HashMap<>();
    
    
    /**
     * Helper method to help train the algorithm to determine the sequence of words to be output.
     * Works by mapping the words that come after it into a hashmap indexed from 0.
     * @param file the file to be read
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
        while (scanner.hasNext()) {
            value = scanner.next().toLowerCase();
            HashMap<Integer, String> probabilities = randomDictionary.get(key);
            if (probabilities == null) { // This seed has not appeared yet.
                probabilities = new HashMap<>();
                randomDictionary.put(key, probabilities);
            }
            probabilities.put(probabilities.size(), value);
            key = value;
        }
        scanner.close();
    }

    /**
     * Gets the correct sequence of words from the dictionary based on random chance, with words that have a higher
     * probability being more likely to be chosen. Works by generating a random number between 0 and the size of the
     * hashmap keyed at the current word.
     * @param seed - word used as a starting point
     * @param howMany - indicates how many words to generate
     * @throws IllegalArgumentException if the seed is null
     * @return the array containing the correct sequence of words
     */
    @Override
    protected String[] getWords(String seed, int howMany) {
        String[] output = new String[howMany];
        Random rand = new Random();
        int count = 1;
        String currentWord = seed.toLowerCase();
        output[0] = currentWord;
        while(count < howMany){
            if (randomDictionary.get(currentWord) == null) {
                output[count] = seed;
            }
            else {
                int size = randomDictionary.get(currentWord).size();
                output[count] = randomDictionary.get(currentWord).get(rand.nextInt(0, size));
            }
            
            currentWord = output[count++];
        }
        return output;
    }
}
