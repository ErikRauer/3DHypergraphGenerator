package DHypergraphGenerator;

import java.util.ArrayList;
import java.util.Collections;

import org.ejml.simple.SimpleMatrix;

public class HypergraphGenerator {

    // (Approximate) Proportion of arcs that should have two heads and two tails respectively
    // NOTE: proportion of hyperarcs = TWO_HEAD_PROP + TWO_TAIL_PROP
    // TODO: Make these parameters in the generate() method
    final static double TWO_HEAD_PROP = 0.333333;
    final static double TWO_TAIL_PROP = 0.333333;

    /**
     * Class for the generation of hypergraph vertex-hyperarc incidence matrix
     */

    
     /**
      * Method that generates the Matrices
      * @param numGraphs number of graphs to create, number of matrices to return
      * @param numVertices number of vertices the graphs should have = number of rows in the matrices
      * @param numArcs number of arcs the graphs should have = number of columns in the matrices
      */
    public ArrayList<SimpleMatrix> generate(int numGraphs, int numVertices, int numArcs) {

        ArrayList<SimpleMatrix> matrixList = new ArrayList<SimpleMatrix>();

        // Create numGraphs matrices and add them to matrixList
        for(int i = 0; i < numGraphs; i++) {

            SimpleMatrix currentMatrix = createMatrix(numVertices, numArcs);

            matrixList.add(currentMatrix);
        }

        return(matrixList);
    }

    /**
     * Randomly generate a hyperarc-incidence matrix
     * @param numRows number of rows = number of vertices
     * @param numCols number of columns = number of arcs
     * @return the hyperarc-incidence matrix as a SimpleMatrix 
     */
    private SimpleMatrix createMatrix(int numRows, int numCols) {

        // Call generateColumn num Cols times and add them to a float[]
        float[][] matrix = new float[numCols][numRows];

        for(int i = 0; i < numCols; i++) {
            matrix[i] = generateColumn(numRows);
        }

        // Check that new Column is different from the rest, if not generate a new one

        SimpleMatrix endMatrix = new SimpleMatrix(matrix);
        endMatrix = endMatrix.transpose();
        return endMatrix;

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
}