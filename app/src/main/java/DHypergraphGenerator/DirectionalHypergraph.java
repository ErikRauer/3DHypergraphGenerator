package DHypergraphGenerator;

/**
 * Object that represents a VertexArcIncidenceMatrix
 * Allows us to easily store data about a given Matrix
 */
public class DirectionalHypergraph {
    
    IncidenceMatrix vertexArcIncidenceMatrix;

    int numVertices, numArcs;

    int numHyperArcs;

    boolean isLinearlyIndependent;

    /**
     * Create a new DirectionalHypergraph with all empty values
     * @param numVertices number of vertices the hypergraph should have
     * @param numArcs number of arcs the hypergraph should have
     */
    public DirectionalHypergraph(int numVertices, int numArcs) {
        this.vertexArcIncidenceMatrix = new IncidenceMatrix(numVertices, numArcs);

        this.numVertices = numVertices;
        this.numArcs = numArcs;

        this.numHyperArcs = 0;
        this.isLinearlyIndependent = false;
    }

    /**
     * Create new DirectionalHypergraph from 2D-Array
     * @param vertexArcIncidenceMatrix 2D Array to build hypergraph from
     */
    public DirectionalHypergraph(float[][] vertexArcIncidenceMatrix) {
        this.vertexArcIncidenceMatrix = new IncidenceMatrix(vertexArcIncidenceMatrix);

        this.numArcs = this.vertexArcIncidenceMatrix.getNumCols();
        this.numVertices = this.vertexArcIncidenceMatrix.getNumRows();

        countHyperArcs(this.vertexArcIncidenceMatrix.getAsArray());
    }

    /**
     * Count the number of hyperarcs in a given vertex-arc incidence matrix
     * @param matrix the vertex-arc incidence matrix as a 2D array
     */
    public void countHyperArcs(float[][] matrix) {

        this.numHyperArcs = 0;

        // For each column..
        for(int i = 0; i < this.numArcs; i++) {

            int sumOfCol = 0;

            // Add the total of it's values
            for(int j = 0; j < this.numVertices; j++) {
                sumOfCol += matrix[i][j];
            }

            // If the sum is not 0, it's a hyperarc
            if(sumOfCol != 0) {
                this.numHyperArcs++;
            }
        }
    }

    /**
     * Get the vertex-arc incidence matrix for this hypergraph
     * @return the matrix as a SimpleMatrix
     */
    public IncidenceMatrix getVertexArcIncidenceMatrix() {
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
