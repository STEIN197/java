package common.structure;

import java.util.function.Consumer;

public class BinaryTree<T> extends Tree<T> {

	@Override
	protected void traverseBreadthFirst(Consumer<TreeNode<T>> fn) {
		var queue = new Queue<Node<T>>((Node<T>) this.root);
		while (queue.size() > 0) {
			var currentNode = queue.dequeue();
			if (currentNode.left != null)
				queue.inqueue(currentNode.left);
			if (currentNode.right != null)
				queue.inqueue(currentNode.right);
			fn.accept(currentNode);
		}
	}

	@Override
	protected void traverseInOrder(Consumer<TreeNode<T>> fn) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void traversePostOrder(Consumer<TreeNode<T>> fn) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void traversePreOrder(Consumer<TreeNode<T>> fn) {
		var stack = new Stack<Node<T>>((Node<T>) this.root);
		while (stack.size() > 0) {
			var currentNode = stack.pop();
			if (currentNode.left != null)
				stack.push(currentNode.left);
			if (currentNode.right != null)
				stack.push(currentNode.right);
			fn.accept(currentNode);
		}
	}

	public static class Node<T> extends TreeNode<T> {

		/** Reference to the left child */
		protected Node<T> left;
		/** Reference to the right child */
		protected Node<T> right;

		/**
		 * Creates a node with specified content and parent node.
		 * @param content Object is stored inside node.
		 * @param parent A node that is to be parent for current.
		 *               {@code null} if node does not have parent.
		 */
		public Node(T content, Node<T> parent) {
			super(content, parent);
		}

		/**
		 * Default constructor for nodes.
		 * @param content Object is stored inside node.
		 */
		public Node(T content) {
			super(content);
		}

		/**
		 * Creates an empty node without any content.
		 */
		public Node() {
			super();
		}

		/**
		 * Removes left child from node. Destroys bidirectional
		 * bonds, i.e. any references between child and parent.
		 * @return Removed left item, or null, if there was no left child.
		 */
		public Node<T> removeLeft() {
			if (this.left == null)
				return null;
			this.left.parent = null;
			var left = this.left;
			this.left = null;
			return left;
		}

		/**
		 * Removes right child from node. Destroys bidirectional
		 * bonds, i.e. any references between child and parent.
		 * @return Removed right item, or null, if there was no right child.
		 */
		public Node<T> removeRight() {
			if (this.right == null)
				return null;
			this.right.parent = null;
			var right = this.right;
			this.right = null;
			return right;
		}

		/**
		 * Returns left child.
		 * @return Left child, or null if it has no left child.
		 */
		public Node<T> getLeft() {
			return this.left;
		}

		/**
		 * Returns right child.
		 * @return Right child, or null if it has no right child.
		 */
		public Node<T> getRight() {
			return this.right;
		}

		/**
		 * Sets left child or replace it with {@code node}.
		 * @param node A node to be left.
		 * @return Previous left node or null if there was no left node.
		 */
		public Node<T> setLeft(Node<T> node) {
			var left = this.removeLeft();
			this.left = node;
			this.left.parent = this;
			return left;
		}

		/**
		 * Sets left child or replace it with {@code content}.
		 * @param content A content to be the left node's content.
		 * @return Previous left node or null if there was no left node.
		 */
		public Node<T> setLeft(T content) {
			return this.setLeft(new Node<T>(content));
		}

		/**
		 * Sets right child or replace it with {@code node}.
		 * @param node A node to be right.
		 * @return Previous right node or null if there was no right node.
		 */
		public Node<T> setRight(Node<T> node) {
			var right = this.removeRight();
			this.right = node;
			this.right.parent = this;
			return right;
		}

		/**
		 * Sets right child or replace it with {@code content}.
		 * @param content A content to be the right node's content.
		 * @return Previous right node or null if there was no right node.
		 */
		public Node<T> setRight(T content) {
			return this.setRight(new Node<T>(content));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isLeaf() {
			return this.left == null && this.right == null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void unleash() {
			if (this.parent == null)
				return;
			var parent = (Node<T>) this.parent;
			this.parent = null;
			if (parent.left == this)
				parent.left = null;
			else
				parent.right = null;
		}
	}
}
