package common.structure;

/**
 * Basic class for all node types that {@link Tree<T>} subclasses
 * contain. It may be node for binary trees, of node for regular trees.
 * Every derived class has {@link #parent} reference to a parent member,
 * All relations are bidirectioinal. That means, that it is possible to
 * make chain from leaves to root and vice versa.
 * @param <T> Type of objects that node can contain.
 */
public abstract class TreeNode<T> {

	/** Stores actual content of node. */
	public final T content;
	/** Reference to a parent node or null if node has no parent. */
	protected TreeNode<T> parent;

	/**
	 * Creates a node with specified content and parent node.
	 * @param content Object is stored inside node.
	 * @param parent A node that is to be parent for current.
	 *               {@code null} if node does not have parent.
	 */
	public TreeNode(T content, TreeNode<T> parent){
		this.content = content;
		this.parent = parent;
	}

	/**
	 * Default constructor for nodes.
	 * @param content Object is stored inside node.
	 */
	public TreeNode(T content){
		this.content = content;
	}

	/**
	 * Creates an empty node without any content.
	 */
	public TreeNode(){
		this.content = null;
	}

	/**
	 * Returns parent node for current node.
	 * Node cannot have more than one parent.
	 * @return Parent node for current node or null if node has no parent (i.e. it is root itself).
	 */
	public final TreeNode<T> getParent(){
		return this.parent;
	}

	/**
	 * Returns top-level parent node that has no parent.
	 * In tree, it means that {@code Node<T>.getRoot() == Tree<T>.getRoot()}.
	 * @return Topest node that has no parent or null if current node
	 *         does not have parent.
	 */
	public final TreeNode<T> getRoot(){
		if(this.parent == null)
			return null;
		var root = this.parent;
		while(root.parent != null)
			root = root.parent;
		return root;
	}

	/**
	 * Returns depth of the current node.
	 * If it does not have parent then its depth is 1 (or if it root which means the same).
	 * @return Depth of the node.
	 */
	public final int getDepth(){
		int depth = 1;
		if(this.parent == null)
			return depth;
		var root = this.parent;
		while (root.parent != null) {
			root = this.parent;
			depth++;
		}
		return depth;
	}

	/**
	 * Leaf node is a node that has no children.
	 * @return {@code true} if current node is leaf.
	 */
	public abstract boolean isLeaf();

	/**
	 * Destroys bidirectional bonds between current node
	 * and its parent. I.e. removes so-called "edge" between them.
	 * Does nothing if node does not have parent.
	 * It may seem that this method removes node from its parent,
	 * actually it removes it by reference, not by {@link #equals()}.
	 */
	public abstract void unleash();

	@Override
	public final int hashCode(){
		return this.content.hashCode();
	}

	@Override
	public final boolean equals(Object obj){
		if (obj == null)
			return false;
		if (obj instanceof TreeNode)
			return this.content.equals(((TreeNode) obj).content);
		return this.content.equals(obj);
	}
}