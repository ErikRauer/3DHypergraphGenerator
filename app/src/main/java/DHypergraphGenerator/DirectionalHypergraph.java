package DHypergraphGenerator;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

import org.ejml.data.SingularMatrixException;

/**
 * Object that represents a VertexArcIncidenceMatrix
 * Allows us to easily store data about a given Matrix
 */
public class DirectionalHypergraph {
    
    SimpleMatrix vertexArcIncidenceMatrix;

    int numVertices, numArcs;

    int numHyperArcs;

    boolean isLinearlyIndependent;

    /**
     * Create a new DirectionalHypergraph with all empty values
     * @param numVertices number of vertices the hypergraph should have
     * @param numArcs number of arcs the hypergraph should have
     */
    public DirectionalHypergraph(int numVertices, int numArcs) {
        this.vertexArcIncidenceMatrix = new SimpleMatrix(numVertices, numArcs);

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
        float[][] cleanedMatrix = this.removeEmptyCols(vertexArcIncidenceMatrix);
        
        this.vertexArcIncidenceMatrix = new SimpleMatrix(cleanedMatrix);
        this.vertexArcIncidenceMatrix = this.vertexArcIncidenceMatrix.transpose();

        this.numArcs = cleanedMatrix.length;
        this.numVertices = cleanedMatrix[0].length;

        countHyperArcs(cleanedMatrix);
        testIndependence();
    }

    private float[][] removeEmptyCols(float[][] matrix) {

        ArrayList<Integer> nonemptyColsCount = new ArrayList();

        // For each column..
        for(int i = 0; i < matrix.length; i++) {

            // Check if all the values are zero
            vertLoop:
            for(int j = 0; j < matrix[i].length; j++) {

                //If any isn't zero, increase count of nonempty columns
                if(matrix[i][j] != 0) {
                    nonemptyColsCount.add(i);
                    break vertLoop;
                }
            }
        }

        float[][] newMatrix = new float[nonemptyColsCount.size()][numVertices];

        for(int i = 0; i < nonemptyColsCount.size(); i++) {
            newMatrix[i] = matrix[nonemptyColsCount.get(i)];
        }

        return newMatrix;
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

    public void testIndependence() {
        // The matrix is definitely not linearly independent if there's more cols than rows not square
        if(this.numArcs > this.numVertices) {
            this.isLinearlyIndependent = false;

        } else {
            try {
                // Create empty vector to test for linear independence
                SimpleMatrix empty = new SimpleMatrix(this.numVertices, 1);

                // Try to solve the equation Ax=0
                // If this doesn't throw an exception, the matrix is linearly independent
                this.vertexArcIncidenceMatrix.solve(empty);

                this.isLinearlyIndependent = true;
            } catch (SingularMatrixException e) {
                // If an exception was thrown, the matrix is linearly dependent
                this.isLinearlyIndependent = false;
            }
        }
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

    /**
     * Get whether or not the Vertex-Hyperarc Matrix for this Hypergraph is linearly independent
     * @return true if the matrix is linearly independent
     */
    public boolean isLinearlyIndependent() {
        return isLinearlyIndependent;
    }

}
