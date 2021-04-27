
import java.util.ArrayList;
import java.util.List;
public class TST {

	private TSTNode root;

	// Specifies if we have reached the current node from the left, right
	// or center pointer from the parent node
	private enum NodeDirection {
		LEFT,
		CENTER,
		RIGHT
	}

	/**
	 * Constructs an empty ternary search tree (TST).
	 */
	public TST() {
		this.root = null;
	}

	/**
	 * Constructs and initializes a ternary search tree (TST) with the words specified.
	 * @param words list of words to initialize the tree with
	 */
	public TST(final List<String> words) {
		for (String word : words) {
			this.insert(word);
		}
	}

	/**
	 * Returns the root of the TST.
	 * @return the root of the TST
	 */
	public TSTNode getRoot() {
		return root;
	}

	/**
	 * Checks if the TST is empty or not.
	 * @return true if the TST is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return this.getRoot() == null;
	}

	/**
	 * Inserts a string into the TST.
	 * 
	 * <p>
	 * The {@code stringToInsert} cannot be {@code null} or empty.
	 * No insert will happen in this case and a null value would be returned.
	 * </p>
	 * @param stringToInsert the string to insert
	 * @return the root of the TST after the insert
	 */
	public TSTNode insert(final String stringToInsert) {

		root = insert(this.root, stringToInsert.toUpperCase().toCharArray(), 0);
		return getRoot();
	}

	private TSTNode insert(TSTNode node, final char[] word, final int index) {

		final char currentChar = word[index];

		if (node == null) {
			node = new TSTNode(currentChar);
		}

		if (currentChar < node.getCharacter()) {
			node.left = insert(node.getLeft(), word, index);
		} else if (currentChar > node.getCharacter()) {
			node.right = insert(node.getRight(), word, index);
		} else {

			if (index + 1 < word.length) {
				node.center = insert(node.getCenter(), word, index + 1);
			} else {
				node.setEndOfWord(true);
			}
		}

		return node;
	}

	/**
	 * Searches for a word in the TST.
	 * 
	 * <p>
	 * The {@code wordToSearch} would return {@code false} as we cannot
	 * insert {@code null} or empty strings into the TST.
	 * </p>
	 * @param wordToSearch the word to search for
	 * @return true if wordToSearch exists, false otherwise
	 */
	public boolean search(final String wordToSearch) {
		return search(getRoot(), wordToSearch.toUpperCase().toCharArray(), 0);
	}
	    
	private boolean search(final TSTNode node, final char[] word, final int index) {

		if (node == null) {
			return false;
		}

		final char currentChar = word[index];
		if (currentChar < node.getCharacter()) {
			return search(node.getLeft(), word, index);
		} else if (currentChar > node.getCharacter()) {
			return search(node.getRight(), word, index);
		} else {
			if (index == word.length - 1) {
				return node.isEndOfWord();
			}

			return search(node.getCenter(), word, index+1);
		}
	}
}