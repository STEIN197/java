package common.structure;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Basic class for all tree data structures like
 * binary tree, binary search tree and etc. Every new tree
 * class should inherit this class.
 * @param <T> Type of objects that are stored inside tree structure and nodes
 */
public abstract class Tree<T> {

	/**
	 * Pass this flag to {@link #traverse(Consumer, byte)} as
	 * the second argument to traverse tree in pre-order way.
	 */
	public static final byte TRAVERSE_PRE_ORDER = 0;

	/**
	 * Pass this flag to {@link #traverse(Consumer, byte)} as
	 * the second argument to traverse tree in in-order way.
	 */
	public static final byte TRAVERSE_IN_ORDER = 1;

	/**
	 * Pass this flag to {@link #traverse(Consumer, byte)} as
	 * the second argument to traverse tree in post-order way.
	 */
	public static final byte TRAVERSE_POST_ORDER = 2;

	/**
	 * Pass this flag to {@link #traverse(Consumer, byte)} as
	 * the second argument to traverse tree in breadth.
	 */
	public static final byte TRAVERSE_BREADTH_FIRST = 3;

	/** Reference to a root of tree or null if tree is empty. */
	protected TreeNode<T> root;

	/**
	 * Creates a tree with {@code root} root node.
	 * @param root Node to be a root.
	 */
	public Tree(TreeNode<T> root){
		this.root = root;
	}

	/**
	 * Creates an empty tree without root.
	 */
	public Tree(){
		this.root = null;
	}

	/**
	 * Returns height of a tree, starting from root node and
	 * ending with deepest leaf node. To calculate, it iterates all
	 * the nodes in tree and calculate depth of only leaf nodes.
	 * @return Height of tree.
	 */
	public int getHeight(){
		var height = new AtomicInteger(0);
		this.traverse(node -> {
			if(!node.isLeaf())
				return;
			var depth = node.getDepth();
			if(depth > height.get())
				height.set(depth);
		}, TRAVERSE_BREADTH_FIRST);
		return height.get();
	}

	/**
	 * Returns root node of a tree.
	 * @return Root node or null if tree is empty (i.e. it has no root).
	 */
	public final TreeNode<T> getRoot(){
		return this.root;
	}

	/**
	 * Sets tree's root if it is null yet.
	 * Otherwise does nothing.
	 * @param node A node to be a root.
	 */
	public void setRoot(TreeNode<T> node){
		if(this.root == null)
			this.root = node;
	}

	/**
	 * Returns subtree of current tree with root node at
	 * {@code node}. The {@code node} argument should be child of
	 * current tree.
	 * @param node A node that new subtree consider as root.
	 * @throws Exception If current tree has no child equals to {@code node}
	 *                   or if three has no root element.
	 * @return New tree retrieved from current.
	 */
	public abstract Tree<T> getSubtree(TreeNode<T> node) throws Exception;

	// public abstract boolean isBalanced();

	/**
	 * Makes a traversal through entire tree. There might be 4 ways
	 * to do it - one in-breadth method and 3 in-depth methods.
	 * @param fn A function to be applied to each node traversal method finds.
	 * @param method A method used to traverse antire structure. It must be
	 *               one of {@code Tree<T>.TRAVERSE_*} constant. Otherwise does
	 *               nothing.
	 */
	public final void traverse(Consumer<TreeNode<T>> fn, byte method){
		if(this.root == null)
			return;
		switch(method){
			case TRAVERSE_PRE_ORDER:
				this.traversePreOrder(fn);
				break;
			case TRAVERSE_IN_ORDER:
				this.traverseInOrder(fn);
				break;
			case TRAVERSE_POST_ORDER:
				this.traversePostOrder(fn);
				break;
			case TRAVERSE_BREADTH_FIRST:
				this.traverseBreadthFirst(fn);
				break;
			default:
				return;
		}
	}

	/**
	 * It is called from {@link #traverse(Consumer, byte)}
	 * to traverse tree in pre-order way if {@link #TRAVERSE_PRE_ORDER}
	 * is passed as second argument.
	 * @param fn Function to be executed per each node in tree.
	 */
	protected abstract void traversePreOrder(Consumer<TreeNode<T>> fn);

	/**
	 * It is called from {@link #traverse(Consumer, byte)}
	 * to traverse tree in in-order way if {@link #TRAVERSE_IN_ORDER}
	 * is passed as second argument.
	 * @param fn Function to be executed per each node in tree.
	 */
	protected abstract void traverseInOrder(Consumer<TreeNode<T>> fn);

	/**
	 * It is called from {@link #traverse(Consumer, byte)}
	 * to traverse tree in post-order way if {@link #TRAVERSE_POST_ORDER}
	 * is passed as second argument.
	 * @param fn Function to be executed per each node in tree.
	 */
	protected abstract void traversePostOrder(Consumer<TreeNode<T>> fn);

	/**
	 * It is called from {@link #traverse(Consumer, byte)}
	 * to traverse tree in breadth way if {@link #TRAVERSE_BREADTH_FIRST}
	 * is passed as second argument.
	 * @param fn Function to be executed per each node in tree.
	 */
	protected abstract void traverseBreadthFirst(Consumer<TreeNode<T>> fn);
}
