package test.structure;

import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import org.junit.Test;

import common.structure.RegularTree;

public class RegularTreeTest {

	@Test
	public void emptyTree_HasZeroHeight() {
		assertEquals(0, new RegularTree().getHeight());
	}

	@Test
	public void specifiedRoot_EqualsSpecifiedContent() {
		var treeByContent = new RegularTree<String>("root");
		var treeByNode = new RegularTree<String>(new RegularTree.Node<>("root"));
		assertTrue(treeByContent.getRoot().equals(treeByNode.getRoot()));
	}

	public void getRoot_Equals_NodeGetRoot() {}

	public static class NodeTest {

		@Test
		public void getRoot_AtParent_IsNull() {
			var node = new RegularTree.Node<String>("A");
			assertNull(node.getRoot());
		}

		@Test
		public void getRoot_ReturnsCorrectValue() {
			var nodeA = new RegularTree.Node<String>("A");
			var nodeB = new RegularTree.Node<String>("B", nodeA);
			var nodeC = new RegularTree.Node<String>("С", nodeB);
			var nodeD = new RegularTree.Node<String>("D", nodeC);
			var nodeE = new RegularTree.Node<String>("E", nodeD);
			assertEquals(nodeE.getRoot().content, nodeA.content);
			assertEquals(nodeD.getRoot().content, nodeA.content);
			assertEquals(nodeC.getRoot().content, nodeA.content);
			assertEquals(nodeB.getRoot().content, nodeA.content);
		}

		@Test
		public void canGetAnonimousRoot() {
			var node = new RegularTree.Node<String>("C", new RegularTree.Node<String>("B", new RegularTree.Node<String>("A")));
			var root = new RegularTree<String>("A");
			var nodeB = new RegularTree.Node<String>("B");
			assertEquals(node.getParent(), nodeB);
			assertEquals(node.getRoot(), node);
			assertEquals(node.getParent().getParent(), node);
			assertEquals(node.getParent().getRoot(), node);
		}

		@Test
		public void getDepth_AtRoot_Is1() {
			assertEquals(1, new RegularTree.Node().getDepth());
		}

		@Test
		public void getDepth_IsCorrect() {
			assertEquals(5, getABCDEChain());
		}

		public void isLeaf_WhenChildrenAreNotPresent_IsTrue() {}
		public void isLeaf_WhenChildIsPresent_IsFalse() {}
		public void isLeaf_AfterAdding_IsFalse() {}
		public void isLeaf_AfterRemoving_IsTrue() {}

		public void unleash_DestroysLinks() {}
		public void unleash_DoesNotAffectChildren() {}
		public void unleash_WhenMultipleOccurences_UnleashesByReference() {}
		public void unleash_DecreasesSiblingsSize() {}

		// TODO removeNodeByNode_*
		public void removeNodeByContent_AtLeafNode_ThrowsException() {}
		public void removeNodeByContent_ThatDoesNotExist_ThrowsException() {}
		public void removeNodeByContent_ReturnsRemovedItem() {}
		public void removeNodeByContent_ThatIsNull_ReturnsNull() {}
		public void removeNodeByContent_ThatOccursMultiple_RemovesOnlyFirst() {}
		public void removeNodeByContent_ReturnsItems_UntilException() {}
		public void removeNodeByContent_UntilException_MakesNodeLeaf() {}
		public void removeNodeByContent_DestroysReferences() {}
		public void removeNodeByContent_DecreasesChildrenCount() {}

		public void addNodeByContent_PermitsMultipleOccurences() {}
		public void addNodeByNode_PermitsMultipleOccurences() {}
		public void addNodeByNode_CreatesBidirectionalReferences() {}

		// TODO hasNodeByNode_*
		public void hasNodeByContent_IfChildDoesNotExist_ReturnsFalse() {}
		public void hasNodeByContent_WhenChildIsPresent_ReturnsTrue() {}
		public void hasNodeByContent_WhenChildrenArePresent_ReturnsTrue() {}
		public void hasNodeByContent_AfterAdding_IsCorrect() {}
		public void hasNodeByContent_AfterRemoving_IsCorrect() {}

		private static RegularTree.Node<String> getABCDEChain() {
			return new RegularTree.Node<String>(
				"E", new RegularTree.Node<String>(
					"D", new RegularTree.Node<String>(
						"C", new RegularTree.Node<String>(
							"B", new RegularTree.Node<String>("A")
						)
					)
				)
			);
		}

		private static RegularTree.Node<String> getChain(int depth) {
			
		}

		private static RegularTree.Node<String> getChilded(int count) {
			var nodeA = new RegularTree.Node<String>("A");
			for (int i = 0; i < count; i++)
				nodeA.addNode(Integer.toString(i));
			return nodeA;
		}
	}
}
