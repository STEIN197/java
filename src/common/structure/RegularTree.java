package common.structure;

import java.util.List;
import java.util.function.Consumer;
import java.util.LinkedList;

public class RegularTree<T> extends Tree<T> {

	public RegularTree(common.structure.Node<T> node) {
		super(node);
	}

	public RegularTree(T rootContent) {
		this.root = new Node<T>(rootContent);
	}

	public RegularTree() {
		super();
	}

	@Override
	public Tree<T> getSubtree(common.structure.Node<T> node) throws Exception {
		if (this.root == null)
			throw new Exception("Tree has no root element");
		else if (!this.root.hasNode(node))
			throw new Exception("Tree has no supplied child element");
		else
			return new RegularTree<T>(node);
	}

	@Override
	protected void traverseBreadthFirst(Consumer<common.structure.Node<T>> fn) {
		var queue = new Queue<common.structure.Node<T>>(this.root);
		while (queue.size() > 0) {
			var currentNode = queue.dequeue();
			common.structure.Node<T>[] children = currentNode.getChildren();
			for (var node : children)
				queue.inqueue(node);
			fn.accept(currentNode);
		}
	}

	@Override
	protected void traversePreOrder(Consumer<common.structure.Node<T>> fn) {
		this.traversePreOrder(fn, this.root);
	}

	@Override
	protected void traversePostOrder(Consumer<common.structure.Node<T>> fn) {
		this.traversePostOrder(fn, this.root);
	}
	
	@Override
	protected void traverseInOrder(Consumer<common.structure.Node<T>> fn) {
		this.traverseInOrder(fn, this.root);
	}

	private void traversePreOrder(Consumer<common.structure.Node<T>> fn, common.structure.Node<T> node) {
		fn.accept(node);
		var children = node.getChildren();
		for (var child : children)
			this.traversePreOrder(fn, child);
	}
	
	private void traversePostOrder(Consumer<common.structure.Node<T>> fn, common.structure.Node<T> node) {
		var children = node.getChildren();
		for (var child : children)
			if (child.isLeaf())
				fn.accept(child);
			else
				this.traversePostOrder(fn, child);
		fn.accept(node);
	}
	
	private void traverseInOrder(Consumer<common.structure.Node<T>> fn, common.structure.Node<T> node) {
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

	public static class Node<T> extends common.structure.Node<T> {

		private List<Node<T>> children = new LinkedList<>();

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
			return this.children.size();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void unleash() {
			if (this.parent == null)
				return;
			this.parent.removeNode(this);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @param node {@inheritDoc}
		 */
		@Override
		public common.structure.Node<T> removeNode(common.structure.Node<T> node) {
			if (this.hasNode(node)) {
				this.children.remove(node);
				node.parent = null;
				return node;
			}
			return null;
		}

		@Override
		public common.structure.Node<T> removeNode(T content) {
			return this.removeNode(new Node<T>(content));
		}

		public void addNode(Node<T> node) {
			this.children.add(node);
			node.parent = this;
		}

		public void addNode(T node) {
			this.addNode(new Node<T>(node));
		}

		@Override
		public boolean hasNode(common.structure.Node<T> node) {
			return this.children.contains(node);
		}

		@Override
		public common.structure.Node<T>[] getChildren() {
			Node<T>[] result = (Node<T>[]) new Node[this.children.size()];
			return this.children.toArray(result);
		}
	}

	@Override
	public int getHeight() {
		return 0;
	}
}
