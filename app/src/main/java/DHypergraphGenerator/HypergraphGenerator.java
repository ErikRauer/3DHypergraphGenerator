package DHypergraphGenerator;

import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

public class HypergraphGenerator {


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

        for(int i = 0; i < numGraphs; i++) {

            SimpleMatrix currentMatrix = createMatrix(numVertices, numArcs);

            matrixList.add(currentMatrix);
        }

        return(matrixList);
    }

    private SimpleMatrix createMatrix(int numRows, int numCols) {
        SimpleMatrix endMatrix = new SimpleMatrix(numRows, numCols);
        return endMatrix;
    }

    
}