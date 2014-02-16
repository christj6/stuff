// Driver.cpp

#include "Matrix.h"
#include "Complex.h"

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>

using namespace std;

int main (void)
{
    /*
    string numberString = "12 4 200 19";
    int numbers[4];
    
    istringstream iss(numberString);
    for (int i = 0; i < 4; i++)
    {
        iss >> numbers[i];   
    }
    
    for (int i = 0; i < 4; i++)
    {
        cout << numbers[i] << endl;   
    }
    */
    //"3 4 1 2 3 4 5 6 7 8 9 10 11 12"


    
    //string x = "5 6 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30";
    //string x = "4 7 1 1 1 1 2 2 2 2 3 3 3 3 4 4 4 4 5 5 5 5 6 6 6 6 7 7 7 7";
    //string y = "6 5 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30";
    //string y = "7 3 1 1 1 1 1 1 1 2 2 2 2 2 2 2 3 3 3 3 3 3 3";

    
    //string x = "5 2 -1 2 3 4 5 6 7 8 9 10";
    //string y = "2 7 -1 2 3 4 5 6 7 8 9 10 11 12 13 14";
    //Matrix m1(x); 
    //Matrix m2(y);
    //Matrix m1;
    //Matrix m2;

    //cout << "Please enter a matrix (first 2 numbers are dimensions, the rest are entries): " << endl;
    //cin >> m1;

    //cout << m1 << endl;
    //m2 = m1;
    //cout << m2 << endl;

    //Matrix m3;
    //m3 = m1*m2;

    //cout << m3;
    

    /*
    string z = "8.6 4.3";
    Complex c1(z);

    string w = "4.0 1.2";
    Complex c2(w);

    Complex c3;
    c3 = c1*c2;

    cout << c3 << endl;
    
    Matrix m1(5, 7);
    Matrix m2(2, 90);
    m2 = m1;
     */

    
    Matrix m1;
    //Matrix m2;
    ifstream inStr1a;
    //string infilename;

    //cin >> infilename;
    inStr1a.open ("m.txt");
    inStr1a >> m1;
    cout << "m.txt Matrix 1a is " << endl << m1 << endl;
    inStr1a.close();
    
    /*
    ifstream inStr2a;
    inStr2a.open ("m2x3.txt");
    inStr2a >> m2;
    cout << "m2x3.txt Matrix 1a is " << endl << m2 << endl;
    
    inStr2a.close();
    
    Matrix m3;
    
    Complex c1(1.1, 2.2);


    m3 = m1*m2;
    cout << "m1*m2 is " << endl;
    cout << m3 << endl;

    m3 = m1 + c1;
    cout << "m1 + c1 is " << endl;
    cout << m3 << endl;

    m3 = c1 + m1;
    cout << "c1 + m1 is " << endl;
    cout << m3 << endl;

    m3 = m1 * c1;
    cout << "m1 * c1 is " << endl;
    cout << m3 << endl;

    m3 = c1 * m1;
    cout << "c1 * m1 is " << endl;
    cout << m3 << endl;

    cout << "Testing copy constructor " << endl;
    Matrix m7(m2);
    cout << "Matrix 7 is " << endl << m7 << endl;
    
    // Test if Matrix 2 is impacted by the change in Matrix 7
    m7 = m7 * c1;
    
    cout << "Matrix 7 is now: " << endl;
    cout << m7 << endl;
    cout << "Matrix 2 is now: " << endl;
    cout << m2 << endl;
    */
    
    //system("pause");
}
