package experiment.copies;

import cse332.datastructures.trees.BinarySearchTree;

/**
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will 
 *    instead mask the super-class fields (i.e., the resulting node 
 *    would actually have multiple copies of the node fields, with 
 *    code accessing one pair or the other depending on the type of 
 *    the references used to access the instance).  Such masking will 
 *    lead to highly perplexing and erroneous behavior. Instead, 
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class ExpAVLTree<K extends Comparable<? super K>, V> extends ExpBinarySearchTree<K, V>  {

    private AVLNode current;

    public ExpAVLTree(){
        super();
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }

        this.root = insertFind(key, (AVLNode) this.root);
        V oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    private AVLNode insertFind(K key, AVLNode node){
        //insert, recurse to the bottom of the tree
        if(node == null) {
            current = new AVLNode(key, null);
            size++;
            return current;
        } else if(key.compareTo(node.key) > 0){
            node.children[1] = insertFind(key, node.castChildrenToAVL(1));
        } else if(key.compareTo(node.key) < 0){
            node.children[0] = insertFind(key, node.castChildrenToAVL(0));
        } else {
            current = node;
            return current;
        }
        // rebalance: perform rotation and update height
        return balanceTree(node);
    }

    private int getHeight(AVLNode node){
        return node == null ? -1 : node.height;
    }

    private void updateHeight(AVLNode node){
        node.height = 1 + Math.max(getHeight(node.castChildrenToAVL(0)),
                                   getHeight(node.castChildrenToAVL(1)));
    }

    private int getBalance(AVLNode node){
        return (node == null) ? 0 :
                getHeight(node.castChildrenToAVL(0)) - getHeight(node.castChildrenToAVL(1));
    }

    private AVLNode balanceTree(AVLNode node){
        updateHeight(node);

        int balVal = getBalance(node);
        if(balVal > 1) {                        // go left first
            int leftBalVal = getBalance(node.castChildrenToAVL(0));
            if(leftBalVal < 0) {                // left-right => double rot
                node.children[0] = rotate(node.castChildrenToAVL(0), false);
            }                                   // rot for single and double rot
            node = rotate(node, true);
        } else if(balVal < -1) {                // go right first
            int rightBalVal = getBalance(node.castChildrenToAVL(1));
            if(rightBalVal > 0) {                // right-left => double rot
                node.children[1] = rotate(node.castChildrenToAVL(1), true);
            }                                   // rot for single and double rot
            node = rotate(node, false);
        }

        return node;
    }

    public AVLNode rotate (AVLNode parent, boolean isRight) {
        // For rotate right (opp. for rotate left): ref to left child
        AVLNode temp = parent.castChildrenToAVL(isRight ? 0 : 1);
        // For rotate right (opp. for rotate left): set parent left ref to left child's right child
        parent.children[isRight ? 0 : 1] = temp.castChildrenToAVL(isRight ? 1 : 0);
        // For rotate right (opp. for rotate left): set child's right to parent (child has moved up)
        temp.children[isRight? 1 : 0] = parent;

        // update heights
        updateHeight(parent);
        updateHeight(temp);

        return temp;
    }

    //TODO: rotateLeft, Right, Double Rotate, insert (will have find)

    private class AVLNode extends BSTNode{
        public int height;

        public AVLNode(K key, V value){
            super(key, value);
            height = 0;
        }

        public AVLNode castChildrenToAVL (int childIndex) {
            return (AVLNode) this.children[childIndex];
        }
    }
}
