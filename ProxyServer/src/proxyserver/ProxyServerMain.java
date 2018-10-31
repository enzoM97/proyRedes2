/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxyserver;
import java.io.*;
import java.net.*;
/**
 *
 * @author enzo
 */
public class ProxyServerMain {
    public static void main(String args[]) throws IOException{
    
        int localPort = 7000;
        String serverName = "127.0.0.1";
        int serverPort = 9000;
        
        //server socket para escuchar conexiones de clientes
        ServerSocket proxy = new ServerSocket(localPort);
        System.out.println("ProxyServer running.");
        
        while(true){
            //representa a un cliente que se conecta al proxyServer
            Socket clientSocket = null;
            try{
                clientSocket = proxy.accept();
                //el cliente conectado es pasado al constructor del proxy
                Thread proxyServer = new ProxyServer(clientSocket, serverName, serverPort);
                proxyServer.start();
            }catch(IOException ioe){
                System.err.println("Error when trying to listen on port " + localPort);
                ioe.printStackTrace();
            }
        }
        
    }
}
