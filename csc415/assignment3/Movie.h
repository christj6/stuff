// Movie.h

//
// programmed by Jack Christiansen
// CSC 415
// last modified 10/8/2013

// This class has the following public methods (member functions)
//------------------------------------------------------------------------------
//
// Movie(string, string, string, double, double, double, double)
//
//	Parameters:	id, string, represents the film's ID number, passed by value
//              title, string, represents the film's title, passed by value
//              year, string, represents the year the film was released, passed by value
//              ratings 0 through 3, double, represent ratings by each critic, passed by value
//	Post-condition: Movie object is instantiated with the information input.
//  	Pre-condition: Year has length of 4 characters. The ratings are initialized at 0
//                     in the event the user doesn't input any ratings, but if ratings are input,
//                     they must reside between 0 and 4, or else no changes will take place.
//
//------------------------------------------------------------------------------
//
// Movie()
//
//	Parameters:	none
//	Post-condition: Blank constructor so MovieList() can instantiate array of Movie objects
//  	Pre-condition: none
//
//------------------------------------------------------------------------------
//
// string getID()
//
//	Parameters:	none
//	Post-condition: Retrieves a string containing the film's ID.
//  	Pre-condition: none
//
//------------------------------------------------------------------------------
//
// void setID(string)
//
//	Parameters:	id, string, represents the film's ID number, passed by value
//	Post-condition: Film's ID is set to input string.
//  	Pre-condition: none
//
//------------------------------------------------------------------------------
//
// string getTitle()
//
//	Parameters:	none
//	Post-condition: Retrieves a string containing the film's title.
//  	Pre-condition: none.
//
//------------------------------------------------------------------------------
//
// void setTitle(string)
//
//	Parameters:	title, string, represents the film's title, passed by value
//	Post-condition: Film's title is set to input string.
//  	Pre-condition: none
//
//------------------------------------------------------------------------------
//
// string getYear()
//
//	Parameters:	none
//	Post-condition: retrieve string containing film's year of release
//  	Pre-condition: none.
//
//------------------------------------------------------------------------------
//
// void setYear(string)
//
//	Parameters:	year, string, represents the year the film was released, passed by value
//	Post-condition: Film's year is set to input string.
//  	Pre-condition: String must have a length of 4 characters.
//
//------------------------------------------------------------------------------
//
//
// double getRating(int)
//
//	Parameters:	whichOne, int, identifies the critic whose rating will be modified, passed by value
//	Post-condition: Retrieves a rating from a given film and a given critic.
//  	Pre-condition: whichOne must be between 0 and 3; otherwise, it's referring to an invalid critic. In that case, 0 is returned.
//
//------------------------------------------------------------------------------
//
// void setRating(int, double)
//
//	Parameters:	whichOne, int, identifies the critic whose rating will be modified, passed by value
//              rating, double, contains the film's new rating, passed by value
//	Post-condition: Changes a given critic's rating of a given film.
//  	Pre-condition: whichOne must be between 0 and 3; otherwise, it's referring to an invalid critic.
//                     rating must be between 0 and 4; otherwise, the rating is invalid. No rating modification occurs.
//
//------------------------------------------------------------------------------




#ifndef MOVIE_H
#define MOVIE_H

class Movie 
{

private:
    std::string movieID;
    std::string title;
    std::string year;
    double ratings[3];
    double userRating;

public:
    Movie (std::string, std::string, std::string, double = 0, double = 0, double = 0, double = 0);
    Movie ();
    std::string getID ();
    void setID (std::string);
    std::string getTitle ();
    void setTitle (std::string);
    std::string getYear ();
    void setYear (std::string);
    double getRating (int); //0 for critic 1, 1 for critic 2, etc.
    void setRating (int, double); //first parameter determines which critic, 2nd is the rating
    
}; 



#endif
