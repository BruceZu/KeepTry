//  Copyright 2016 The Minorminor Open Source Project
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

package charter3;

public interface MyLinkedList<E> extends Cloneable {

    public boolean isEmpty();

    public int size();

    /**
     * Assuming that we did not maintain variable size.
     * @return
     */
    @Deprecated
    public int size2();

    /**
     * Be Default, add to the head
     *
     * @param newContent
     */
    public MyLinkedList addToTheHead(E newContent);

    public MyLinkedList appendToTheEnd(E newContent);

    public MyLinkedList addBefore(E newContent, int index);

    public MyLinkedList addAfter(E newContent, int index);

    public E deleteHead();

    public E deleteEnd();

    public E delete(int index);

    public E update(int index, E newContent);

    public E updateHead(E newContent);

    public E updateEnd(E newContent);

    public E getHead();

    public E getEnd();

    public E get(int index);

    public void clean();

    public MyLinkedList<E> clone();
}
