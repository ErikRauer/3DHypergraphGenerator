package DHypergraphGenerator;

import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

public class HypergraphGenerator {

    // (Approximate) Proportion of arcs that should have two heads and two tails respectively
    // NOTE: number of hyperarcs = TWO_HEAD_PROP + TWO_TAIL_PROP
    // TODO: Make these parameters in the generate() method
    final static double TWO_HEAD_PROP = 1/3;
    final static double TWO_TAIL_PROP = 1/3;

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

        SimpleMatrix endMatrix = new SimpleMatrix(numRows, numCols);
        return endMatrix;

    }

    /**
     * Generates a column/arc for the hyperarc-incidence matrix
     * @param numElements the number of elements the column should have = number of vertices in the hypergraph
     * @return the column/arc as a float[numElements]
     */
    private float[] generateColumn(int numElements) {
        float[] column = new float[numElements];

        // Decide which type of arc/hyperarc to create
        double columnTypeSeed = Math.random();
        if(columnTypeSeed < TWO_HEAD_PROP) {
            // Generate a two headed hyperarc
        } else if (columnTypeSeed < TWO_HEAD_PROP + TWO_TAIL_PROP) {
            // Generate a two tailed hyperarc
        } else {
            // Generate a regular arc
        }

        return column;
    }
    
}