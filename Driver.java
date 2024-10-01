import java.io.*;

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println("Expected p(7)= 0.0, Actual p(7) = " + p.evaluate(7));

        double[] c1 = {2, 0, 0, 4};
        int[] e1 = {1, 2, 3, 4};
        Polynomial p1 = new Polynomial(c1, e1);
        
        double[] c2 = {1, 0, -1, -2, -4};
        int[] e2 = {1, 2, 3, 4, 0};
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial sum = p1.add(p2);
        
        System.out.println("Expected sum(2) = 26.0, Actual sum(2) = " + sum.evaluate(2));

		double[] c3 = {3, 2, 1, 0};
		int[] e3 = {1, 2, 3, 4};
		Polynomial p3 = new Polynomial(c3, e3);

		double[] c4 = {1, 2, 3, 4};
		int[] e4 = {1, 2, 3, 4};
		Polynomial p4 = new Polynomial(c4, e4);

		Polynomial multiplied = p3.multiply(p4);

		System.out.println("Expected multiplication is: 3.0x^2 + 8.0x^3 + 14.0x^4 + 20.0x^5 + 11.0x^6 + 4.0x^7");
		
		System.out.println("Actual multiplication is: " + multiplied.coefficients[0] + "x^" + multiplied.exponents[0] + " + " + multiplied.coefficients[1] + "x^" + multiplied.exponents[1] + " + " + multiplied.coefficients[2] + "x^" + multiplied.exponents[2] + " + " + multiplied.coefficients[3] + "x^" + multiplied.exponents[3] + " + " + multiplied.coefficients[4] + "x^" + multiplied.exponents[4] + " + " + multiplied.coefficients[5] + "x^" + multiplied.exponents[5]);

        if (sum.hasRoot(1)) {
            System.out.println("1 is a root of sum");
        } else {
            System.out.println("1 is not a root of sum");
        }

        sum.saveToFile("ResultPolynomial.txt");
        System.out.println("The polynomial has been saved to 'ResultPolynomial.txt'.");
        
        try {
            File file = new File("ResultPolynomial.txt");
            Polynomial test = new Polynomial(file);
            System.out.println("Polynomial extracted from file evaluated at 2 = " + test.evaluate(2));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}