// Jack Christiansen
// CSC 360 - Computer Networking
// Project 1

import java.io.*;
import java.net.*;
import java.util.*;



// When testing it with the url "http://csmac2.lions.tcnj.edu:8004/" I got:

// GET / HTTP/1.1
// Host: csmac2.lions.tcnj.edu:8004
// User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:17.0) Gecko/20100101 Firefox/17.0
// Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
// Accept-Language: en-US,en;q=0.5
// Accept-Encoding: gzip, deflate
// Connection: keep-alive

// Example from section 2.2 of book, pages 103-104:

// GET /somedir/page.html HTTP/1.1 
// Host: www.someschool.edu
// Connection: close 
// User-agent: Mozilla/5.0 
// Accept-language: fr



class WebServer
{
    public static void main(String args[]) throws Exception
    {
        // Set the port number
        int port = 6789;
        
        //Establish the listen socket: ?
        ServerSocket welcomeSocket = new ServerSocket(8004);  //subject to change
        
        // Process HTTP service requests in an infinite loop
        while(true)
        {
            //Listen for a TCP connection request: ?
            Socket connectionSocket = welcomeSocket.accept();
            
            // Construct an object to process the HTTP request message.
            HttpRequest request = new HttpRequest(connectionSocket);
            // Create a new thread to process the request.
            Thread thread = new Thread(request);
            // Start the thread.
            thread.start();
        }
        
    }
}

final class HttpRequest implements Runnable
{
    final static String CRLF = "\r\n";
    Socket socket;
    
    // Constructor
    public HttpRequest(Socket socket) throws Exception
    {
        this.socket = socket;
    }
    
    // Implement the run() method of the Runnable interface.
    public void run()
    {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void processRequest() throws Exception
    {
        // Get a reference to the socket's input and output streams.
        InputStream is = socket.getInputStream();
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        
        // Set up input stream filters.
        //?
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        
        // Get the request line of the HTTP request message.
        String requestLine = br.readLine();
        
        // Display the request line.
        System.out.println();
        System.out.println(requestLine);
        
        // Get and display the header lines.
        String headerLine = null;
        while ((headerLine = br.readLine()).length() != 0)
        {
            System.out.println(headerLine);
        }
        
        // maybe move this (the chunk down below?)
        // --------------------------------------------
        
        // Extract the filename from the request line.
		StringTokenizer tokens = new StringTokenizer(requestLine); 
		tokens.nextToken(); // skip over the method, which should be "GET" 
		String fileName = tokens.nextToken();
		// Prepend a "." so that file request is within the current directory. 
		fileName = "." + fileName;
		
		// Open the requested file. 
		FileInputStream fis = null; 
		boolean fileExists = true; 
		try 
		{
			fis = new FileInputStream(fileName); 
		} 
		catch (FileNotFoundException e) 
		{
			fileExists = false;
		}
		
		// Construct the response message. 
		String statusLine = null;
		String contentTypeLine = null; 
		String entityBody = null;
		
		if (fileExists) 
		{ 
			statusLine = "a";
			contentTypeLine = "Content-type: " + contentType( fileName ) + CRLF;
		} else {
			statusLine = "b";
		}
		contentTypeLine = "c"; 
		entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + "<BODY>Not Found</BODY></HTML>";
		
		// Send the status line. 
		os.writeBytes(statusLine);
		// Send the content type line. 
		os.writeBytes(contentTypeLine);
		
// Send a blank line to indicate the end of the header lines. 
		os.writeBytes(CRLF);
		
		// Send the entity body. 
		if (fileExists)
		{
			sendBytes(fis, os);
			fis.close(); 
		} else {
			os.writeBytes(entityBody);
		}

		//maybe move the chunk above?
		// -------------------------------------
        
        // Close streams and socket. 
        os.close();
        br.close();
        socket.close();
    }
    
    private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception
	{
		// Construct a 1K buffer to hold bytes on their way to the socket. 
		byte[] buffer = new byte[1024];
		int bytes = 0;
		// Copy requested file into the socket's output stream. 
		while((bytes = fis.read(buffer)) != -1 ) 
		{
			os.write(buffer, 0, bytes); 
		}
	}
	
	private static String contentType(String fileName)
	{
		if(fileName.endsWith(".htm") || fileName.endsWith(".html")) 
		{ 
			return "text/html";
		}
		if(fileName.endsWith(".jpeg")) 
		{
			return "image/jpeg"; 
		}
		if(fileName.endsWith(".gif")) 
		{ 
			return "image/gif";
		}
		return "application/octet-stream";
	}
	
}
