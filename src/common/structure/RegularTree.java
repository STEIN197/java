package common.structure;

import java.util.List;
import java.util.LinkedList;

public class RegularTree<T> extends Tree<T> {

	public static class Node<T> extends common.structure.Node<T> {
		
		private List<Node<T>> children = new LinkedList<>();

		/**
		 * {@inheritDoc}
		 * @param node
		 */
		public Node(T node){
			super(node);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getChildNodesCount(){
			return this.children.size();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void unleash(){
			if(this.parent == null)
				return;
			this.removeNode(this);
		}

		/**
		 * {@inheritDoc}
		 * @param node {@inheritDoc}
		 */
		@Override
		public common.structure.Node<T> removeNode(common.structure.Node<T> node){
			this.children.remove(node);
			node.parent = null;
			return node;
		}

		public void addNode(Node<T> node){
			this.children.add(node);
			node.parent = this;
		}

		public void addNode(T node){
			this.addNode(new Node<T>(node));
		}
	}
}