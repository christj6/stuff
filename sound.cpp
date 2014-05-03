#include <cstdio>
#include <sndfile.h>
#include <windows.h>
#include <cstdlib>
#include <cmath>

// compile with: g++ sound.cpp -o sound.o -lsndfile

// taken from: http://stackoverflow.com/questions/5671055/libsndfile-usage-joining-and-mixing-wav-files

#define BUFFER_LEN 1024
#define MAX_CHANNELS 6

static void data_processing (double *data, int count, int channels);

int main (void) 
{
  static double data[BUFFER_LEN];
  static double data2[BUFFER_LEN];
  static double outdata[BUFFER_LEN];

  SNDFILE *infile;
  SNDFILE *outfile;
  SNDFILE *infile2;
  SF_INFO sfinfo;
  int readcount;
  SF_INFO sfinfo2;
  int readcount2;

  const char *infilename = "inputOne.wav";
  const char *infilename2 = "inputTwo.wav";
  const char *outfilename = "output.wav";

  if (! (infile = sf_open (infilename, SFM_READ, &sfinfo))) 
  {
    /* Open failed so print an error message. */
    printf ("Not able to open input file %s.\n", infilename);

    /* Print the error message from libsndfile. */
    puts (sf_strerror (NULL));
    return 1;
  }

  if (sfinfo.channels > MAX_CHANNELS) 
  {
    printf ("Not able to process more than %d channels\n", MAX_CHANNELS);
    return 1;
  }

  if (! (infile2 = sf_open (infilename2, SFM_READ, &sfinfo2))) 
  {
    /* Open failed so print an error message. */
    printf ("Not able to open input file %s.\n", infilename2);

    /* Print the error message from libsndfile. */
    puts (sf_strerror (NULL));
    return 1;
  }

  if (sfinfo2.channels > MAX_CHANNELS) 
  {
    printf ("Not able to process more than %d channels\n", MAX_CHANNELS) ;
    return 1;
  }

  /* Open the output file. */
  if (! (outfile = sf_open (outfilename, SFM_WRITE, &sfinfo))) 
  {
    printf ("Not able to open output file %s.\n", outfilename);
    puts (sf_strerror (NULL));
    return 1;
  }

  while ((readcount = sf_read_double (infile, data, BUFFER_LEN)) && (readcount2 = sf_read_double (infile2, data2, BUFFER_LEN))) 
  { 
    data_processing (data, readcount, sfinfo.channels);
	data_processing(data2, readcount2, sfinfo2.channels);

    for (int i = 0; i < 1024; ++i) 
    {
  		outdata[i] = (data[i] + data2[i]) - (data[i])*(data2[i])/65535;
    }

    sf_write_double (outfile, outdata, readcount);
  }

  /* Close input and output files. */
  sf_close (infile);
  sf_close (infile2);
  sf_close (outfile);
  return 0;
}

static void data_processing (double *data, int count, int channels) 
{ 
	double channel_gain[MAX_CHANNELS] = { 0.5, 0.8, 0.1, 0.4, 0.4, 0.9 };
	int k; 
	int chan;

	for (chan = 0; chan < channels; chan++)
	{
		for (k = chan; k < count; k += channels)
		{
			data[k] *= channel_gain[chan];
		}
	}

  return;
}
