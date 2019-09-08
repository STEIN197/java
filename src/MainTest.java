import common.structure.RegularTree;
import common.structure.Tree;

public class MainTest {
	public static void main(String[] args) {
		RegularTree<String> t = new RegularTree<String>();
		var root = new RegularTree.Node<String>("Root");
		t.setRoot(root);
		var n1 = new RegularTree.Node<String>("1");
		root.addNode(n1);
		root.addNode("2");
		var n3 = new RegularTree.Node<String>("3");
		root.addNode(n3);
		var n1_1 = new RegularTree.Node<String>("1.1");
		n1.addNode(n1_1);
		n1.addNode("1.2");
		n1_1.addNode("1.1.1");
		n1_1.addNode("1.1.2");
		n1_1.addNode("1.1.3");
		n3.addNode("3.1");
		var n3_2 = new RegularTree.Node<String>("3.2");
		n3.addNode(n3_2);
		n3_2.addNode("3.2.1");
		n3_2.addNode("3.2.2");
		n3_2.addNode("3.2.3");
		t.traverse(node -> {
			System.out.println(node.content);
		}, Tree.TRAVERSE_IN_ORDER);
	}
}
