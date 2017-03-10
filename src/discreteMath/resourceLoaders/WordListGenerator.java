package discreteMath.resourceLoaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Loads in a list of valid english words from a textfile,
 * which, if not specified, defaults to enable1.txt.
 */
public class WordListGenerator
{	
	private ArrayList<String> wordList;
	
	/**
	 * Constructs a WordsListGnerator.
	 * Initializes the wordsList by reading in a list of words
	 * from a .txt file passed as a string. Each word in the file
	 * should be on its own line.
	 */
	public WordListGenerator(String sourceFilePath)
	{
		wordList = new ArrayList<String>();
		String word;
		try
		{
			FileReader fileReader = new FileReader(sourceFilePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((word = bufferedReader.readLine()) != null)
			{
				wordList.add(word);
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File not found! Terminating.");
			System.exit(-1);
		}
		catch(IOException e)
		{
			System.err.println("Unknown IOException occured in WordListGenerator!");
			System.err.println("Terminating.");
			System.exit(-1);
		}
	}
	
	/**
	 * Returns the list of words.
	 */
	public ArrayList<String> getWordList()
	{
		return wordList;
	}
}
