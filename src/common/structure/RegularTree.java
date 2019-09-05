package common.structure;

import java.util.List;
import java.util.LinkedList;

public class RegularTree<T> extends Tree<T> {

	public static class Node<T> extends common.structure.Node<T> {
		
		private List<Node<T>> children = new LinkedList<>();

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
		public boolean isLeaf() {
			return this.getChildNodesCount() == 0;
		}
	}
}