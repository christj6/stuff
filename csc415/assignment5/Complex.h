//  Complex.h
//
//  Header file for Complex.cpp
//  Programmed by: William Ersing and Jack Christiansen
//
//	Complex stores complex numbers
//	in two parts:
//	real, and imaginary (imag)


// 	This class has the following public methods (member functions)
//
//-----------------------------------------------------------------------------------
//
//	Complex(float, float)
//
//	Parameters:			real, float, represents the real part of a complex number, passed by value
//									imag, float, represents the imaginary part of a complex number, passed by value
//	Post-condition: The parameterized constructor allows the programmer to pre-set the 
//									Complex object
//	
//-----------------------------------------------------------------------------------
//
//	Complex operator+(Complex)
//
//	Parameters:			cx, Complex, represents a Complex object, passed by reference
//	Post-condition: adds two Complex objects and outputs a third Complex object
//	
//-----------------------------------------------------------------------------------
//
//	Complex operator+(float)
//
//	Parameters:			rl, float, represents a real number, passed by value
//	Post-condition: adds two Complex objects and outputs a third Complex object
//	
//-----------------------------------------------------------------------------------


// 	This class also has the following friend functions
//
//-----------------------------------------------------------------------------------
//
//	Complex operator+(float, Complex)
//
//	Parameters:			rl, float, represents a real number, passed by value
//									cx, Complex, represents a Complex object, passed by reference
//	Post-condition: adds two Complex objects and outputs a third Complex object
//	
//-----------------------------------------------------------------------------------
//
//	Complex operator<<(ostream, Complex)
//
//	Parameters:			out, ostream, represents an output stream, passed by reference
//									cpx, Complex, represents a Complex object, passed by reference
//	Post-condition: overloads output stream and prints to screen
//	
//-----------------------------------------------------------------------------------
//
//	Complex operator>>(istream, Complex)
//
//	Parameters:			in, istream, represents an input stream, passed by reference
//									cpx, Complex, represents a Complex object, passed by reference
//	Post-condition: overloads input stream and takes in data from user
//	
//-----------------------------------------------------------------------------------

#ifndef COMPLEX_H
#define COMPLEX_H

#include <iostream>
#include <iomanip> 
#include <sstream>
#include <string>
using namespace std;

class Complex
{

private:
    float real;
    float imag;
    
public:
    Complex (float=0, float=0);
    Complex (string);
    const Complex operator+(const Complex &);
    const Complex operator+(const float);
    friend Complex operator+(float, Complex&);

    const Complex operator*(const Complex &);

    friend ostream &operator<<(ostream &, Complex &);
    friend istream &operator>>(istream &, Complex &);
};

#endif
