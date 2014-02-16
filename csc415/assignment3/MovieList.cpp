// MovieList.cpp

//
// programmed by Jack Christiansen
// CSC 415
// last modified 10/8/2013

#include <iostream>
#include <iomanip> 
#include <fstream>
#include <sstream>
#include <cmath>
#include <string>
#include <stdlib.h>
#include <stdio.h> 


using namespace std;

#include "MovieList.h"

//Constructor for MovieList. No parameters needed.
MovieList::MovieList()
{
    numberOfFilms = 0;
    
    for (int i = 0; i < 5; i++)
    {
        ratedFilmIndex[i] = -1;
    }
}

void MovieList::addMovie (string id, string name, string yearOfRelease)
{
    if (numberOfFilms < 1899)
    {
        bool found = false;
        
        for (int i = 0; i < numberOfFilms; i++)
        {
            string sub = (movies[i].getTitle()).substr(0, name.length());
            if (name.compare(sub) == 0 && !found)
            {
                found = true;
            }
        }
        
        if (!found)
        {
            movies[numberOfFilms].setID(id);
            movies[numberOfFilms].setTitle(name);
            movies[numberOfFilms].setYear(yearOfRelease);
            numberOfFilms++;
        }
        else
        {
            cout << "Film already exists in database." << endl;
        }
    }
    else
    {
        cout << "Maximum number of films exceeded." << endl;
    }
}


// Finds the critic who rates films most similarly to the user. 
void MovieList::recommend()
{
    double critic[3];
    
    // Calculates summation of [ cr(i) - ur(i) ] ^ 2, where
    // i represents the movie index, cr represents the critic rating, and ur represents the user rating.
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 5; j++)
        {
            critic[i] += pow(movies[ratedFilmIndex[j]].getRating(i) - movies[ratedFilmIndex[j]].getRating(3), 2);
        }
    }
    
    // Given the circumstances, we could assume that if x < b, then sqrt(x) < sqrt(b).
    // However, it felt safer not to skip this step, so the equation is calculated correctly.
    for (int i = 0; i < 3; i++)
    {
        critic[i] = sqrt(critic[i]);
    }
    
    int idealCritic = 0;
    
    // Finds lowest critic value through comparison
    for (int i = 0; i < 3; i++)
    {
        if (critic[i] < critic[idealCritic])
        {
            idealCritic = i;
        }
    }
    
    // Displays particular critic's films rated above 3.
    for (int i = 0; i < numberOfFilms; i++)
    {
        if (movies[i].getRating(idealCritic) > 3.0 && movies[i].getRating(idealCritic) != 0)
        {
            cout << movies[i].getTitle() << " " << movies[i].getRating(idealCritic) << endl;
        }
    }

}

// Returns true if a film is found in the database with the title the user provided.
bool MovieList::rateFilm(string search)
{
    bool found;
    found = false;
    for (int i = 0; i < numberOfFilms; i++)
    {
        string sub = (movies[i].getTitle()).substr(0, search.length());

        // If the input string matches with the current Movie title, and the
        // movie has not already been found, then it is rated.
        if (search.compare(sub) == 0 && !found)
        {
            cout << "You entered: " << movies[i].getTitle() << endl;
            found = true;
            cout << "Enter a rating for this film (from 0.0 to 4.0): " << endl;
            
            string value;
            cin >> value;
            movies[i].setRating(3, atof(value.c_str()));
            
            bool switched = false;
            for (int j = 0; j < 5; j++)
            {
                if (ratedFilmIndex[j] == -1 && !switched)
                {
                    ratedFilmIndex[j] = i;
                    switched = true;
                }
            }
            
        }
    }
    
    if (!found)
    {
        cout << "That film is not in the database. Please try again." << endl;
    }
    
    return found;
}

// User chooses file to scan. Relevant lines are scanned as Movie objects in MovieList.
void MovieList::scanFile()
{
    bool success = true;
	string inFileName;
	ifstream inStr;
	
	do
	{
		cout << "Enter name of file to read from: ";
		cin >> inFileName;
		inStr.open (inFileName.c_str());
        
		if (!inStr.fail())
		{
            success = true;
		}
		else
        {
            cerr << "Error opening file. Please try again." << endl;
            inStr.clear();
            success = false;
        }
	} while (!success);
    
    string line;
    
    //Eliminate junk at beginning of data file
    int counter = 0;
    while (counter < 13)
    {
        getline(inStr, line, ' ');
        istringstream lineScan(line);
        string junk;
        lineScan >> junk;
        counter++;
    }
    getline(inStr, line, ')');
    istringstream lineScan(line);
    string junk;
    lineScan >> junk;

    // Process data, provided the array is not yet filled.
    while (!inStr.eof() && numberOfFilms < 1899)
    {
        
        string id;
        string title;
        string year;
        double rate1;
        double rate2;
        double rate3;

        // Use comma delimiter for first entries of data, and then
        // carriage return delimiter for last item of data in each line.
        for (int i = 0; i < 5; i++)
        {
            getline(inStr, line, ',');
            if (i == 0)
            {
                id = line;
            }
            else if (i == 1)
            {
                title = line;
            }
            else if (i == 2)
            {
                year = line;
            }
            else if (i == 3)
            {
                rate1 = atof(line.c_str());
            }
            else if (i == 4)
            {
                rate2 = atof(line.c_str());
            }
        }
        getline(inStr, line, '\r');
        rate3 = atof(line.c_str());

        // Scan information into a movie object.
        movies[numberOfFilms].setID(id);
        movies[numberOfFilms].setTitle(title);
        movies[numberOfFilms].setYear(year);
        movies[numberOfFilms].setRating(0, rate1);
        movies[numberOfFilms].setRating(1, rate2);
        movies[numberOfFilms].setRating(2, rate3);
        numberOfFilms++; //Move on to next movie.
    }

    inStr.close();
}

// User chooses filename to write to. Data is written to file in a similar manner to the "movies.csv" file.
void MovieList::writeFile()
{
    string outFileName;
	ofstream outStr;
    bool success = false;
	
	do 
	{
		cout << "Enter name of file to write to: ";
		cin >> outFileName;
		outStr.open (outFileName.c_str());
        
		if (!outStr.fail())
		{
			success = true;
		}
		else
        {
            cerr << "Error opening file. Try again." << endl;
            outStr.clear();
            success = false;
        }
	} while (!success);
	
    // Write header lines
    outStr << "Critic Ratings for years 2000 to 2009 processed,,,,," << endl;
    outStr << ",,,,," << endl;
    outStr << "Film ID,Film Title,Year (NA),Metacritic,Rotten Tomatoes/4,VideoHound Score (0-4)\r";
	
    
	for (int i=0; i < numberOfFilms; i++)
	{
        outStr << movies[i].getID() << ",";
        outStr << movies[i].getTitle() << ",";
        outStr << movies[i].getYear() << ",";
        outStr << setprecision(2) << movies[i].getRating(0) << ",";
        outStr << setprecision(2) << movies[i].getRating(1) << ",";
        outStr << setprecision(2) << movies[i].getRating(2) << ",";
        outStr << setprecision(2) << movies[i].getRating(3);
		outStr << endl;
	}
	
	outStr.close();
}

