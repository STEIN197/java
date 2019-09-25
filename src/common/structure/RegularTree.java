package common.structure;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Concrete tree class implementing {@link Tree} abstract class.
 * It is regular tree structure that provides very basic logic to
 * create such structures.
 * @param <T>
 */
public class RegularTree<T> extends Tree<T> {

	/**
	 * Creates a tree with {@code root} root node.
	 * @param root Node to be a root.
	 */
	public RegularTree(TreeNode<T> node) {
		super(node);
	}

	/**
	 * Creates a tree with root element storing {@code rootContent}
	 * object as a content.
	 * @param rootContent Object to be stored in root node.
	 */
	public RegularTree(T rootContent) {
		this.root = new Node<T>(rootContent);
	}

	/**
	 * Creates an empty tree without root.
	 */
	public RegularTree() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tree<T> getSubtree(TreeNode<T> node) throws Exception {
		if (this.root == null)
			throw new Exception("Tree has no root element");
		else if (!this.root.hasNode(node))
			throw new Exception("Tree has no supplied child element");
		else
			return new RegularTree<T>(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void traverseBreadthFirst(Consumer<TreeNode<T>> fn) {
		var queue = new Queue<TreeNode<T>>(this.root);
		while (queue.size() > 0) {
			var currentNode = queue.dequeue();
			TreeNode<T>[] children = currentNode.getChildren();
			for (var node : children)
				queue.inqueue(node);
			fn.accept(currentNode);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void traversePreOrder(Consumer<TreeNode<T>> fn) {
		this.traversePreOrder(fn, this.root);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void traversePostOrder(Consumer<TreeNode<T>> fn) {
		this.traversePostOrder(fn, this.root);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void traverseInOrder(Consumer<TreeNode<T>> fn) {
		this.traverseInOrder(fn, this.root);
	}

	/**
	 * @see {@link #traversePreOrder(Consumer)}
	 */
	private void traversePreOrder(Consumer<TreeNode<T>> fn, TreeNode<T> node) {
		fn.accept(node);
		var children = node.getChildren();
		for (var child : children)
			this.traversePreOrder(fn, child);
	}
	
	/**
	 * @see {@link #traversePostOrder(Consumer)}
	 */
	private void traversePostOrder(Consumer<TreeNode<T>> fn, TreeNode<T> node) {
		var children = node.getChildren();
		for (var child : children)
			if (child.isLeaf())
				fn.accept(child);
			else
				this.traversePostOrder(fn, child);
		fn.accept(node);
	}
	
	/**
	 * @see {@link #traverseInOrder(Consumer)}
	 */
	private void traverseInOrder(Consumer<TreeNode<T>> fn, TreeNode<T> node) {
		var children = node.getChildren();
		for (int i = 0; i < children.length; i++) {
			var child = children[i];
			if (child.isLeaf()) {
				fn.accept(child);
			} else {
				this.traverseInOrder(fn, child);
			}
			if (i + 1 < children.length)
				fn.accept(child.parent);
		}
	}

	/**
	 * Concrete node class that is used by {@link RegularTree} class.
	 * @param <T>
	 */
	public static class Node<T> extends TreeNode<T> {

		/** Stores child nodes relate to current node */
		private LinkedList<TreeNode<T>> children = new LinkedList<>(); // TODO Replace it with HashTable in future for constant time operations

		/**
		 * Creates a node with specified content and parent node.
		 * @param content Object is stored inside node.
		 * @param parent A node that is to be parent for current.
		 *               {@code null} if node does not have parent.
		 */
		public Node(T content, TreeNode<T> parent){
			super(content, parent);
		}

		/**
		 * Default constructor for nodes.
		 * @param content Object is stored inside the node.
		 */
		public Node(T node) {
			super(node);
		}

		/**
		 * Creates an empty node without any content.
		 */
		public Node(){
			super();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getChildNodesCount() {
			return this.children.getSize();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TreeNode<T> removeNode(TreeNode<T> node) throws NoSuchElementException {
			TreeNode<T> old = this.children.remove(node);
			old.parent = null;
			return old;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TreeNode<T> removeNode(T content) {
			return this.removeNode(new Node<T>(content));
		}

		/**
		 * Adds child to the current node.
		 * @param node Node to be added.
		 */
		public void addNode(Node<T> node) {
			this.children.addLast(node);
			node.parent = this;
		}

		/**
		 * Creates node with content, specified by
		 * {@code node} argument, and adds it to the current one.
		 * @param node Content is stored inside new added child node.
		 */
		public void addNode(T node) {
			this.addNode(new Node<T>(node));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNode(TreeNode<T> node) {
			return this.children.contains(node);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TreeNode<T>[] getChildren() {
			return this.children.toArray();
		}
	}
}
// TODO Make tests
