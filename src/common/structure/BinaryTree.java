package common.structure;

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

		protected Node<T> left;
		protected Node<T> right;

		@Override
		public int getChildNodesCount() {
			int count = 0;
			if (this.left != null)
				count++;
			if (this.right != null)
				count++;
			return count;
		}

		@Override
		public common.structure.Node<T>[] getChildren() {
			if (this.left != null && this.right != null) {
				return (common.structure.Node<T>[]) new common.structure.Node[]{
					this.left, this.right
				};
			} else if (this.left == null) {
				return (common.structure.Node<T>[]) new common.structure.Node[]{
					this.right
				};
			} else if (this.right == null) {
				return (common.structure.Node<T>[]) new common.structure.Node[]{
					this.left
				};
			}
			return (common.structure.Node<T>[]) new common.structure.Node[0];
		}

		@Override
		public boolean hasNode(common.structure.Node<T> node) {
			var children = this.getChildren();
			for (var n : children)
				if (n.equals(node))
					return true;
			return false;
		}

		@Override
		public common.structure.Node<T> removeNode(common.structure.Node<T> node) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public common.structure.Node<T> removeNode(T content) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void unleash() {
			// TODO Auto-generated method stub

		}
	}
}
