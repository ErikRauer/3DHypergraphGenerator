package DHypergraphGenerator;

import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

import org.ejml.data.SingularMatrixException;

public class IncidenceMatrix {

    SimpleMatrix asSimpleMatrix;
    float[][] asArray;

    int numRows;
    int numCols;

    int rank;

    boolean isLinearlyIndependent;
    
    public IncidenceMatrix(int numVertices, int numArcs) {
        this.asSimpleMatrix = new SimpleMatrix(numVertices, numArcs);
        this.asArray = new float[numArcs][numVertices];

        for(int i = 0; i < numArcs; i++) {
            for(int j = 0; j < numVertices; j++) {
                this.asArray[i][j] = 0;
            }
        }
        
        this.numRows = numVertices;
        this.numCols = numArcs;

        this.isLinearlyIndependent = false;
    }



    public IncidenceMatrix(float[][] matrix) {
        float[][] cleanedMatrix = this.removeEmptyCols(matrix);
        
        this.asArray = cleanedMatrix;

        this.asSimpleMatrix = new SimpleMatrix(cleanedMatrix);
        this.asSimpleMatrix = this.asSimpleMatrix.transpose();

        this.numCols = cleanedMatrix.length;
        this.numRows = cleanedMatrix[0].length;

        this.isLinearlyIndependent = testIndependence(cleanedMatrix);

        calculateRank();
    }

    
    /**
     * Removes all columns with only 0 values from the matrix
     * @param matrix the matrix to remove the cols from
     * @return a new matrix with all the 0 columns removed, but everything else kept the same
     */
    private float[][] removeEmptyCols(float[][] matrix) {

        // Keep track of which columns aren't empty
        ArrayList<Integer> nonemptyColsCount = new ArrayList<>();
        int numVertices = matrix[0].length;

        // For each column..
        for(int i = 0; i < matrix.length; i++) {

            // Check if all the values are zero
            vertLoop:
            for(int j = 0; j < matrix[i].length; j++) {

                //If any isn't zero, add to our list of nonempty columns
                if(matrix[i][j] != 0) {
                    nonemptyColsCount.add(i);
                    break vertLoop;
                }
            }
        }

        // Create new matrix and copy the nonempty columns into it
        float[][] newMatrix = new float[nonemptyColsCount.size()][numVertices];

        for(int i = 0; i < nonemptyColsCount.size(); i++) {
            newMatrix[i] = matrix[nonemptyColsCount.get(i)];
        }

        return newMatrix;
    }


    private boolean testIndependence(float[][] matrix) {

        int colsNum = matrix.length;
        int rowsNum = matrix[0].length;

        // The matrix is definitely not linearly independent if there's more cols than rows not square
        if(colsNum > rowsNum) {
            return false;

        } else {
            try {
                // Create empty vector to test for linear independence
                SimpleMatrix empty = new SimpleMatrix(rowsNum, 1);

                // Convert to SimpleMatrix
                SimpleMatrix inSimpleMatrixForm = new SimpleMatrix(matrix);
                inSimpleMatrixForm = inSimpleMatrixForm.transpose();

                // Try to solve the equation Ax=0
                // If this doesn't throw an exception, the matrix is linearly independent
                inSimpleMatrixForm.solve(empty);

                return true;
            } catch (SingularMatrixException e) {
                // If an exception was thrown, the matrix is linearly dependent
                return false;
            }
        }
    }


    private void calculateRank() {
        this.rank = this.asSimpleMatrix.svd().rank();
    }

    public int getNumRows() {
        return this.numRows;
    }
    
    public int getNumCols() {
        return this.numCols;
    }

    public float[][] getAsArray() {
        return this.asArray;
    }

    public SimpleMatrix getAsSimpleMatrix() {
        return this.asSimpleMatrix;
    }

    public int getRank() {
        calculateRank();
        return this.rank;
    }
    
    /**
     * Get whether or not the Vertex-Hyperarc Matrix for this Hypergraph is linearly independent
     * @return true if the matrix is linearly independent
     */
    public boolean isLinearlyIndependent() {
        this.isLinearlyIndependent = testIndependence(this.asArray);
        return isLinearlyIndependent;
    }
}
