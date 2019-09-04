package common.structure;

/**
 * Basic interface for all tree data structures like
 * binary tree, binary search tree and etc.
 * @param <T> Type of objects that are stored inside tree structure.
 */
public abstract class Tree<T> {

	/** Reference to a root of tree or null if tree is empty. */
	private Node<T> root;

	/**
	 * Returns height of a tree, starting from root node and
	 * ending with deepest leaf node.
	 * @return Height of tree.
	 */
	public abstract int getHeight();

	/**
	 * Returns root node of a tree.
	 * @return Root node or null if tree is empty (i.e. it has no root).
	 */
	public Node<T> getRoot(){
		return this.root;
	}

	/**
	 * Returns subtree of current tree with root node at
	 * {@code node}. The {@code node} argument should be child of
	 * current tree.
	 * @param node A node that new subtree consider as root.
	 * @throws Exception If current tree has no child equals to {@code node}.
	 * @return New tree retrieved from current.
	 */
	public abstract Tree<T> getSubtree(Node<T> node) throws Exception;
}
