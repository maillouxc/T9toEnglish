package discreteMath.trie;

import java.util.ArrayList;

/**
 * Simple implementation of the Trie data structure.
 */
public class Trie 
{
	private TrieNode root;
	
	/**
	 * Constructs a trie from the given list of strings.
	 */
	public Trie(ArrayList<String> list)
	{
		root = new TrieNode();
		for(String word : list)
		{
			root.addWord(word);
		}
	}
	
	/**
	 * Determines whether the trie contains a given prefix.
	 * @param prefix The prefix to search for.
	 * @param exact A boolean flag that determines whether the prefix
	 * must end in a terminal node (a.k.a. a complete word).
	 * @return True if the Trie contains the prefix.
	 */
	public boolean contains(String prefix, boolean exact)
	{
		TrieNode lastNode = root;
		for(int i = 0; i < prefix.length(); i++)
		{
			lastNode = lastNode.getChild(prefix.charAt(i));
			if(lastNode == null)
			{
				return false;
			}
		}
		return !exact || lastNode.isTerminal();
	}
	
	/**
	 * Overload of the contains method, where the caller doesn't need
	 * to specify whether an exact match is required. Defaults to false.
	 */
	public boolean contains(String prefix)
	{
		return contains(prefix, false);
	}
	
	/**
	 * Returns the root node of the Trie.
	 */
	public TrieNode getRoot()
	{
		return root;
	}
}
