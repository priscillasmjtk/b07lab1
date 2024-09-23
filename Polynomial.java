public class Polynomial {
	
	private double[] coefficients;
	
	public Polynomial() {
		this.coefficients = new double[] {0};
	}
	
	public Polynomial(double[] coefficients) {
		this.coefficients = coefficients;
	}
	
	public Polynomial add(Polynomial other) {
		double[] result = new double[coefficients.length];
		
		for (int i = 0; i < coefficients.length; i++) {
			result[i] = this.coefficients[i] + other.coefficients[i];
		}
		
		return new Polynomial(result);
	}
	
	public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }

        return result;  
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0; 
    }	
	
}