import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Polynomial {
	
	double[] coefficients;
	int[] exponents;
	
	public Polynomial() {
		this.coefficients = new double[] {0};
		this.exponents = new int[] {0};
	}
	
	public Polynomial(double[] coefficients, int[] exponents) {
		this.coefficients = coefficients;
		this.exponents = exponents;
	}
	
	public Polynomial(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        
        String polynomialStr = scanner.nextLine(); 
        scanner.close(); 

        polynomialStr = polynomialStr.replace("-", "+-");
        
        String[] terms = polynomialStr.split("\\+");
        
        coefficients = new double[terms.length];
        exponents = new int[terms.length];

        for (int i = 0; i < terms.length; i++) {
            String term = terms[i];
            
            if (term.contains("x")) {
                String[] parts = term.split("x"); 
                coefficients[i] = Double.parseDouble(parts[0]); 
                exponents[i] = Integer.parseInt(parts[1]);      
            } else {
                coefficients[i] = Double.parseDouble(term); 
                exponents[i] = 1;  
            }
        }
    }
	
	public Polynomial add(Polynomial other) {
		int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
		double[] resultCoefs = new double[maxLength];
		int[] resultExps = new int[maxLength];
		
		int i = 0, j = 0, k = 0;

		while (i < this.coefficients.length && j < other.coefficients.length) {
			// If the exponents are the same, add the coefficients
			if (this.exponents[i] == other.exponents[j]) {
				double sum = this.coefficients[i] + other.coefficients[j];
				// If the sum is not zero, we add it to the result
				if (sum != 0) { 
					resultCoefs[k] = sum;
					resultExps[k] = this.exponents[i];
					k++;
				}
				i++;
				j++;
			// If the exponent of 'this' is greater, add it to the result
			} else if (this.exponents[i] > other.exponents[j]) { 
				resultCoefs[k] = this.coefficients[i];
				resultExps[k] = this.exponents[i];
				i++;
				k++;
			// If the exponent of 'other' is greater, then add it to the result
			} else {
				resultCoefs[k] = other.coefficients[j];
				resultExps[k] = other.exponents[j];
				j++;
				k++;
			}
		}
		
		// Add leftover terms from 'this'
		while (i < this.coefficients.length) {
			resultCoefs[k] = this.coefficients[i];
			resultExps[k] = this.exponents[i];
			i++;
			k++;
		}

		// Add leftover terms from 'other'
		while (j < other.coefficients.length) {
			resultCoefs[k] = other.coefficients[j];
			resultExps[k] = other.exponents[j];
			j++;
			k++;
		}

		// Create the final result arrays with the correct size
		double[] finalCoefs = Arrays.copyOf(resultCoefs, k);
		int[] finalExps = Arrays.copyOf(resultExps, k);

		return new Polynomial(finalCoefs, finalExps);
}

	
	public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }

        return result;  
    }
	
    public boolean hasRoot(double x) {
        return evaluate(x) == 0; 
    }	
    
    public Polynomial multiply(Polynomial other) {
		int maxSize = this.coefficients.length * other.coefficients.length;
		double[] resultCoefs = new double[maxSize];
		int[] resultExps = new int[maxSize];
		
		int index = 0;
	
		// Multiply each coefficient of the first polynomial with each coefficient of the second polynomial. 
		for (int i = 0; i < this.coefficients.length; i++) {
			for (int j = 0; j < other.coefficients.length; j++) {
				double newCoef = this.coefficients[i] * other.coefficients[j];
				int newExp = this.exponents[i] + other.exponents[j];
				
				// 
				resultCoefs[index] = newCoef;
				resultExps[index] = newExp;
				index++;
			}
		}
	
		// Combine the terms that have the same exponents
		for (int i = 0; i < resultCoefs.length; i++) {
			for (int j = i + 1; j < resultCoefs.length; j++) {
				if (resultExps[i] == resultExps[j]) {
					resultCoefs[i] += resultCoefs[j];
					resultCoefs[j] = 0;
				}
			}
		}
	
		// Count the number of non-zero terms
		int nonZeroCount = 0;
		for (int i = 0; i < resultCoefs.length; i++) {
			if (resultCoefs[i] != 0) {
				nonZeroCount++;
			}
		}
	
		// Combine each coefficient with its corresponding exponent
		double[] finalCoefs = new double[nonZeroCount];
		int[] finalExps = new int[nonZeroCount];
		int lastIndex = 0;
	
		// Add the non-zero terms to the final arrays
		for (int i = 0; i < resultCoefs.length; i++) {
			if (resultCoefs[i] != 0) {
				finalCoefs[lastIndex] = resultCoefs[i];
				finalExps[lastIndex] = resultExps[i];
				lastIndex++;
			}
		}
	
		return new Polynomial(finalCoefs, finalExps);
	}
	
    
    public void saveToFile(String fileName) {
		StringBuilder polynomialString = new StringBuilder();
		
		for (int i = 0; i < coefficients.length; i++) {
			polynomialString.append(coefficients[i]);
			if (exponents[i] != 0) {
				polynomialString.append("x").append(exponents[i]);
			}
			if (i < coefficients.length - 1) {
				if (coefficients[i + 1] >= 0) {
					polynomialString.append("+");
				}
			}
		}

		try (FileWriter writer = new FileWriter(fileName)) {
			writer.write(polynomialString.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}