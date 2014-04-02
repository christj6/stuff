
#include <boost/lambda/lambda.hpp>
#include <stdio.h>
#include <math.h>
#include <iostream>
#include <iterator>
#include <algorithm>

#include <sndfile.h>
//#include "portaudio.h"

//#define SAMPLE_RATE (44100)
//static paTestData data;

// to use Boost: g++ -Wall -I /C:/cygwin64/lib/boost_1_55_0 example.cpp -o example
// compile using: ./example
// disregard that -- I installed Boost just fine. Compile it like any other C++ program: g++ example.cpp -o example.o

// for portaudio, try: C:/Users/Jack/Downloads/portaudio/include
// g++ -Wall -I C:/Users/Jack/Downloads/portaudio/include example.cpp -o example.o
// ./example.o
// as said here: http://stackoverflow.com/questions/6652702/compiling-a-c-program

//  to install Boost, if you're using Cygwin: download the 99 MB Boost zip, extract it, navigate to it,
//     cd boost
//     ./bootstrap.sh
//     ./b2
//     ./b2 install


using namespace std;
using namespace boost::lambda;

int main (void)
{  
	/*
    typedef istream_iterator<int> in;

    std::for_each(in(cin), in(), cout << (_1 * 3) << " " );
    */
    
}
