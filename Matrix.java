import java.util.ArrayList;

//from https://introcs.cs.princeton.edu/java/22library/Matrix.java.html

public class Matrix {
	
	/*
	 public static void main(String args[]){
		double[] a ={1,2,3,4};
		double[] b ={1,2,3,4};
		
		System.out.println(Matrix.dotProduct(a, b));
		
	}
	
	// return c = a + b
    public static Double[][] add(Double[][] a, ArrayList<double[]> b) {
        int m = a.length;
        int n = a[0].length;
        Double[][] c = new Double[m][n];
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                c[i][j] = a[i][j] + b.get(i)[j];
            }
        }
        return c;
    }

    // return c = a - b
    public static Double[][] subtract(Double[][] a, ArrayList<double[]> b) {
        int m = a.length;
        int n = a[0].length;
        Double[][] c = new Double[m][n];
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                c[i][j] = a[i][j] - b.get(i)[j];
            }
        }
        return c;
    }
    */

    // return c = a * b
    public static ArrayList<double[]> multiply(double[][] a, ArrayList<double[]> b) {
        int n1 = a[0].length;
        
        //bc b is transposed
        int m2 = b.get(0).length;
        int n2 = b.size();
        
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        ArrayList<double[]> c = new ArrayList<double[]>(n2);
       
        for (int col = 0; col < n2; col++){
        	double[] bCol=b.get(col);
        	double[] temp={
	        			dotProduct(a[0], bCol),
	        			dotProduct(a[1], bCol),
	        			dotProduct(a[2], bCol),
	        			dotProduct(a[3], bCol),
	        			dotProduct(a[4], bCol),
	        			dotProduct(a[5], bCol)
	        			};
        	c.add(temp);
        }
        return c;
    }
    
    public static double dotProduct(double[] a, double[] b){
    	if(a.length!=b.length){
    		throw new RuntimeException("Illegal dotProduct dimensions.");
    	}
    	double total=0;
    	for(int i=0;i<a.length;i++){
    		total+=a[i]*b[i];
    	}
    	return total;
    }
    
}
