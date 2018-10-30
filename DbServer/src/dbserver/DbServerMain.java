/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbserver;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author enzo
 */
public class DbServerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        int portNumber = 9000;
       
        HashMap<Integer,String> hmap = new HashMap<Integer,String>();
        hmap.put(1, "post1");
        hmap.put(2, "post2");
        hmap.put(3, "post3");
        hmap.put(4, "post4");
        hmap.put(5, "post5");
        //servidor
        ServerSocket server = new ServerSocket(portNumber);
        System.out.println("Server running.");
        //bucle infinito donde se crean instancias de la clase Thread 
        //para cada cliente que se conecta
        //cada cliente tiene su propia entrada y salida (in, out).
        while(true){
            Socket socket = null;
            try{
               socket = server.accept();
               //input and output are extracted from the current request's socket
               BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
          
                System.out.println("A new client is connected.");
                Thread dbServer = new DbServer(socket, in, out);
                dbServer.start();
            }catch(IOException ioe){
                socket.close();
                System.err.println("Error when trying to listen on port "+portNumber);
                ioe.printStackTrace();
            }
        }
    }
}
