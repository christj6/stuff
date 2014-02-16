// Driver.cpp

//
// programmed by Jack Christiansen
// CSC 415
// last modified 10/8/2013

#include <iostream>
#include <fstream>
#include <cmath>
#include <string>

#include "MovieList.h"


using namespace std;


int main (void)
{    
    MovieList list;
    list.scanFile();
    cin.ignore();
    
    for (int i = 0; i < 2; i++)
    {
        cout << "Enter the name of a new film to add to the database." << endl;
        string name;
        getline (cin, name);
        cin.ignore();
    
        cout << "Enter the year the film was released." << endl;
        string year;
        getline (cin, year);
        cin.ignore();
    
        cout << "Enter the id." << endl;
        string id;
        getline (cin, id);
        cin.ignore();
    
        list.addMovie(id, name, year);   
    }
    
    
    int userRatings = 0;
    
    cout << "Please rate 5 films." << endl;
    string search;
    while (userRatings < 5)
    {
        cout << "Enter a title (case sensitive): " << endl;
        
        getline (cin, search);
        
        if (list.rateFilm(search))
        {
            userRatings++;
        }
        
        cin.ignore();
    }
    
    if (userRatings >= 5)
    {
        cout << "Recommendations" << endl;
        list.recommend();
    }
    
    list.writeFile();
    
    return 0;
}
