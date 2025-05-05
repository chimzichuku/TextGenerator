package comprehensive;

import java.io.File;

/**
 * This class represents an abstract text editor. The command line interface takes in a file, a seed word, how many
 * words to generate, and the type of word genration to uss (probable, deterministic or random)
 * Deterministic generation generates based on the word with the most frequency or lowest lexicographical value in case
 *  of a tie
 * Random generation works similar to a lottery for the next word--if a word occurs multiple times after a seed word in
 *  an input text, it is more likely to be generated.
 * Probable generation outputs the most probable words to come after a seed words.
 */
public abstract class TextGenerator {

    protected String seed;
    protected int howMany;
    private static TextGenerator wordGenerator;

    /**
     * method used to run the program files.
     * @param args command line arguments that are used to choose which text algorithm to use, how many words to
     *             generate, which file to choose from, and which word to start with (seed word).
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            System.out.println("Quitting the program...Goodbye");
        }
        if (args.length < 4) {
            System.out.println("Not enough arguments. Try again");
        }
        switch(args[3]) {
            case "probable":
                wordGenerator = new ProbabilisticGenerator();
                break;
            case "random":
                wordGenerator = new RandomGenerator();
                break;
            case "deterministic":
                wordGenerator = new DeterministicGenerator();
                break;
            default:
                System.out.println("Invalid argument: " + args[3] + ". \nMust be one of three options: " +
                        "\'random\', \'deterministic\', or \'probable.\'");
                break;
        }
        wordGenerator.generate(Integer.parseInt(args[2]),new File(args[0]), args[1]);
    }

    /**
     * Generates a sentence based on the array of words returned from the getWords method
     * @param howMany - how many words to generate
     * @param file - the files being used to train the algorithm
     * @param seed - the starting word
     */
    public void generate(int howMany, File file, String seed) {
        this.seed = seed;
        this.howMany = howMany;
        train(file);
        StringBuilder output = new StringBuilder();
        for (String word : getWords(seed, howMany)) {
            output.append(word);
            output.append(" ");
        }
        output.deleteCharAt(output.length() - 1); // Removes the additional whitespace placed at the end
        System.out.println(output);
    }

    /**
     * Abstract helper method to help train the algorithm to determine the sequence of words to be output.
     * @param file the file to be read
     */
    protected abstract void train(File file);

    /**
     * Abstract method that gets the correct sequence of words from the dictionary based on the highest k probable words
     * connected to the seed
     * @param seed - word used as a starting point
     * @param howMany - indicates how many words to generate
     * @return the array containing the correct sequence of words
     */
    protected abstract String[] getWords(String seed, int howMany);
}
