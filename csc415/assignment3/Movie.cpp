// Movie.cpp

//
// programmed by Jack Christiansen
// CSC 415
// last modified 10/8/2013

#include <iostream>
#include <string>
using namespace std;

#include "Movie.h"

Movie::Movie (string id, string name, string yearOfRelease, double rate1, double rate2, double rate3, double userRate)
{
    movieID = id;
    title = name;
    year = yearOfRelease;
    ratings[0] = rate1;
    ratings[1] = rate2;
    ratings[2] = rate3;
    userRating = userRate;
}

// blank constructor so the MovieList class can instantiate an array of Movie objects.
Movie::Movie ()
{
    
}

string Movie::getID ()
{
    return movieID;  
}

void Movie::setID (string id)
{
    movieID = id;   
}

string Movie::getTitle ()
{
    return title;  
}

void Movie::setTitle (string name)
{
    title = name;   
}

string Movie::getYear ()
{
    return year;  
}

void Movie::setYear (string yearOfRelease)
{
    // I'm assuming this software will be used between the years 1000 and 9999. I might be wrong.
    if (yearOfRelease.length() == 4)
    {
        year = yearOfRelease;
    }
    else
    {
        //Invalid year selected
    }
}

// The integer inputted corresponds with the critic of the rating that is output.
// 0 is Metacritic, 1 is Rotten Tomatoes, 2 is VideoHub, and 3 is the user rating.
double Movie::getRating (int whichOne)
{
    if (whichOne == 0)
    {
        return ratings[0];
    }
    else if (whichOne == 1)
    {
        return ratings[1];
    }
    else if (whichOne == 2)
    {
        return ratings[2];
    }
    else if (whichOne == 3)
    {
        return userRating;
    }
    else
    {
        //nonexistent critic selected, error.
        return 0;
    }
}

// int whichOne serves same purpose as in getRating, only this time,
// whichOne determines which critic's rating is set, not returned.
// For the rating: 0 refers to hating a film, 4 refers to loving a film.
// Anything outside those boundaries is an invalid rating.
void Movie::setRating (int whichOne, double rate)
{
    if (rate >= 0.0 && rate <= 4.0)
    {
        if (whichOne == 0)
        {
            ratings[0] = rate;
        }
        else if (whichOne == 1)
        {
            ratings[1] = rate;
        }
        else if (whichOne == 2)
        {
            ratings[2] = rate;
        }
        else if (whichOne == 3)
        {
            userRating = rate;
        }   
        else
        {
            //invalid critic selected: do nothing
        }
    }
    else
    {
        //invalid rating submitted: do nothing
    }
}

