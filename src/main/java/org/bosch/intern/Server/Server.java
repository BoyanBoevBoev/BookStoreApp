package org.bosch.intern.Server;

import org.bosch.intern.core.ClientRequest;
import org.bosch.intern.exception.BookStoreException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Closeable {
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public void starServer(int portNumber) {
        System.out.println("Starting server");
        try {
            serverSocket = new ServerSocket(portNumber);
            socket = serverSocket.accept();
            System.out.println("Client connected");
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
            System.out.println("Server ready");
        } catch (IOException e) {
            throw new BookStoreException("Internal Server Error.");
        }
    }

//    public void sendMessage(List<String> response) {
//        printWriter.println(response);
//        printWriter.flush();
//    }

        public void sendMessage(String response){
            printWriter.println(response);
            printWriter.flush();
        }

    public String readMessage() throws IOException {
        return bufferedReader.readLine();
    }
    public ClientRequest readRequest() throws IOException {
        String command = readMessage();
        String entity = readMessage();
        if (command.equalsIgnoreCase("read") || command.equalsIgnoreCase("add")){
            String parameters = readMessage();
            return new ClientRequest(command,entity,parameters);
        }
        return new ClientRequest(command,entity);
    }

    @Override
    public void close()  {
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
            System.out.println("ServerSocket closed.");
        } catch (IOException e) {
            throw new BookStoreException("Error with the ServerSocket");
        }
        try {
            if (socket != null){
                socket.close();
            }
            System.out.println("Socket closed.");
        } catch (IOException e) {
            throw new BookStoreException("Error with the Socket");
        }
        if (printWriter != null) {
            printWriter.close();
            System.out.println("PrintWriter closed.");
        }
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
                System.out.println("BufferedReader closed.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendResponse(List<String> responses) {
        //Result amount
        this.sendMessage(Integer.toString(responses.size()));

        for (String response : responses) {
            this.sendMessage(response);
        }
    }
}

