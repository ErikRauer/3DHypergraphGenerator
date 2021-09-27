package DHypergraphGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.ejml.simple.SimpleMatrix;

public class HypergraphGenerator {

    // (Approximate) Proportion of arcs that should have two heads and two tails respectively
    // NOTE: proportion of hyperarcs = TWO_HEAD_PROP + TWO_TAIL_PROP
    // TODO: Make these parameters in the generate() method
    final static double TWO_HEAD_PROP = 0.4;
    final static double TWO_TAIL_PROP = 0.4;

    // Maximum number of times the generator will attempt to create a nonduplicate column before giving up
    final static int MAX_RETRIES = 5;

    /**
     * Class for the generation of hypergraph vertex-hyperarc incidence matrix
     */

    
     /**
      * Method that generates the Matrices
      * @param numGraphs number of graphs to create, number of matrices to return
      * @param numVertices number of vertices the graphs should have = number of rows in the matrices, must be >= 3
      * @param numArcs number of arcs the graphs should have = number of columns in the matrices, must be >= 1
      */
    public ArrayList<DirectionalHypergraph> generate(int numGraphs, int numVertices, int numArcs) {

        ArrayList<DirectionalHypergraph> graphList = new ArrayList<DirectionalHypergraph>();

        // Check that there are at least 3 vertices, otherwise can't generate hyperarcs
        if(numVertices < 3) {
            System.out.println("Too few vertices");
            return graphList;
        } else if (numArcs < 1) {
            System.out.println("Too few arcs");
            return graphList;
        }

        // Create numGraphs matrices and add them to matrixList
        for(int i = 0; i < numGraphs; i++) {

            DirectionalHypergraph currentGraph = createHypergraph(numVertices, numArcs);

            graphList.add(currentGraph);
        }

        return(graphList);
    }

    /**
     * Randomly generate a hyperarc-incidence matrix
     * @param numRows number of rows = number of vertices
     * @param numCols number of columns = number of arcs
     * @return the hyperarc-incidence matrix as a SimpleMatrix 
     */
    private DirectionalHypergraph createHypergraph(int numRows, int numCols) {

        // Call generateColumn num Cols times and add them to a float[]
        float[][] matrix = new float[numCols][numRows];

        int currentNumColumns = 0;
        
        for(int i = 0; i < numCols; i++) {

            // Counter to check the number of times this column has been generated
            int currentNumTries = 0;
            float[] newColumn = new float[numRows];

            // Only try to generate a unique column MAX_RETRIES times
            retryLoop:
            while(currentNumTries < MAX_RETRIES) {

                // Generate a new potential column
                newColumn = generateColumn(numRows);

                // Check for duplicate, if it is not a duplicate, we're done
                if(!duplicateColumn(matrix, newColumn, numCols, currentNumColumns)) {
                    break retryLoop;
                }

                // Reset newColumn
                for(int j = 0; j < numRows; j++) {
                    newColumn[j] = 0;
                }

                currentNumTries++;
            }

            matrix[i] = newColumn;

            currentNumColumns++;

        }


        DirectionalHypergraph endGraph = new DirectionalHypergraph(matrix);
        return endGraph;

    }

    /**
     * Generates a column/arc for the hyperarc-incidence matrix
     * @param numElements the number of elements the column should have = number of vertices in the hypergraph
     * @return the column/arc as a float[numElements]
     */
    private float[] generateColumn(int numElements) {

        float[] column;

        // Decide which type of arc/hyperarc to create
        double columnTypeSeed = Math.random();
        if(columnTypeSeed < TWO_HEAD_PROP) {
            column = generateTwoHeadedArc(numElements);
        } else if (columnTypeSeed < TWO_HEAD_PROP + TWO_TAIL_PROP) {
            column = generateTwoTailedArc(numElements);
        } else {
            column = generateRegularArc(numElements);
        }

        return column;
    }
    
    /**
     * Generates a column/arc with two heads and one tail for the hyperarc-incidence matrix
     * @param numElements the number of elements the column should have = number of vertices in the hypergraph
     * @return the column/arc as a float[numElements]
     */
    private float[] generateTwoHeadedArc(int numElements) {


        // Randomly sample the 3 indexes for the tails/heads of the arc
        // Might not be the most efficient to be doing this every time we want
        // a new arc, so might change later.
        ArrayList<Integer> sampleNums = new ArrayList<>();

        for(int i = 0; i < numElements; i++) {
            sampleNums.add(i);
        }

        Collections.shuffle(sampleNums);

        float[] arc = new float[numElements];

        arc[sampleNums.get(0)] = -1;
        arc[sampleNums.get(1)] = 1;
        arc[sampleNums.get(2)] = 1;

        return arc;
    }

    /**
     * Generates a column/arc with two tails and one head for the hyperarc-incidence matrix
     * @param numElements the number of elements the column should have = number of vertices in the hypergraph
     * @return the column/arc as a float[numElements]
     */
    private float[] generateTwoTailedArc(int numElements) {


        // Randomly sample the 3 indexes for the tails/heads of the arc
        // Might not be the most efficient to be doing this every time we want
        // a new arc, so might change later.
        ArrayList<Integer> sampleNums = new ArrayList<>();

        for(int i = 0; i < numElements; i++) {
            sampleNums.add(i);
        }

        Collections.shuffle(sampleNums);

        float[] arc = new float[numElements];

        arc[sampleNums.get(0)] = -1;
        arc[sampleNums.get(1)] = -1;
        arc[sampleNums.get(2)] = 1;

        return arc;
    }

    /**
     * Generates a column/arc with one head and one tail for the hyperarc-incidence matrix
     * @param numElements the number of elements the column should have = number of vertices in the hypergraph
     * @return the column/arc as a float[numElements]
     */
    private float[] generateRegularArc(int numElements) {


        // Randomly sample the 3 indexes for the tails/heads of the arc
        // Might not be the most efficient to be doing this every time we want
        // a new arc, so might change later.
        ArrayList<Integer> sampleNums = new ArrayList<>();

        for(int i = 0; i < numElements; i++) {
            sampleNums.add(i);
        }

        Collections.shuffle(sampleNums);

        float[] arc = new float[numElements];

        arc[sampleNums.get(0)] = 1;
        arc[sampleNums.get(1)] = -1;

        return arc;
    }

    private boolean duplicateColumn(float[][] matrix, float[] column, int numCols, int numOtherCols) {

        // For each other already existing column...
        for(int i = 0; i < numOtherCols; i++) {

            // If the column is the same as the new one, return true
            if(Arrays.compare(matrix[i], column) == 0) {
                return true;
            }
        }

        return false;
    }
}