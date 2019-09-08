package common.structure;

/**
 * Basic class for all node types that {@link Tree<T>} subclasses
 * contain. It may be node for binary trees, of node for regular trees.
 * @param <T> Type of objects that node can contain.
 */
public abstract class Node<T> {

	/** Stores actual content of node. */
	public final T content;
	/** Reference to a parent node or null if node has no parent. */
	protected Node<T> parent;

	/**
	 * Creates a node with specified content and parent node.
	 * @param content Object is stored inside node.
	 * @param parent A node that is to be parent for current.
	 *               {@code null} if node does not have parent.
	 */
	public Node(T content, Node<T> parent){
		this.content = content;
		this.parent = parent;
	}

	/**
	 * Default constructor for nodes.
	 * @param content Object is stored inside node.
	 */
	public Node(T content){
		this.content = content;
	}

	public Node(){
		this.content = null;
	}

	/**
	 * Returns parent node for current node.
	 * Node cannot have more than one parent.
	 * @return Parent node for current node or null if node has no parent (i.e. it is root itself).
	 */
	public final Node<T> getParent(){
		return this.parent;
	}

	/**
	 * Returns top-level parent node that has no parent.
	 * In tree, it means that {@code Node<T>.getRoot() == Tree<T>.getRoot()}.
	 * @return Topest node that has no parent or null if current node
	 *         does not have parent.
	 */
	public final Node<T> getRoot(){
		if(this.parent == null)
			return null;
		var root = this.parent;
		while(root.parent != null)
			root = root.parent;
		return root;
	}

	/**
	 * Leaf node is a node that has no children.
	 * @return {@code true} if current node is leaf.
	 */
	public final boolean isLeaf(){
		return this.getChildNodesCount() == 0;
	}

	/**
	 * Returns an amount of children.
	 * @return Amount of child nodes corresponding to the current node.
	 */
	public abstract int getChildNodesCount();

	/**
	 * Destroys bidirectional bonds between current node
	 * and its parent. I.e. removes so-called "edge" between them.
	 * Do nothing if node does not have parent.
	 */
	public abstract void unleash();

	/**
	 * Removes child node from current node.
	 * @param node A node to be removed.
	 * @return Removed node or null if supplied
	 *         node does not equal to any of children.
	 */
	public abstract Node<T> removeNode(Node<T> node);

	public abstract Node<T> removeNode(T content);

	public abstract boolean hasNode(Node<T> node);

	public abstract Node<T>[] getChildren();

	@Override
	public final int hashCode(){
		return this.content.hashCode();
	}

	@Override
	public final boolean equals(Object obj){
		return this.content.equals(obj);
	}
}