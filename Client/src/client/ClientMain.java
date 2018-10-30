/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import java.io.*;
import java.net.*;
/**
 *
 * @author enzo
 */
public class ClientMain {
    public static void main(String args[]){
        
        String hostName = "127.0.0.1";
        int portNumber = 7000; 
        
        Client cliente = new Client(hostName, portNumber);
        
        cliente.connectToServer();
    }
}
