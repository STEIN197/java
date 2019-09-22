package common.structure;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

// TODO Document class, make tests
public class RegularTree<T> extends Tree<T> {

	public RegularTree(TreeNode<T> node) {
		super(node);
	}

	public RegularTree(T rootContent) {
		this.root = new Node<T>(rootContent);
	}

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

	@Override
	protected void traversePreOrder(Consumer<TreeNode<T>> fn) {
		this.traversePreOrder(fn, this.root);
	}

	@Override
	protected void traversePostOrder(Consumer<TreeNode<T>> fn) {
		this.traversePostOrder(fn, this.root);
	}
	
	@Override
	protected void traverseInOrder(Consumer<TreeNode<T>> fn) {
		this.traverseInOrder(fn, this.root);
	}

	private void traversePreOrder(Consumer<TreeNode<T>> fn, TreeNode<T> node) {
		fn.accept(node);
		var children = node.getChildren();
		for (var child : children)
			this.traversePreOrder(fn, child);
	}
	
	private void traversePostOrder(Consumer<TreeNode<T>> fn, TreeNode<T> node) {
		var children = node.getChildren();
		for (var child : children)
			if (child.isLeaf())
				fn.accept(child);
			else
				this.traversePostOrder(fn, child);
		fn.accept(node);
	}
	
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

	public static class Node<T> extends TreeNode<T> {

		private LinkedList<TreeNode<T>> children = new LinkedList<>();

		/**
		 * {@inheritDoc}
		 * 
		 * @param node
		 */
		public Node(T node) {
			super(node);
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

		@Override
		public TreeNode<T> removeNode(T content) {
			return this.removeNode(new Node<T>(content));
		}

		public void addNode(Node<T> node) {
			this.children.addLast(node);
			node.parent = this;
		}

		public void addNode(T node) {
			this.addNode(new Node<T>(node));
		}

		@Override
		public boolean hasNode(TreeNode<T> node) {
			return this.children.contains(node);
		}

		@Override
		public TreeNode<T>[] getChildren() {
			return this.children.toArray();
		}
	}
}
