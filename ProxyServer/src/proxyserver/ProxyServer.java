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
public class ProxyServer extends Thread {
    private Socket clientSocket;   //socket for an especific client
    private String serverName;     //server name
    private int serverPort;        //server port
    private String fromClient;     //client input
    private String toClient;       //client output
    private String fromServer;     //server output
    private String toServer;       //server input
    
    public ProxyServer(Socket clientSocket, String serverName, int serverPort){
        this.clientSocket = clientSocket;
        this.serverName = serverName;
        this.serverPort = serverPort;
    }
    
    @Override
    public void run(){
        //creates a socket to connect to the server
        try(
            //socket para conectarse al servidor como cliente
            Socket server = new Socket(serverName, serverPort);
            //output para cliente
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            //input de cliente
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //output para el server
            PrintWriter toServer = new PrintWriter(server.getOutputStream(), true);
            //input del server
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
        ){
            
            System.out.println("A new client is connected.");
            //thread para enviar multiples pedidos al server
            new Thread(){
                @Override
                public void run(){
                    String input, output;
                    while(true){
                        try{
                            //
                            if(server.isClosed()){
                                clientSocket.close();
                                break;
                            }
                            else{
                                //lee la entrada del cliente
                                input = fromClient.readLine();
                                //envia al servidor la entrada del cliente
                                toServer.println(input);
                            }
                        }catch(IOException ioe){
                            ioe.printStackTrace();
                        }
                    }
                }
            }.start();
            //el servidor devuelve los pedidos al cliente
            while(true){
                String input, output;
                try{
                    input = fromServer.readLine();
                    //si la respuesta del servidor es el exit del cliente
                    //el proxyserver cierra la conexión con ese cliente
                    if(input.equals("exit")){
                        server.close();
                        System.out.println("Client disconnected.");
                        break;
                    }
                    output = input;
                    toClient.println(output);
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public String getFromClient() {
        return fromClient;
    }

    public String getToClient() {
        return toClient;
    }

    public String getFromServer() {
        return fromServer;
    }

    public String getToServer() {
        return toServer;
    }
    
}