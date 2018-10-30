package client;
import java.io.*;
import java.net.*;
import java.util.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author enzo
 */
public class Client {
    private String hostName;
    private int portNumber;
    
    public Client(String hostName, int portNumber){
        this.hostName = hostName;
        this.portNumber = portNumber;
    }
    
    public void connectToServer(){
        String clientInput;
        String toServer = "";
        String fromServer;
        
        try(
            Socket socket = new Socket(this.hostName, this.portNumber);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        ){
            System.out.println("Connected to ProxyServer.");
            System.out.println(in.readLine());
            
            while(true){
                //pedido al servidor
                toServer = stdin.readLine();
                
                if(toServer.equals("exit")){
                    System.out.println("Connection closed.");
                    socket.close();
                    break;
                }
                //sends message to server
                out.println(toServer);
                
                //respuesta del servidor
                fromServer = in.readLine();
                System.out.println(fromServer);
            }
        }catch(IOException ioe){
            System.err.println("Couldn't get I/O for connection to host "+this.hostName);
            ioe.printStackTrace();
        }
    } 
}
