//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package datastructure.list;

public class ListRecursonImplementation {
    // a list is:
    //  empty
    // or
    //  has a head with data in it, followed by a tail that is a list
    public static void print(ListNode node) {
        if (node == null)
            return;
        else {
            System.out.println(node.val);
            print(node.next);
        }
    }

    public static int length(ListNode l) {
        if (l == null)
            return 0;
        else
            return 1 + length(l.next);
    }

    public static int sum(ListNode l) {
        if (l == null)
            return 0;
        else
            return l.val + sum(l.next);
    }

    public static ListNode search(ListNode l, int value) {
        if (l == null || l.val == value)
            return l;
        else
            return search(l.next, value);
    }

    public static ListNode copy(ListNode l) {
        if (l == null)
            return null;
        else
            return new ListNode(l.val, copy(l.next));
    }

    public static ListNode insertRear(ListNode l, int value) {
        if (l == null) {
            l = new ListNode(value, null);
        } else {
            l.next = insertRear(l.next, value);
        }
        return l;
    }

    public static ListNode insertOrdered(ListNode l, int value) {
        if (l == null || value < l.val)
            return new ListNode(value, l);
        else {
            l.next = insertOrdered(l.next, value);
            return l;
        }
    }

    public static ListNode removeFirst(ListNode l, int value) {
        if (l != null) {
            if (l.val == value) {
                l = l.next;
            } else {
                l.next = removeFirst(l.next, value);
            }
        }
        return l;
    }

    public static ListNode removeFirstFromOrdered(ListNode p, int k) {
        if (p == null || p.val > k) {
            return p;
        } else if (p.val == k)
            return p.next;
        else {
            p.next = removeFirstFromOrdered(p.next, k);
            return p;
        }
    }

    public static ListNode removeAll(ListNode l, int value) {
        if (l != null) {
            if (l.val == value) {
                l = removeAll(l.next, value);
            } else {
                l.next = removeAll(l.next, value);
            }
        }
        return l;
    }

    public static ListNode removeAllFromOrdered(ListNode p, int k) {
        if (p == null || p.val > k) {
            return p;
        } else if (p.val == k)
            return removeAllFromOrdered(p.next, k);
        else {
            p.next = removeAllFromOrdered(p.next, k);
            return p;
        }
    }

    public static boolean contains(Object x, ListNode n) {
        if (n == null) {
            return false;
        }
        if (x.equals(n.val)) {
            return true;
        }
        return contains(x, n.next);
    }

    public static ListNode deleteLast(ListNode p) {
        if (p == null || p.next == null)
            return null;
        else {
            p.next = deleteLast(p.next);
            return p;
        }
    }

    // adds the second list to the end of the first list
    public static ListNode append(ListNode p, ListNode q) {
        if (p == null)
            return q;
        else {
            p.next = append(p.next, q);
            return p;
        }
    }

    // zips up two lists, taking a node from one, then from the other.
    public static ListNode zip(ListNode p, ListNode q) {
        if (p == null)
            return q;
        else if (q == null)
            return p;
        else {
            p.next = zip(q, p.next); //  exchange p and q here
            return p;
        }
    }

    // merges nodes from two sorted lists, preserving their order:
    ListNode merge(ListNode p, ListNode q) {
        if (p == null)
            return q;
        if (q == null)
            return p;
        if (p.val < q.val) {
            p.next = merge(p.next, q);
            return p;
        }
        q.next = merge(p, q.next);
        return q;
    }
}
