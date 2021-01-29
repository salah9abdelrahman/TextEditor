package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    // The list of words with their next words
    private List<ListNode> wordList;

    // The starting "word"
    private String starter;

    // The random number generator
    private Random rnGenerator;

    public MarkovTextGeneratorLoL(Random generator) {
        wordList = new LinkedList<ListNode>();
        starter = "";
        rnGenerator = generator;
    }

    private boolean isWordExists(String w) {
        for (ListNode listNode : wordList) {
            if (listNode.getWord().equals(w)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Train the generator by adding the sourceText
     */
    @Override
    public void train(String sourceText) {
        String[] listOfWords = sourceText.split(" ");
        starter = listOfWords[0];
        String prevWord = starter;
        for (int i = 1; i < listOfWords.length; i++) {
            String w = listOfWords[i];
            boolean isExist = false;
            for (ListNode listNode : wordList) {
                if (listNode.getWord().equals(prevWord)) {
                    listNode.addNextWord(w);
                    isExist = true;
                }
            }
            if (!isExist) {
                ListNode newNode = new ListNode(prevWord);
                newNode.addNextWord(w);
                wordList.add(newNode);
            }

            prevWord = w;
        }

        String lastWord = listOfWords[listOfWords.length - 1];
        if (!isWordExists(lastWord)) {
            ListNode newNode = new ListNode(lastWord);
            newNode.addNextWord(starter);
            wordList.add(newNode);

        }


    }

    /**
     * Generate the number of words requested.
     */
    @Override
    public String generateText(int numWords) {
        //hi there hi leo
        // hi
        String currWord = starter;
        StringBuilder output = new StringBuilder();
        int currNumOfWords = 1;
        while (currNumOfWords < numWords) {
            output.append(currWord).append(" ");
            currNumOfWords++;
            for (ListNode listNode : wordList
            ) {
                if (listNode.getWord().equals(currWord)) {
                    String w = listNode.getRandomNextWord(rnGenerator);
                    output.append(w).append(" ");
                    currWord = w;
                    currNumOfWords++;
                }
            }
        }

        return output.toString();
    }


    // Can be helpful for debugging
    @Override
    public String toString() {
        String toReturn = "";
        for (ListNode n : wordList) {
            toReturn += n.toString();
        }
        return toReturn;
    }

    /**
     * Retrain the generator from scratch on the source text
     */
    @Override
    public void retrain(String sourceText) {
        this.wordList = new LinkedList<>();
        this.train(sourceText);
    }

    // TODO: Add any private helper methods you need here.

    public static void main(String[] args) {
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
//        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        String textString = "hi there hi leo";
        gen.train(textString);
//        String s = "I I I I I love cats. I I love love love cats. I I I I I I I I I I I I I I I I hate dogs. I I hate dogs. I I am a text generator. I I I I I I I I love cats. I I I I I I I I love cats. I I like books. I I I I I I I I I I love cats. I I love love love cats. I I I I I I I I I I I I I I I I love socks. I I I I I I I I I I I I I I I I I I love cats. I I am a text generator. I I I I I I I I I I I I hate dogs. I I I I I I I I I I hate dogs. I I I I I I love cats. I I I I I I love love love cats. I I I I I \n";
//        System.out.println(gen);
        System.out.println(gen.generateText(20));

    }

    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     *
     * @param args
     */
    public static void main2(String[] args) {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
        String textString2 = "You say yes, I say no, " +
                "You say stop, and I say go, go, go, " +
                "Oh no. You say goodbye and I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "I say high, you say low, " +
                "You say why, and I say I don't know. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "Why, why, why, why, why, why, " +
                "Do you say goodbye. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "You say yes, I say no, " +
                "You say stop and I say go, go, go. " +
                "Oh, oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }

}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word) {
        this.word = word;
        nextWords = new LinkedList<String>();
    }

    public String getWord() {
        return word;
    }

    public void addNextWord(String nextWord) {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator) {

        // The random number generator should be passed from
        // the MarkovTextGeneratorLoL class
        return nextWords.get(generator.nextInt(nextWords.size()));
    }

    public String toString() {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}


