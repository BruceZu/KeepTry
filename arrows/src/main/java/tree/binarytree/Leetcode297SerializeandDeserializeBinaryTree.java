//  Copyright 2017 The keepTry Open Source Project
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

package tree.binarytree;


public class Leetcode297SerializeandDeserializeBinaryTree {
    static class TobeDeSerialized {
        char[] data; // will be like "#,"; "1,2,#,#,3,4,#,#,5,#,#,";
        int index;
        int pre;

        TobeDeSerialized(String data) {
            this.data = data.toCharArray();
        }

        public String next() {
            if (index == data.length) {
                return null; // will not be here
            }
            pre = index;
            while (index < data.length && data[index] != ',') {
                index++;
            }
            return new String(data, pre, index++ - pre);
        }
    }

    static private void readTreeByPreOrder(TreeNode n, StringBuilder r) {
        if (n == null) {
            r.append("#").append(",");
            return;
        }
        r.append(n.v).append(",");
        readTreeByPreOrder(n.left, r);
        readTreeByPreOrder(n.right, r);
    }

    // Encodes a tree to a single string.
    static public String serialize(TreeNode root) {
        StringBuilder r = new StringBuilder();
        readTreeByPreOrder(root, r);
        return r.toString();
    }

    static private TreeNode constructByPreOrder(TobeDeSerialized data) {
        String v = data.next();
        if (v.equals("#")) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(v));
        node.left = constructByPreOrder(data);
        node.right = constructByPreOrder(data);
        return node;
    }

    // Decodes your encoded data to tree.
    static public TreeNode deserialize(String data) {
        return constructByPreOrder(new TobeDeSerialized(data));
    }

    public static void main(String[] args) {
        TreeNode tree = deserialize("1,2,#,#,3,4,#,#,5,#,#");
        TreeNode.drawingInOrder(tree);
        System.out.println(serialize(tree));
    }
}
