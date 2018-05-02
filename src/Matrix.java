public class Matrix {
    int[][] matrix;
    int n;
    int m;
    public Matrix(int n){
        this.n = n;
        matrix = new int[n][n];
    }

    public Matrix(int n, int m){
        this.n = n;
        this.m = m;
        matrix = new int[n][m];
    }
    public int fibonacci(int n){
        if(n < 0){
            return -1;
        }else if(n <= 1){
            return 1;
        }else{
            return fibonacci(n-1) + fibonacci(n - 2);
        }
    }

    public int get(int i, int j) throws Exception{
        if(i >= n){
            throw new Exception(i+" is not in range of matrix");
        }
        if(j >= m){
            throw new Exception(j+" is not in range of matrix");
        }
        return matrix[i][j];
    }
    public void setFibonaccisFirstRowAndColumn(){
        for(int j = 0; j < n; ++j){
            matrix[0][j] = fibonacci(j);
        }

        for(int i = 0; i < n; ++i){
            matrix[i][0] = fibonacci(i);
        }
    }
    public void set(int i, int j, int val) throws Exception{
        if(i >= n){
            throw new Exception(i+" is not in range of matrix");
        }
        if(j >= m){
            throw new Exception(j+" is not in range of matrix");
        }
        matrix[i][j] = val;
    }
    public void maybeSegfault(){
        System.out.println("numbner = "+ matrix[2][2]);
    }

    public void fillRestOfMatrix(){
        for(int i = 1; i < n; ++i){
            for(int j = 1; j < n; j++){
                matrix[i][j] = matrix[i-1][j-1] + matrix[i][j-1] + matrix[i-1][j];
            }
        }
    }

    public void printFirstDiagonal(){
        for(int i = 0; i < n; ++i){
            System.out.println(matrix[i][i]);
        }
    }

    public int computeDiagonalSum(int startx, int starty){
        int length = n - Math.max(startx, starty);
        int sum = 0;
        for(int i = 0; i < length ; ++i){
            sum += matrix[startx + i][starty + i];
        }
        return sum;
    }

    public void printMatrix(){
        for(int i = 0; i < n; ++i){
            for(int j = 0; j < m; ++j){
                System.out.printf("%7d", matrix[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args){
        Matrix m = new Matrix(5);
        m.maybeSegfault();
        m.setFibonaccisFirstRowAndColumn();
        m.fillRestOfMatrix();
        m.maybeSegfault();
        m.printMatrix();
        int x = m.computeDiagonalSum(2,3);
        System.out.println(x);
        m.printFirstDiagonal();
    }

}
