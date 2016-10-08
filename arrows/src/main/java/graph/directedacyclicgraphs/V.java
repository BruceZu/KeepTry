package graph.directedacyclicgraphs;

import java.util.Collections;
import java.util.List;

class V {
    String value; // assume the value is distinguish. as ID of V.
    List<V> outgoings = Collections.emptyList(); // neighbors. Default value is empty, as end vertex.

    public V(String v, List<V> outgoings) {
        this.value = v;
        this.outgoings = outgoings;
    }

    public V(String v) {
        this.value = v;
    }

    public void setOutgoings(List<V> outgoings) {
        this.outgoings = outgoings;
    }
}
