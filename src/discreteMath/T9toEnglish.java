package discreteMath;

import java.util.ArrayList;
import java.util.Scanner;

import discreteMath.resourceLoaders.WordListGenerator;
import discreteMath.trie.Trie;
import discreteMath.trie.TrieNode;

/**
 * Translates a T9 text keypad input into a list of possible words typed.
 */
public class T9toEnglish
{
	/**
	 * The mappings of digits to T9 characters, where
	 * the digits are the first level indices of the array.
	 */
	private static final char[][] t9Mappings = {
			null,
			null,
			{'a', 'b', 'c'},
			{'d', 'e', 'f'},
			{'g', 'h', 'i'},
			{'j', 'k', 'l'},
			{'m', 'n', 'o'},
			{'p', 'q', 'r', 's'},
			{'t', 'u', 'v'},
			{'w', 'x', 'y', 'z'}
	};	
	
	public static void main(String[] args)
	{
		String wordListFilePath = "resources/enable1.txt";
		Trie englishLanguageTrie = initializeTrie(wordListFilePath);
		ArrayList<String> digitStrings = getT9InputFromUser();
		ArrayList<ArrayList<String>> resultsList =
				new ArrayList<ArrayList<String>>();
		
		for(String digitString : digitStrings)
		{ 
			ArrayList<String> result = 
					convertT9(englishLanguageTrie, digitString);
			resultsList.add(result);
		}
		
		for(int result = 0; result < resultsList.size(); result++)
		{
			System.out.println("Possible words formed from " 
					+ digitStrings.get(result) + ":");
			if(resultsList.get(result).size() == 0)
			{
				System.out.println("\t" + "No result.");
			}
			for(String word : resultsList.get(result))
			{
				System.out.println("\t" + word);
			}
		}
	}
	
	/**
	 * Returns the list of possible words that a user could have entered,
	 * calculated from a string of digits representing the T9 keys that 
	 * were pressed.
	 * @param wordsTrie A Trie of the words that are to be considered valid.
	 * @param numberSequence The sequence of numbers representing key presses.
	 * @return An ArrayList of Strings which are valid possible words.
	 */
	private static ArrayList<String> convertT9(
			Trie wordsTrie, 
			String numberSequence)
	{
		ArrayList<String> results = new ArrayList<String>();
		int startIndex = 0;
		String startPrefix = "";
		getPossibleWords(numberSequence, 
						 startPrefix, 
						 startIndex, 
						 wordsTrie.getRoot(), 
						 results);
		return results;
	}
	
	/**
	 * Returns an ArrayList of strings of digits entered from the user.
	 * Guaranteed to contain only digits, else program will crash.
	 * When user enters input, multiple values can be seperated with
	 * spacebar.
	 */
	private static ArrayList<String> getT9InputFromUser()
	{
		ArrayList<String> digitStrings = new ArrayList<String>();
		Scanner inputScanner = new Scanner(System.in);
		String digitsOnlyRegex = "[0-9]+";
		String currentInput;
		
		System.out.println("Enter T9 sequence of digits: ");
		System.out.println("(For multiple sequences, "
				+ "seperate with new line or spacebar.)");
		
		System.out.println("Enter -1 after last input.");
		Benjamin:
		while(true) // Forever, until break statement.
		{
			currentInput = inputScanner.next();
			if(currentInput.equals("-1"))
			{
				break Benjamin;
			}
			digitStrings.add(currentInput);
			if(!currentInput.matches(digitsOnlyRegex))
			{
				System.err.println("Error: Input can contain only digits 0-9!");
				System.err.println("Terminating!");
				System.exit(-1);
			}
		}
		inputScanner.close();
		
		return digitStrings;
	}
	
	/**
	 * Creates and returns a Trie from a provided list of words,
	 * accessed via the given file path.
	 */
	private static Trie initializeTrie(String wordListFilePath)
	{
		WordListGenerator wordListGenerator = 
				new WordListGenerator(wordListFilePath);
		ArrayList<String> wordList = wordListGenerator.getWordList();
		Trie englishLanguageTrie = new Trie(wordList);
		return englishLanguageTrie;
	}
	
	/**
	 * Determines the possible words that could have been entered from a T9
	 * key sequence. Returns result by modifying the results array passed in.
	 * 
	 * The Big O runtime for this method is hard to calculate, but in
	 * practice it runs pretty fast, since the Trie is a very efficient
	 * data structure for this kind of work. More optimizations are
	 * most likely possible, but this is sufficient for our needs.
	 * 
	 * Theoretically, we could maybe even just compute a T9 to english 
	 * dictionary and lookup words against it, but that wouldn't be 
	 * in the spirit of the problem.
	 * 
	 * @param numberSequence The key sequence entered on the T9 keypad. A number.
	 * @param prefix The possible word prefix. This is used in recursive calls.
	 * Typically, call for the first time with an empty string.
	 * @param index The index of the digit in the number sequence to check.
	 * Used mainly in recursive calls to track the level of recursion.
	 * 0 should be provided for the first call, typically.
	 * @param trieNode A TrieNode object, typically the root of the Trie
	 * for the first call, and the selected child nodes for subsequent
	 * recursive calls.
	 * @param results An ArrayList of String results. The possible words will
	 * be stored in this list after all recursive calls are complete.
	 */
	private static void getPossibleWords(
			String numberSequence,
			String prefix,
			int index, 
			TrieNode trieNode, 
			ArrayList<String> results)
	{
		// If we have a complete word...
		if(index == numberSequence.length())
		{
			if(trieNode.isTerminal())
			{
				results.add(prefix);
			}
			return;
		}
		else
		{
			// Get the possible T9 characters that match the current digit.
			char digit = numberSequence.charAt(index);
			char[] possibleChars = getT9Chars(digit);
			
			// Check remaining possiblities.
			if(possibleChars != null)
			{
				for(char character : possibleChars)
				{
					TrieNode child = trieNode.getChild(character);
					if (child != null)
					{
						// Recurse
						getPossibleWords(
								numberSequence,
								prefix + character,
								index + 1,
								child,
								results
								);
					}
				}
			}
		}
	}
	
	/**
	 * Returns a list of characters that can map to the provided.
	 */
	private static char[] getT9Chars(char digit)
	{
		if (!Character.isDigit(digit))
		{
			return null;
		}
		else
		{
			int number = Character.getNumericValue(digit) 
					   - Character.getNumericValue('0');
			return t9Mappings[number];
		}
	}
}
