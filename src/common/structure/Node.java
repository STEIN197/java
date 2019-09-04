package common.structure;

/**
 * Basic class for all node types that {@link Tree<T>} subclasses
 * contain. It may be node for binary trees, of node for regular trees.
 * @param <T> Type of objects that node can contain.
 */
public abstract class Node<T> {

	/** Stores actual content of node. */
	private T content;
	/** Reference to a parent node or null if node has no parent. */
	private Node<T> parent;

	/**
	 * Default constructor for nodes.
	 * @param content Object is stored inside node.
	 */
	public Node(T content){
		this.content = content;
	}

	/**
	 * Returns parent node for current node.
	 * Node cannot have more that one parent.
	 * @return Parent node for current node or null if node has no parent (i.e. it is root itself).
	 */
	public Node<T> getParent(){
		return this.parent;
	}

	/**
	 * Leaf node is a node that has no children.
	 * @return {@code true} if current node is leaf.
	 */
	public abstract boolean isLeaf();

	/**
	 * Returns an amount of children.
	 * @return Amount of child nodes corresponding to the current node.
	 */
	public abstract int getChildNodesCount();

	@Override
	public int hashCode() {
		return this.content.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.content.equals(obj);
	}

}