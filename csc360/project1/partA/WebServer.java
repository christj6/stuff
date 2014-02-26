// Jack Christiansen
// CSC 360 - Computer Networking
// Project 1 - part A

import java.io.*;
import java.net.*;
import java.util.*;

/*

When testing it with the url "http://csmac2.lions.tcnj.edu:8004/" I got:

GET / HTTP/1.1
Host: csmac2.lions.tcnj.edu:8004
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:17.0) Gecko/20100101 Firefox/17.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Connection: keep-alive

Example from section 2.2 of book, pages 103-104:

GET /somedir/page.html HTTP/1.1 
Host: www.someschool.edu
Connection: close 
User-agent: Mozilla/5.0 
Accept-language: fr

*/

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
        
        // Close streams and socket. 
        os.close();
        br.close();
        socket.close();
    }
}
