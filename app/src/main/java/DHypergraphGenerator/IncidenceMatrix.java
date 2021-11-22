package DHypergraphGenerator;

import java.util.ArrayList;
import java.util.Arrays;

import org.ejml.simple.SimpleMatrix;

import org.ejml.data.SingularMatrixException;

public class IncidenceMatrix {

    SimpleMatrix asSimpleMatrix;
    float[][] asArray;

    int numRows;
    int numCols;

    int rank;

    boolean isLinearlyIndependent;

    ArrayList<int[]> basesList;
    
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
        generateBases();
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


    private void generateBases() {
        
        // Store the bases
        ArrayList<int[]> bases = new ArrayList<>();

        // For each column:
        for (int i = 0; i < this.numCols; i++) {

            // List of linearly independent columns
            int[] acceptedColumns = new int[this.rank];
            int numAcceptedCols = 1;
            int nextColNum = (i + 1) % this.numCols;
            acceptedColumns[0] = i;

            // Keep adding another column to matrix and testing for linear independence
            while (numAcceptedCols < this.rank && nextColNum != i) {

                // Create a matrix of appropriate size
                float[][] currentMatrix = new float[numAcceptedCols + 1][this.numRows];

                // Fill in the columns we have so far
                for (int j = 0; j < numAcceptedCols; j++) {

                    int toAdd = acceptedColumns[j];
                    currentMatrix[j] = this.asArray[toAdd];

                }

                // Add the next column
                currentMatrix[numAcceptedCols] = this.asArray[nextColNum];

                // Test matrix for linear independence
                boolean isCurrentLinInd = testIndependence(currentMatrix);

                // System.out.println(i + " " + nextColNum + " " + isCurrentLinInd);

                // Add the column to our list of accepted columns if it's linearly inddpendent
                if (isCurrentLinInd) {
                    acceptedColumns[numAcceptedCols] = nextColNum;
                    numAcceptedCols++;
                }

                nextColNum = (nextColNum + 1) % this.numCols;
            }

            // acceptedColumns should be a basis for the column space of this matrix

            // Sort the columns
            Arrays.sort(acceptedColumns);
            boolean newBasis = true;

            // Compare to existing bases and reject if they're the same
            for (int[] basis : bases) {
                if (Arrays.compare(acceptedColumns, basis) == 0) {
                    newBasis = false;
                    // System.out.println("Not a new basis");
                    break;
                }
            }

            // Add the basis if it is new
            if(newBasis) {
                bases.add(acceptedColumns);
                // System.out.println("Adding new basis");
            }
        }

        this.basesList = bases;
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

    public ArrayList<int[]> getBasesList() {
        generateBases();
        return this.basesList;
    }

    public void printBases() {
        generateBases();

        System.out.println("Column numbers that form bases are: ");

        for (int[] basis : this.basesList) {
            String toPrint = "";

            for(int i = 0; i < this.rank; i++) {
                toPrint += basis[i] + ", ";
            }
            System.out.println(toPrint);
        }
    }
}
