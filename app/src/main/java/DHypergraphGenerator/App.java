/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package DHypergraphGenerator;

import org.ejml.simple.SimpleMatrix;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        SimpleMatrix test = new SimpleMatrix(2,2);
        test.print();
    }
}