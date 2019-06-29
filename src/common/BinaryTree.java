package common;

public class BinaryTree<T extends Comparable<T>> {

	private Node<T> root;

	public void add(T data){
		var node = new Node<T>(data);
		if(this.root == null){
			this.root = node;
			return;
		}
		var currentNode = this.root;
		while(currentNode != null){
			if(node.data.compareTo(currentNode.data) < 0){
				if(currentNode.left == null){
					currentNode.left = node;
					currentNode = null;
				} else {
					currentNode = currentNode.left;
				}
			} else {
				if(currentNode.right == null){
					currentNode.right = node;
					currentNode = null;
				} else {
					currentNode = currentNode.right;
				}
			}
		}
	}

	public T getMin() throws NullPointerException {
		var node = this.root;
		while(node.left != null)
			node = node.left;
		return node.data;
	}

	public T getMax() throws NullPointerException {
		var node = this.root;
		while(node.right != null)
			node = node.right;
		return node.data;
	}

	public T getRoot(){
		try {
			return this.root.data;
		} catch(NullPointerException ex) {
			return null;
		}
	}

	private static class Node<T extends Comparable<T>> {

		public Node<T> left;
		public Node<T> right;
		public Node<T> parent;
		public T data;

		public Node(T data){
			this.data = data;
		}
	}
}
