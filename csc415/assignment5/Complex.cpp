//  Complex.cpp
//
//  Sample to demonstrate abstraction by overloading operators
//  Programmed by: William Ersing and Jack Christiansen


#include "Complex.h"
#include <iostream>
#include <iomanip> 
#include <sstream>
#include <string>
#include <cmath>
using namespace std;


// parameterized constructor
Complex::Complex (float left, float right)
{
    real = left;
    imag = right;
}

Complex::Complex (string data)
{
    istringstream iss(data);
    iss >> real;
    iss >> imag;
}

const Complex Complex::operator+(const Complex &cx)
{
	//cout << "operator+(const Complex &cx)" << endl;

    return Complex(real + cx.real, imag + cx.imag);
}

const Complex Complex::operator*(const Complex &cx)
{
	//cout << "operator*(const Complex &cx)" << endl;
	float a = real;
	float b = imag;
	float c = cx.real;
	float d = cx.imag;

    return Complex((a*c - b*d), (b*c - a*d));
}

const Complex Complex::operator+(const float rl)
{
	//cout << "operator+(const float rl)" << endl;

    return Complex(real + rl, imag);
}

Complex operator+(float rl, Complex &cx)
{
    return Complex(cx.real + rl, cx.imag);
}

// Overload Input stream
istream &operator>>(istream &in, Complex &cpx)
{
		in >> cpx.real >> cpx.imag;
		return in;
}

// Overload Output stream
ostream &operator<<(ostream &out, Complex &cpx)
{
		out << setw(2) << fixed << setprecision(1);
		if(cpx.imag == 0)
		{
			return out << cpx.real << "\t\t";
		}
		else{
			if(cpx.real == 0)
			{
					return out << cpx.imag << "i\t\t";
			}
			else{
				if(cpx.imag < 0)
				{
					return out << cpx.real << " - " << ((-1)*cpx.imag) << "i\t";
				}
				else{
					return out << cpx.real << " + " << cpx.imag << "i\t";
				}
			}
		}
}
