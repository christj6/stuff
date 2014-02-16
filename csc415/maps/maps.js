// maps.js
// @author Jack Christiansen
// @author Bill Ersing
// @author Chris Springer
// @author Scotty Boutin
//
// This javascript file allows a user to enter markers on a Google Maps overlay via location (address) information
// and chemicals/attributes associated with the property. The costs will be computed and the marker will be displayed
// as red or green, depending on if the threshold is exceeded.
//
// These costs are determined by the program reading in a text file (data.txt) with the following format:
// ATTRIBUTE_NAME \t ATTRIBUTE_COST \n
// where the \t refers to a tab and the \n refers to a newline.
//  

var geocoder;
var map;
var brownfields = []; //Stores the locations entered by the user
var attributes = []; //Stores the attributes read from the text file.
var threshold = 100; //Threshold cost for developing a property

//A current bug with the "threshold" variable is that if the threshold is changed by the user in the middle
//of the marker displaying process, the colors of the markers will not always be consistent with the threshold.
// For example, if a marker is submitted, and it costs 200 dollars, and the threshold is set at 300, the marker
// will display green. However, if the threshold is changed to 100 dollars, and another 200-dollar marker is
// submitted, that marker will be red. The other will remain green. This issue will be handled at some point.

// This object represents a brownfield. It stores an address, location, and chemicals.
function brownfield(addr, lat1, lng1, chm)
{
  this.streetAddress = addr;
  this.latitude = lat1;
  this.longitude = lng1;
  this.chemicals = chm.concat();
  this.cost = 0;
}

// This object stores an attribute (which could be a chemical, or not) and its associated cost.
function attribute(name, price)
{
    this.name = name;
    this.price = price;
}

//This function establishes the map, and centers it on Trenton Transit Center.
function initialize() 
{
    readTextFile();
    geocoder = new google.maps.Geocoder();
    var trenton = new google.maps.LatLng(40.21888, -74.75351);
    var mapOptions = {
      zoom: 13,
      center: trenton,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);
 }

// This function will check if an address was already entered in the system,
// before placing another marker on the map overlay.
 function duplicateCheck(str)
 {
      for (var i = 0; i < brownfields.length; i++)
      {
          if (brownfields[i].streetAddress.toUpperCase() === str.toUpperCase())
          {
            return true;
          } 
      }
      return false;
 }

//This function geocodes an address, converting it into GPS coordinates. Then the address, and chemicals
// are input into a brownfield object which is stored in an array of brownfield objects. 
// The object is evaluated to determine whether the marker should be red or green, and then it's displayed.
function codeAddress() 
{
    var address = document.getElementById('address').value;
    threshold = parseFloat(document.getElementById('thresh').value);
    geocoder.geocode( {'address': address}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {

          if (!duplicateCheck(address)) //duplicate check will go here (!duplicateCheck(address))
          {
            var rawText = document.getElementById('chem').value; //Grabs raw text input from the chemicals textbox.
            var propertyChemicals = rawText.split("\n"); // Splits input text into separate chemicals using the newline as a delimiter

            var loc = new brownfield(address, results[0].geometry.location.lat(), results[0].geometry.location.lng(), propertyChemicals); //Instantiates new brownfield object.
            loc.cost = calculateCost(loc);
            brownfields.push(loc); //places location into the brownfield array

            var string = address + " contains: " + propertyChemicals.concat() + " and costs " + loc.cost + " dollars to develop."; //constructs a string of chemicals in one address

            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location,
                title: string //Using the string as the title allows the user to hover their mouse over the marker and see what chemicals are stored in that plot of land.
            });

            if (loc.cost > threshold)
            {
                marker.setIcon('http://maps.google.com/mapfiles/ms/icons/red-dot.png'); // Red marker
            } else {
                marker.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png'); // Green marker
            }


          } else {
            alert('Duplicate address detected. ');
          }

      } else {
          alert('Geocode error: ' + status);
      }
    });

  }

  // This function allows the user to view all the brownfields entered in the database,
  // and how much it would cost to develop each one.
  function view() 
  {
      for (var i = 0; i < brownfields.length; i++)
      {
          alert(brownfields[i].streetAddress + ' costs: ' + brownfields[i].cost + ' to develop.');
      }
  }

  //This function is used to calculate the cost of developing a property, given the information in the text file.
  function calculateCost(area)
  {
      area.cost = 0;

      for (var i = 0; i < area.chemicals.length; i++)
      {
          for (var j = 0; j < attributes.length; j++)
          {
              if (area.chemicals[i].toUpperCase() === attributes[j].name.toUpperCase())
              {
                  area.cost += parseFloat(attributes[j].price); //if the attribute associated with the property matches one in the data file, its cost is added.
              } 
          }
      }

      return area.cost;
  }

// This function reads the locally-stored text file containing the attributes and their associated costs,
// and places the information into an array. This array will later be used to calculate the cost of development
// for a particular location.
function readTextFile()
{
    var xmlhttp;

    if (window.XMLHttpRequest)
    {
      xmlhttp = new XMLHttpRequest();
    }
    else
    {
      xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function()
    {
          if (xmlhttp.readyState==4 && xmlhttp.status==200)
          {
              var rawData = xmlhttp.responseText; //Grabs raw text input from the text file.
              var allData = rawData.split(/(?:\t|\n)+/); //Separates data using tabs and newlines as delimiters.

              for (var i = 0; i < allData.length; i += 2)
              {
                  var attr = new attribute(allData[i], allData[i+1]); //Instantiates new attribute object.
                  attributes.push(attr); //places location into the attribute array
              }
          }
    }
    xmlhttp.open("GET","data.txt",true);
    xmlhttp.send();
}