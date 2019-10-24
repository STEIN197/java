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
	public RegularTree(Node<T> node) {
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
	protected void traverseBreadthFirst(Consumer<TreeNode<T>> fn) {
		var queue = new Queue<Node<T>>((Node<T>) this.root);
		while (queue.size() > 0) {
			var currentNode = queue.dequeue();
			Node<T>[] children = currentNode.getChildren();
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
		this.traversePreOrder(fn, (Node<T>) this.root);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void traversePostOrder(Consumer<TreeNode<T>> fn) {
		this.traversePostOrder(fn, (Node<T>) this.root);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void traverseInOrder(Consumer<TreeNode<T>> fn) {
		this.traverseInOrder(fn, (Node<T>) this.root);
	}

	/**
	 * @see {@link #traversePreOrder(Consumer)}
	 */
	private void traversePreOrder(Consumer<TreeNode<T>> fn, Node<T> node) {
		fn.accept(node);
		var children = node.getChildren();
		for (var child : children)
			this.traversePreOrder(fn, child);
	}
	
	/**
	 * @see {@link #traversePostOrder(Consumer)}
	 */
	private void traversePostOrder(Consumer<TreeNode<T>> fn, Node<T> node) {
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
	private void traverseInOrder(Consumer<TreeNode<T>> fn, Node<T> node) {
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
		private LinkedList<Node<T>> children = new LinkedList<>(); // TODO Replace it with HashTable in future for constant time operations

		/**
		 * Creates a node with specified content and parent node.
		 * @param content Object is stored inside node.
		 * @param parent A node that is to be parent for current.
		 *               {@code null} if node does not have parent.
		 */
		public Node(T content, Node<T> parent){
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
		 * Removes child node from current node. If node has
		 * more than one children equal to {@code node}, then
		 * the first occurence will be removed.
		 * @param node A node to be removed.
		 * @return Removed node.
		 * @throws NoSuchElementException If there is no {@code node} child in node.
		 */
		public Node<T> removeNode(Node<T> node) throws NoSuchElementException {
			Node<T> old = this.children.remove(node);
			old.parent = null;
			return old;
		}

		/**
		 * Removes child node from current, if any of children
		 * has content in way that expression {@code content.equals(node.content)}
		 * evaluates to {@code true}. It is the same as {@link #removeNode(TreeNode)},
		 * except that argument is not wrapper. If node has
		 * more than one children equal to {@code node}, then
		 * the first occurence will be removed.
		 * @param content Node with this object inside will be removed.
		 * @return Removed child.
		 * @throws NoSuchElementException If there is no {@code content} child in node.
		 */
		public Node<T> removeNode(T content) throws NoSuchElementException {
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
		 * Check if node has specified child
		 * that contains {@code content} item inside.
		 * @param content A content is considered as child of current node.
		 * @return {@code true} if current node has child node with {@code content} content inside.
		 */
		public boolean hasNode(T content) {
			return this.hasNode(new Node<T>(content));
		}

		/**
		 * Check if node has specified child.
		 * @param node A node is considered as child of current node.
		 * @return {@code true} if current node has {@code node} child.
		 */
		public boolean hasNode(Node<T> node) {
			return this.children.contains(node);
		}

		/**
		 * Returns an array of children of current node.
		 * @return An array.
		 */
		public Node<T>[] getChildren() {
			return this.children.toArray();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void unleash() {
			if (this.parent == null)
				return;
			var parent = (Node<T>) this.parent;
			var siblings = parent.getChildren();
			for (int i = 0; i < siblings.length; i++) {
				if (siblings[i] == this) {
					this.parent = null;
					parent.children.removeAt(i);
					return;
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isLeaf() {
			return this.children.getSize() == 0;
		}
	}
}
// TODO Make tests, replace recursive in-depth methods to stack object
