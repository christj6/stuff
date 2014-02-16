//  Matrix.h
//
//  Header file for Matrix.cpp
//  Programmed by: Jack Christiansen
//


//  This class has the following public methods (member functions)
//
//-----------------------------------------------------------------------------------
//
//  Matrix(int = ROW_DEF, int = COL_DEF)
//
//  Parameters:         rows (int) and columns (int) represent the dimensions of the matrix.
//                      arrary (pointer to an array of pointers to arrays of Complex objects) manages the data in the matrix.
//  Post-condition: The parameterized constructor allows the programmer to pre-set the
//                                  Matrix object
//
//-----------------------------------------------------------------------------------
//
//  Matrix(___)
//
//  Parameters:         Object of type Matrix, passed by reference.
//  Post-condition:     A new matrix is constructed using the attributes of the input matrix.
//
//-----------------------------------------------------------------------------------
//
//  ~Matrix()
//
//  Parameters:         none
//  Post-condition:     Memory is de-allocated.
//
//-----------------------------------------------------------------------------------
//
//  Matrix operator+(Matrix)
//
//  Parameters:         mat, Matrix, represents a Matrix object, passed by reference
//  Post-condition: adds two Matrix objects and returns a third Matrix object,
//  provided the matrices are both of the same dimension.
//  Otherwise, an error message is outputted.
//
//-----------------------------------------------------------------------------------
//
//  Matrix operator+(Complex)
//
//  Parameters:         comp, Complex, represents a Complex object, passed by reference
//  Post-condition: Adds a Complex object to every entry in a Matrix object.
//
//-----------------------------------------------------------------------------------
//
//  Matrix operator*(Matrix)
//
//  Parameters:         mat, Matrix, represents a Matrix object, passed by reference
//  Post-condition: Multiplies two Matrix objects to return a third Matrix object,
//  provided matrix 1's # of columns matches matrix 2's # of rows.
//  Otherwise, an error message is outputted.
//
//-----------------------------------------------------------------------------------
//
//  Matrix operator*(Complex)
//
//  Parameters:         comp, Complex, represents a Complex object, passed by reference
//  Post-condition: Multiplies every entry in a Matrix object by a Complex object.
//
//-----------------------------------------------------------------------------------
//
//  Matrix operator=(Matrix)
//
//  Parameters:         mat, Matrix, represents a Matrix object, passed by reference
//  Post-condition: Assigns a matrix to another matrix object.
//
//-----------------------------------------------------------------------------------
//
//  Matrix operator=(Complex)
//
//  Parameters:         comp, Complex, represents a Complex object, passed by reference
//  Post-condition:     Assigns a Complex value to every cell in a matrix object.
//
//-----------------------------------------------------------------------------------
//
//  This class also has the following friend functions
//
//-----------------------------------------------------------------------------------
//
//  Matrix operator+(Complex, Matrix)
//
//  Parameters:         comp, Complex, represents a Complex object, passed by reference
//                      mat, Matrix, represents a Matrix object, passed by reference
//  Post-condition: Adds a Complex object to every entry in a Matrix object.
//  (implemented so that (comp + matrix) and (matrix + comp) have same results)
//
//-----------------------------------------------------------------------------------
//
//  Matrix operator*(Complex, Matrix)
//
//  Parameters:         comp, Complex, represents a Complex object, passed by reference
//  Post-condition: Performs scalar multiplication on a Matrix object using a Complex object.
//  (implemented so that (comp * matrix) and (matrix * comp) have same results)
//
//-----------------------------------------------------------------------------------
//
//  Matrix operator<<(ostream, Matrix)
//
//  Parameters:         out, ostream, represents an output stream, passed by reference
//                                  mat, Matrix, represents a Matrix object, passed by reference
//  Post-condition: overloads output stream and prints to screen
//
//-----------------------------------------------------------------------------------
//
//  Matrix operator>>(istream, Matrix)
//
//  Parameters:         in, istream, represents an input stream, passed by reference
//                                  mat, Matrix, represents a Matrix object, passed by reference
//  Post-condition: overloads input stream and takes in data from user/filename
//
//-----------------------------------------------------------------------------------

#ifndef MATRIX_H
#define MATRIX_H

#include "Complex.h"
#include <iostream>
#include <iomanip> 
#include <sstream>
#include <string>
using namespace std;

#define ROW_DEF 3 //default number of rows
#define COL_DEF 4 //default number of columns

class Matrix
{
    private:
        int rows;
        int columns;
        Complex **array;
        
    public:
        Matrix(int = ROW_DEF, int = COL_DEF); //constructor
        Matrix(const Matrix &); // copy constructor
        ~Matrix(); //destructor

        //Addition overload
        const Matrix operator+(const Matrix &); //m3 = m1 + m2
        const Matrix operator+(const Complex &); //m3 = m1 + value
        friend Matrix operator+(Complex, Matrix&); //m3 = value + m1

        //Multiplication overload
        const Matrix operator*(const Matrix &); //m3 = m1 * m2
        const Matrix operator*(const Complex &); //m3 = m1 * value
        friend Matrix operator*(Complex, Matrix&); //m3 = value * m1

        //Assignment overload
        Matrix& operator= (const Matrix&); // m3 = m2
        const Matrix operator=(const Complex &); //m3 = comp

        //Input and output
        friend ostream &operator<<(ostream &, Matrix &); // cout << m2
        friend istream &operator>>(istream &, Matrix &); // in >> m2



};

#endif
