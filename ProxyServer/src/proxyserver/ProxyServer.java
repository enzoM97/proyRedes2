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
    private Socket clientSocket;         //socket to connect a client to the db server
    private Socket proxySocket;          //socket to connect the db server to a client
    
    private String serverName;           //server name
    private int serverPort;              //server port
    
    private String clientInput;           //client input
    private String clientOutput;             //client output
    private String serverInput;           //server output
    private String serverOutput;             //server input
    
    public ProxyServer(Socket clientSocket, String serverName, int serverPort){
        this.clientSocket = clientSocket;
        this.serverName = serverName;
        this.serverPort = serverPort;
    }
    
    //se encarga de enviar al servidor pedidos del cliente 
    public void toServer(BufferedReader in, PrintWriter out){
        System.out.println("A new client is connected.");
        //thread para enviar multiples pedidos al server
        new Thread(){
            @Override
            public void run(){
                String input, output;
                while(true){
                    try{
                        //lee la entrada del cliente
                        input = in.readLine();
                          
                        if(input.equals("exit")){
                            out.println(input);
                            clientSocket.close();
                            proxySocket.close();
                            System.out.println("Client disconnected.");
                            break;
                        }
                        //envia al servidor la entrada del cliente
                        out.println(input);
                    }catch(IOException ioe){
                        ioe.printStackTrace();
                    }
                }
            }
        }.start();    
    }
    
    //se encarga de enviar al cliente la respuesta del servidor
    public void fromServer(BufferedReader in, PrintWriter out){    
        while(true){
            String input, output;
            try{
                //si la respuesta del servidor es el exit del cliente
                //el proxyserver cierra la conexión con ese cliente
                if(proxySocket.isClosed()) break;
                
                input = in.readLine();
                output = input;
                out.println(output);
                
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
       
    }
    
    @Override
    public void run(){
        try(
            //socket que servira de conexión con el db server
            Socket socket = new Socket(serverName, serverPort);
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));    
        ){
            proxySocket = socket;
            toServer(fromClient, toServer);
            fromServer(fromServer, toClient);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }  
    }
}