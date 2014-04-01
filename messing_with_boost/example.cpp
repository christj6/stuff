
#include <boost/lambda/lambda.hpp>
#include <iostream>
#include <iterator>
#include <algorithm>

// to use Boost: g++ -Wall -I /C:/cygwin64/lib/boost_1_55_0 example.cpp -o example
// compile using: ./example

//  to install Boost, if you're using Cygwin: download the 99 MB Boost zip, extract it, navigate to it,
//     cd boost
//     ./bootstrap.sh
//     ./b2
//     ./b2 install


using namespace std;
using namespace boost::lambda;

int main (void)
{  
	
    typedef istream_iterator<int> in;

    std::for_each(in(cin), in(), cout << (_1 * 3) << " " );
}
