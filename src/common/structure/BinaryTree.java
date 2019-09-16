package common.structure;

import java.util.LinkedList;
import java.util.function.Consumer;

public class BinaryTree<T> extends Tree<T> {

	@Override
	public Tree<T> getSubtree(common.structure.Node<T> node) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void traverseBreadthFirst(Consumer<common.structure.Node<T>> fn) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void traverseInOrder(Consumer<common.structure.Node<T>> fn) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void traversePostOrder(Consumer<common.structure.Node<T>> fn) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void traversePreOrder(Consumer<common.structure.Node<T>> fn) {
		// TODO Auto-generated method stub

	}
	public static class Node<T> extends common.structure.Node<T> {

		/** Reference to the left child */
		protected Node<T> left;
		/** Reference to the right child */
		protected Node<T> right;

		public Node(T content, Node<T> parent) {
			super(content, parent);
		}

		public Node(T content) {
			super(content);
		}

		public Node() {
			super();
		}

		@Override
		public int getChildNodesCount() {
			int count = 0;
			if (this.left != null)
				count++;
			if (this.right != null)
				count++;
			return count;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public common.structure.Node<T>[] getChildren() {
			var children = new LinkedList<common.structure.Node<T>>();
			if (this.left != null)
				children.add(this.left);
			if (this.right != null)
				children.add(this.right);
			return (common.structure.Node<T>[]) children.toArray();
		}

		@Override
		public boolean hasNode(common.structure.Node<T> node) {
			return
				this.left != null && this.left.equals(node)
					||
				this.right != null && this.right.equals(node);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public common.structure.Node<T> removeNode(common.structure.Node<T> node) {
			if (this.left != null && this.left.equals(node))
				return this.removeLeft();
			if (this.right != null && this.right.equals(node))
				return this.removeRight();
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public common.structure.Node<T> removeNode(T content) {
			return this.removeNode(new Node<T>(content));
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
	}
}
