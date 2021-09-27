package DHypergraphGenerator;

import org.ejml.simple.SimpleMatrix;

/**
 * Object that represents a VertexArcIncidenceMatrix
 * Allows us to easily store data about a given Matrix
 */
public class DirectionalHypergraph {
    
    SimpleMatrix vertexArcIncidenceMatrix;

    int numVertices, numArcs;

    int numHyperArcs;

    /**
     * Create a new DirectionalHypergraph with all empty values
     * @param numVertices number of vertices the hypergraph should have
     * @param numArcs number of arcs the hypergraph should have
     */
    public DirectionalHypergraph(int numVertices, int numArcs) {
        this.vertexArcIncidenceMatrix = new SimpleMatrix(numVertices, numArcs);

        this.numVertices = numVertices;
        this.numArcs = numArcs;
    }

    /**
     * Create new DirectionalHypergraph from 2D-Array
     * @param vertexArcIncidenceMatrix 2D Array to build hypergraph from
     */
    public DirectionalHypergraph(float[][] vertexArcIncidenceMatrix) {
        
        this.vertexArcIncidenceMatrix = new SimpleMatrix(vertexArcIncidenceMatrix);
        this.vertexArcIncidenceMatrix = this.vertexArcIncidenceMatrix.transpose();

        this.numVertices = vertexArcIncidenceMatrix.length;
        this.numArcs = vertexArcIncidenceMatrix[0].length;

    }

    /**
     * Get the vertex-arc incidence matrix for this hypergraph
     * @return the matrix as a SimpleMatrix
     */
    public SimpleMatrix getVertexArcIncidenceMatrix() {
        return vertexArcIncidenceMatrix;
    }

    /**
     * Set the number of hyperarcs for counting purposes
     * @param numHyperArcs number of hyperarcs
     */
    public void setNumHyperArcs(int numHyperArcs) {
        this.numHyperArcs = numHyperArcs;
    }

    /**
     * Get the number of hyperarcs
     * @return the number of hyperarcs
     */
    public int getNumHyperArcs() {
        return numHyperArcs;
    }

    /**
     * Get the number of vertices
     * @return the number of arcs
     */
    public int getNumVertices() {
        return numVertices;
    }

    /**
     * Get the number of arcs
     * @return the number of arcs
     */
    public int getNumArcs() {
        return numArcs;
    }

}
