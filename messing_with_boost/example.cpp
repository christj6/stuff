
#include <boost/lambda/lambda.hpp>
#include <iostream>
#include <iterator>
#include <algorithm>

// to use Boost: g++ -Wall -I /C:/cygwin64/lib/boost_1_55_0 example.cpp -o example
// compile using: ./example

// Has the user type in an integer and multiplies it by 3.


using namespace std;
using namespace boost::lambda;

int main (void)
{  
	
    typedef istream_iterator<int> in;

    std::for_each(in(cin), in(), cout << (_1 * 3) << " " );
}
