// Matrix.cpp

#include "Matrix.h"
#include "Complex.h"
#include <iostream>
#include <iomanip> 
#include <sstream>
#include <string>
using namespace std;

//Constructor, default values of ROW_DEF and COL_DEF are passed in
Matrix::Matrix(int length, int width)
{
    if (length >= 1)
    {
        rows = length;
    } else {
        rows = ROW_DEF;
    }
    
    if (width >= 1)
    {
        columns = width;
    } else {
        columns = COL_DEF;
    }

    array = new Complex* [rows]; //Points "**array" to an array of "rows" pointers to Complex objects.

    for (int i = 0; i < rows; i++)
    {
        array[i] = new Complex[columns]; //For each row, a pointer points to a column-wide array of Complex objects.
    }
}

//Destructor
Matrix::~Matrix()
{
    for(int i = 0; i < rows; i++) 
    {
        delete array[i];
    }
    delete array;
}

//Copy constructor
Matrix::Matrix (const Matrix& mat)
{
    rows = mat.rows;
    columns = mat.columns;
    
    array = new Complex*[rows];

    for (int i = 0; i < rows; i++)
    {
        array[i] = new Complex[columns];
        for (int j = 0; j < columns; j++)
        {
            array[i][j] = mat.array[i][j];
        }
    }
}

//Addition operator overload for 2 matrices of equal dimensions
const Matrix Matrix::operator+(const Matrix &mat)
{
    Matrix result(rows, columns);

    if ((rows == mat.rows) && (columns == mat.columns))
    {
        result.rows = rows;
        result.columns = columns;

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                result.array[i][j] = array[i][j] + mat.array[i][j];    
            }  
        }
        
    }
    else
    {
        //dimensions do not match, matrices cannot be added.
        cout << "Dimensions of matrices not equal. Invalid operation." << endl;
    }
    return result;
}

// Adds a single complex value to every entry in a matrix
const Matrix Matrix::operator+(const Complex &comp)
{
    Matrix result(rows, columns);

    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < columns; j++)
        {
            result.array[i][j] = array[i][j] + comp;    
        }  
    }

    return result;
}

// Performs m2 = value + m1
Matrix operator+(Complex comp, Matrix &mat)
{
    Matrix result(mat.rows, mat.columns);

    for (int i = 0; i < mat.rows; i++)
    {
        for (int j = 0; j < mat.columns; j++)
        {
            result.array[i][j] = mat.array[i][j] + comp;    
        }  
    }

    return result;
}

// 
const Matrix Matrix::operator*(const Matrix &mat)
{
    Matrix result(rows, mat.columns);

    if (columns == mat.rows)
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < mat.columns; j++)
            {
                result.array[i][j] = 0;

                for (int k = 0; k < columns; k++)
                {
                    Complex product;
                    product = (array[i][k])*(mat.array[k][j]);
                    result.array[i][j] = product + result.array[i][j];
                }   
            }  
        }
        
    }
    else
    {
        //dimensions do not match, matrices cannot be multiplied.
        cout << "m1's # of columns != # of m2's rows. Invalid operation." << endl;
    }

    return result;
}

// Performs m2 = m1 * value
const Matrix Matrix::operator*(const Complex &comp)
{
    Matrix result(rows, columns);

    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < columns; j++)
        {
            result.array[i][j] = array[i][j] * comp;    
        }  
    }

    return result;
}

// Performs m2 = value * m1
Matrix operator*(Complex comp, Matrix &mat)
{
    Matrix result(mat.rows, mat.columns);

    for (int i = 0; i < mat.rows; i++)
    {
        for (int j = 0; j < mat.columns; j++)
        {
            result.array[i][j] = mat.array[i][j] * comp;    
        }  
    }

    return result;
}

// Assignment operator overload
Matrix& Matrix::operator=(const Matrix &mat)
{
    if (this == &mat)
    {
        return *this;
    }
    else
    {
        for(int i = 0; i < rows; i++) 
        {
            delete array[i];
        }
        delete array;

        rows = mat.rows;
        columns = mat.columns;

        array = new Complex*[rows];

        for (int i = 0; i < rows; i++)
        {
            array[i] = new Complex[columns];
            
            for (int j = 0; j < columns; j++)
            {
                array[i][j] = mat.array[i][j];
            }
        }

        return *this;
    }
}

// m1 = value
const Matrix Matrix::operator=(const Complex &comp)
{
    Matrix result(rows, columns);
    
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < columns; j++)
        {
            result.array[i][j] = comp;
        }
    }
    
    return result;
    
}

// Overload Input stream
istream &operator>>(istream &in, Matrix &mat)
{

    //Scan in # of rows and columns
    in >> mat.rows;
    in >> mat.columns;

    Matrix temp(mat.rows, mat.columns);
    
    for (int i = 0; i < mat.rows; i++)
    {
        for (int j = 0; j < mat.columns; j++)
        {
            if (in.rdbuf()->in_avail() != 0)
            {
                in >> temp.array[i][j];
            } else {
                temp.array[i][j] = 0;
            }
        }
    }

    mat = temp;

    return in;
}


// Overload Output stream
ostream &operator<<(ostream &out, Matrix &mat)
{
    cout << endl;
    
    for (int i = 0; i < mat.rows; i++)
    {
        for (int j = 0; j < mat.columns; j++)
        {
            out << mat.array[i][j] << " ";    
        }  
        out << endl;
    }
    return out;
}
