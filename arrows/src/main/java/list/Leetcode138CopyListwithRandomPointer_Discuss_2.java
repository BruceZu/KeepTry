package list;

/**
 * <img src="../../resources/graph_list_have_circle_clone2.png" height="250" width="600">
 */
public class Leetcode138CopyListwithRandomPointer_Discuss_2 {
    public static RandomListNode copyRandomList(RandomListNode head) {
        RandomListNode iter = head, next;

        // First round: make copy of each node,
        // and link them together side-by-side in a single list.
        while (iter != null) {
            next = iter.next;

            RandomListNode copy = new RandomListNode(iter.label);
            iter.next = copy;
            copy.next = next;

            iter = next; // please note: even the next pointer can form endless circle
        }

        // Second round: assign random pointers for the copy nodes.
        iter = head;
        while (iter != null) {
            if (iter.random != null) {
                iter.next.random = iter.random.next;
            }
            iter = iter.next.next;
        }

        // Third round: restore the original list, and extract the copy list.
        iter = head;
        RandomListNode pseudoHead = new RandomListNode(0);
        RandomListNode copy, copyIter = pseudoHead;

        while (iter != null) {
            next = iter.next.next;

            // extract the copy
            copy = iter.next;
            copyIter.next = copy;
            copyIter = copy;

            // restore the original list
            iter.next = next;

            iter = next;
        }

        return pseudoHead.next;
    }

    /*--------------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
        //  1 -> 2 -> 3 -> 4
        //  |<---|
        //  |-------->|

        RandomListNode one = new RandomListNode(1);
        RandomListNode two = new RandomListNode(2);
        RandomListNode three = new RandomListNode(3);
        RandomListNode four = new RandomListNode(4);

        one.next = two;
        one.random = three;

        two.next = one;
        two.random = three;

        three.next = four;

        RandomListNode copy = copyRandomList(one);
        System.out.println(copy); // For Adding Break Point. The main() will be replaced by test case one print() is ready
    }
}
