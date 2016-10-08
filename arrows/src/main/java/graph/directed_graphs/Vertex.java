package graph.directed_graphs;

import java.util.Collections;
import java.util.List;

class Vertex {
    String value; // assume the value is distinguish. as ID of Vertex.
    List<Vertex> outgoings = Collections.emptyList(); // neighbors. Default value is empty, as end vertex.

    public Vertex(String v, List<Vertex> outgoings) {
        this.value = v;
        this.outgoings = outgoings;
    }

    public Vertex(String v) {
        this.value = v;
    }

    public void setOutgoings(List<Vertex> outgoings) {
        this.outgoings = outgoings;
    }
}
