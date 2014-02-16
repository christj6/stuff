// MovieList.h

//
// programmed by Jack Christiansen
// CSC 415
// last modified 10/8/2013
//------------------------------------------------------------------------------
//
// Movie movies[]: array of Movie 1900 objects.
//
//
// int numberOfFilms: Number of movies stored in the Movie object array.
//                    Incremented by one with every film scanned in or added by the user,
//                    unless the next film would exceed 1899.
//
// int ratedFilmIndex[]: Integer array with every entry initialized at -1. When a film is
//                       rated, the index of that film overrides the next ratedFilmIndex of -1, until each index is != -1.
//                       When the distance formula is computed with each critic, the 5
//                       indices in the array are used as the 5 movies to compute the final value.
//
//------------------------------------------------------------------------------
// This class has the following public methods (member functions)
//------------------------------------------------------------------------------
//
// MovieList()
//
//	Parameters:	none
//	Post-condition: numberOfFilms is initialized to 0. Each member of ratedFilmIndex is initialized to -1.
//  	Pre-condition:  none
//
//------------------------------------------------------------------------------
//
// void addMovie(string, string, string)
//
//	Parameters:	id, string, represents the film's ID number, passed by value
//              title, string, represents the film's title, passed by value
//              year, string, represents the year the film was released, passed by value
//	Post-condition: A new Movie object is added at the bottom of the Movies array.
//  	Pre-condition: numberOfFilms must be less than 1899, and the film
//      must not already exist in the Movie objects array.
//
//------------------------------------------------------------------------------
//
// void recommend()
//
//	Parameters:	none
//	Post-condition: The critic with ratings closest to the user's ratings will
//                  be selected, and films that critic rated 3 or above will
//                  be displayed, provided the user didn't already rate them.
//  	Pre-condition: The user must rate at least 5 films. Otherwise, the program
//                     will attempt to compute distance with -1 array indices,
//                      thus resulting in segmentation fault errors.
//
//------------------------------------------------------------------------------
//
// void scanFile()
//
//	Parameters:	none
//	Post-condition: Except for the header, each line of an input file is
//                  broken into tokens via comma and carriage return delimiters.
//                  Each token is input into the corresponding field of a Movie object.
//                  numberOfFilms is incremented with every line scanned in.
//  	Pre-condition: User must input a valid filename to scan.
//                     Input file must not contain more than 1900 entries.
//
//------------------------------------------------------------------------------
//
// void writeFile()
//
//	Parameters:	none
//	Post-condition: New text file (name chosen by user) writes the data,
//                  with the addition of user ratings occupying another column.
//  	Pre-condition: User must input a valid filename to write to.
//
//------------------------------------------------------------------------------
//
// bool rateFilm(string)
//
//	Parameters:	title, string, represents title of the film, passed by value
//	Post-condition: If film is found, the user can rate it, and the relevant
//                  ratedFilmIndex is set to that particular array index.
//  	Pre-condition: film must be found in array for user to rate film.
//
//------------------------------------------------------------------------------
//

#ifndef MOVIELIST_H
#define MOVIELIST_H


#include "Movie.h"

class Movie;

class MovieList 
{

private:
    Movie movies[1900];
    int ratedFilmIndex[5];
    int numberOfFilms;

public:
    MovieList();
    void addMovie (std::string, std::string, std::string);
    void recommend();
    void scanFile();
    void writeFile();
    bool rateFilm(std::string);
    
    
}; 

#endif
