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
public class DbServer extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    
    public DbServer(Socket socket, BufferedReader in, PrintWriter out){
        this.socket = socket;
        this.in = in;
        this.out = out;
    }
    @Override
    public void run(){
        String input, output;
        while(true){
            try{
                //sends instruction to connected client
                this.out.println("Type exit to exit xd");
                input = this.in.readLine();
                if(input.equals("exit")){
                    //cierra el socket cliente que está conectado al server
                    this.socket.close();
                    System.out.println("Client disconnected.");
                    break;
                }
                this.out.println("received!: "+input);
            }catch(IOException ioe){
                ioe.printStackTrace();
                System.exit(1);
            }
        }
    } 
        
    public Boolean isNumber(String str){
        try{
            Integer.parseInt(str);
        }catch(NumberFormatException e){
            return false;
        }catch(NullPointerException e){
            return false;
        }
        return true;
    } 
}
