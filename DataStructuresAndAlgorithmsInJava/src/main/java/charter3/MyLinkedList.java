package charter3;

public interface MyLinkedList<E> {

    public boolean empty();

    public int size();

    /**
     * Be Default, the Added one will be the head
     *
     * @param newContent
     */
    public void add(E newContent);

    public void appendToTheEnd(E newContent);

    public void addBefore(E newContent, int index);

    public void addAfter(E newContent, int index);

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
}