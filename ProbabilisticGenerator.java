package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The probabilistic generator generates the most probable words that come after a word in an input text.
 * @author Chimzi Chuku and Charles Adair
 * @version April 21, 2025
 */
public class ProbabilisticGenerator extends TextGenerator{

    private HashMap<String, Integer> probabilisticDictionary = new HashMap<>();

    /**
     * An object from this class contains a string and a frequency. This is used for sorting a list of words based first
     * on their frequency and then their lexicographical order.
     */
    private class Tuple implements Comparable<Tuple> {
        String str;
        int frequency;

        /**
         * Creates a new tuple
         * @param key string value
         * @param value integer frequency
         */
        private Tuple(String key, Integer value) {
            this.str = key;
            this.frequency = value;
        }

        /**
         * Gets the word stored in this Tuple
         * @return the stored string variable
         */
        public String getWord(){return str;}

        /**
         * compares this tuple with another tuple(rhs) by frequency and in natural ordering (least to greatest).
         * - If this tuple's frequency is greater than the other, -1 will be returned
         * - if this tuple has a frequency less than the other tuple, 1 will be returned
         * - if both tuples have equal frequencies, the String class' lexicographical ordering will be used
         * @param rhs the object to be compared.
         * @return -1, 1, or the value from the String class' lexicographical comparison
         */
        @Override
        public int compareTo(Tuple rhs) {
            if (this.frequency > rhs.frequency) {
                return -1;
            }
            if (this.frequency < rhs.frequency) {
                return 1;
            }
            return this.str.compareTo(rhs.str);
        }
    }


    /**
     * Creates a new ProbabilisticGenerator
     */
    public ProbabilisticGenerator(){super();}

    /**
     * Helper method to help train the algorithm to determine the sequence of words to be output.
     * This method works by scanning the document for every occurrence of the input word and maps the words that come
     * after it to the number of times it occurs after the input word.
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
        while(scanner.hasNext()){
            value = scanner.next().toLowerCase();
            if(seed.equals(key)){
                if (probabilisticDictionary.get(value) == null) { //creating the key if it does not exist
                    probabilisticDictionary.put(value, 1);
                }
                else { //if the key and value exist, update the value's frequency
                    probabilisticDictionary.put(value, probabilisticDictionary.get(value) + 1);
                }
            }
            key = value;
        }
        scanner.close();
    }

    /**
     * Gets the correct sequence of words from the dictionary based on the highest k probable words connected
     * to the seed
     * @param seed - word used as a starting point
     * @param howMany - indicates how many words to generate
     * @throws IllegalArgumentException if the seed is null
     * @return the array containing the correct sequence of words
     */
    @Override
    protected String[] getWords(String seed, int howMany) {
        Tuple[] tupleArr = new Tuple[probabilisticDictionary.size()];
        String[] output;
        int index = 0;
        if (howMany >= probabilisticDictionary.size()) {
            output = new String[probabilisticDictionary.size()];
        }
        else {
            output = new String[howMany];
        }
        for(Map.Entry<String, Integer> entry : probabilisticDictionary.entrySet()){
            Tuple tuple = new Tuple(entry.getKey(), entry.getValue());
            tupleArr[index++] = tuple;
        }
        Arrays.sort(tupleArr);
        for(int i = 0; i < output.length; i++){
            output[i] = tupleArr[i].getWord();
        }
        return output;
    }
}
