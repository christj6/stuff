
import java.util.*;
import java.text.DecimalFormat;
@SuppressWarnings("unchecked")
/**
 * 
 * @author Jack Christiansen
 */
public class Matrix
{
    private int rows;
    private int columns;
    private float[][] matrix;
    
    public Matrix(int row, int col)
    {
        rows = row;
        columns = col;
        matrix = new float[rows][columns];
    }
    
    /**
     * 
     * @param row
     * @param col
     * @param values 
     */
    public Matrix(int row, int col, String values)
    {
        rows = row;
        columns = col;
        matrix = new float[rows][columns];
        
        Scanner scan = new Scanner(values);
        scan.useDelimiter(" ");
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                matrix[i][j] = scan.nextFloat();
            }
        }
    }
    
    public float getValue(int row, int col)
    {
        return matrix[row][col];
    }
    
    public int getRowDim()
    {
        return rows;
    }
    
    public int getColumnDim()
    {
        return columns;
    }
    
    /**
     * 
     * @param firstRow
     * @param secondRow
     * @param scalar 
     */
    public void rowOp1(int firstRow, int secondRow, float scalar)
    {
        float[] temp = new float[rows];
        for (int i = 0; i < rows; i++)
        {
            temp[i] = scalar*matrix[firstRow][i];
            matrix[secondRow][i] += temp[i];
        }
    }
    
    /**
     * 
     * @param firstRow
     * @param secondRow 
     */
    public void rowOp2(int firstRow, int secondRow)
    {
        float[] temp = new float[rows];
        for (int i = 0; i < rows; i++)
        {
            temp[i] = matrix[firstRow][i];
            matrix[firstRow][i] = matrix[secondRow][i];
            matrix[secondRow][i] = temp[i];
        }
    }
    
    /**
     * 
     * @param row
     * @param scalar 
     */
    public void rowOp3(int row, float scalar)
    {
        for (int i = 0; i < rows; i++)
        {
            matrix[row][i] *= scalar;
        }
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public Matrix add(Matrix other)
    {
        String values = "";
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                values += (getValue(i, j) + other.getValue(i, j)) + " ";
            }
        }
        Matrix result = new Matrix(getRowDim(), getColumnDim(), values);
        return result;
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public Matrix subtract(Matrix other)
    {
        String values = "";
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                values += (getValue(i, j) - other.getValue(i, j)) + " ";
            }
        }
        Matrix result = new Matrix(getRowDim(), getColumnDim(), values);
        return result;
    }
    
    /**
     * 
     * @param scalar
     * @return 
     */
    public Matrix scalarMult(float scalar)
    {
        String values = "";
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                values += (getValue(i, j)*scalar) + " ";
            }
        }
        Matrix result = new Matrix(getRowDim(), getColumnDim(), values);
        return result;
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public Matrix matrixMult(Matrix other)
    {
        if (getColumnDim() != other.getRowDim())
        {
            System.out.println("Error, rows != columns.");
            return null;
        }
        else
        {
            String values = "";
            for (int i = 0; i < other.getRowDim(); i++)
            {
                for (int j = 0; j < getColumnDim(); j++)
                {
                    float entry = 0;
                    
                    for (int k = 0; k < getColumnDim(); k++)
                    {
                        entry += getValue(i, k)*other.getValue(k, j);
                    }
                    
                    values += entry + " ";
                }
            }
            Matrix result = new Matrix(getRowDim(), other.getRowDim(), values);
            return result;
        }
    }
    
    /**
     * 
     * @return 
     */
    public void rowReduce()
    {
        /*
        for (int i = 1; i < rows; i++)
        {
            if (matrix[i][0] != 0)
            {
                float scalar = (-1*matrix[i][0])/(matrix[0][0]);
                rowOp1(0, i, scalar);
            }
        }
        
        for (int i = 2; i < rows; i++)
        {
            if (matrix[i][1] != 0)
            {
                float scalar = (-1*matrix[i][1])/(matrix[1][1]);
                rowOp1(1, i, scalar);
            }
        }
        */
        
        for (int current = 0; current < rows; current++)
        {
            for (int i = current+1; i < rows; i++)
            {
                if (matrix[i][current] != 0 && matrix[current][current] != 0)
                {
                    float scalar = (-1*matrix[i][current])/(matrix[current][current]);
                    rowOp1(current, i, scalar);
                }
                else if (matrix[current][current] == 0)
                {
                    rowOp2(current, i%rows); /** Swaps rows so that the i==j entry is nonzero. */
                }
            }
        }
        
    }
    
    /**
     * Way faster way of finding the determinant. It row-reduces the matrix,
     * takes the product of the diagonal entries, and then multiplies that product
     * by -1 to the power of however many swaps (rowop2) were done.
     * @return 
     */
    public float experimentalFindDet()
    {
        float det = 1;
        int swaps = 0;
        
        for (int current = 0; current < rows; current++)
        {
            for (int i = current+1; i < rows; i++)
            {
                if (matrix[i][current] != 0 && matrix[current][current] != 0)
                {
                    float scalar = (-1*matrix[i][current])/(matrix[current][current]);
                    rowOp1(current, i, scalar);
                }
                else if (matrix[current][current] == 0)
                {
                    rowOp2(current, i%rows);
                    swaps++;
                }
            }
        }
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                if (i == j)
                {
                    det *= matrix[i][j];
                }
            }
        }
        det *= Math.pow(-1, swaps);
        
        return det;
    }
    
    /**
     * Returns the determinant of a square matrix.
     * Uses cofactor expansion.
     * This method is recursive, so using it for matrices of dimensions greater
     * than 8x8 is ill-advised.
     * @return 
     */
    public float findDet()
    {
        if (rows != columns)
        {
            System.out.println("Sorry, findDet() works only on square matrices.");
            return 0;
        }
        else if (rows > 9)
        {
            System.out.println("Dimensions are too big, the program will take forever if you do that. ");
            return 0;
        }
        else
        {
            float det = 0;
            if (rows < 2 && columns < 2)
            {
                det = matrix[0][0];
            }
            else if (rows == 2 && columns == 2)
            {
                det = matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
            }
            else
            {
                int i = 0;
                for (int j = 0; j < columns; j++)
                {
                    det += (matrix[i][j]*Math.pow(-1, (i + j))*subMatrix(i, j).findDet());
                }
            }
            return det;
        }
    }
    
    /**
     * Returns the submatrix of a matrix formed by eliminating the ith row and jth column.
     * @param row
     * @param col
     * @return 
     */
    public Matrix subMatrix(int row, int col)
    {
        String values = "";

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                if (i != row && j != col)
                {
                    values += matrix[i][j] + " ";
                }
            }
        }
        
        Matrix sub = new Matrix(rows-1, columns-1, values);
        return sub;
    }
    
    public Matrix identity(int dimension, float entry)
    {
        /**
         * Creates nxn identity matrix
         */
        String values = "";
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if (i==j)
                {
                    values += entry + " ";
                }
                else
                {
                    values += entry + " ";
                }
            }
        }
        Matrix identity = new Matrix(getRowDim(), getColumnDim(), values);
        return identity;
    }
    
    
    public String toString()
    {
        String string = "";
        DecimalFormat fmt = new DecimalFormat("0.##");
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                string += fmt.format(matrix[i][j]) + "\t";
            }
            string += "\n";
        }
        
        return string;
    }
}