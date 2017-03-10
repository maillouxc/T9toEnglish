package discreteMath.trie;

import java.util.HashMap;

/**
 * Simple Implementation of a node in the Trie data Structure.
 */
public class TrieNode
{
	private HashMap<Character, TrieNode> children;
	private boolean isTerminal = false;
	private char character;
	
	/**
	 * Constructs an empty TrieNode. 
	 * Should only be used to create the root.
	 */
	public TrieNode()
	{
		this.children = new HashMap<Character, TrieNode>();
	}
	
	/**
	 * Constructs a TrieNode with the given character value.
	 */
	public TrieNode(char character)
	{
		this();
		this.character = character;
	}
	
	/**
	 * Returns the chracter value for this node.
	 */
	public char getChar()
	{
		return character;
	}
	
	/**
	 * Adds a word to the Trie.
	 */
	public void addWord(String word)
	{
		if(word == null || word.isEmpty())
		{
			return;
		}
		
		char firstChar = word.charAt(0);
		
		TrieNode child = getChild(firstChar);
		if(child == null)
		{
			child = new TrieNode(firstChar);
			children.put(firstChar, child);
		}
		
		if(word.length() > 1)
		{
			child.addWord(word.substring(1));
		}
		else
		{
			child.setIsTerminal(true);
		}
	}
	
	/**
	 * Returns the child node of this node with the given chracter value.
	 */
	public TrieNode getChild(char c)
	{
		return children.get(c);
	}
	
	/**
	 * Returns true if this node is a terminal node.
	 * A terminal node is a node which marks the end of a complete word.
	 */
	public boolean isTerminal()
	{
		return isTerminal;
	}
	
	/**
	 * Sets whether this node is marked as a terminal node.
	 * A terminal node is a node which marks the end of a complete word.
	 */
	public void setIsTerminal(boolean isTerminal)
	{
		this.isTerminal = isTerminal;
	}
}
