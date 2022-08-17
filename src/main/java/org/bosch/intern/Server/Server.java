package org.bosch.intern.Server;

import org.bosch.intern.core.ClientRequest;
import org.bosch.intern.exception.BookStoreException;
import org.bosch.intern.util.ConstantMessages;
import org.bosch.intern.util.ExceptionMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Closeable {
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private static final String READ = "read";
    private static final String ADD = "add";

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

    private void sendMessage(String response) {
        printWriter.println(response);
        printWriter.flush();
    }

    private String readMessage() throws IOException {
        return bufferedReader.readLine();
    }

    public ClientRequest readRequest() throws IOException {
        String command = readMessage();
        String entity = readMessage();
        if (command.equalsIgnoreCase(READ) || command.equalsIgnoreCase(ADD)) {
            String parameters = readMessage();
            return new ClientRequest(command, entity, parameters);
        }
        return new ClientRequest(command, entity);
    }

    public void sendResponseError(List<String> responses) {
        this.sendMessage(Integer.toString(responses.size()));
        for (String response : responses) {
            this.sendMessage(response);
        }
    }

    public void sendResponse(List<List<String>> responses) {
        this.sendMessage(Integer.toString(responses.size()));
        for (List<String> currentLine : responses) {
            this.sendMessage(String.join(" ", currentLine));

        }
    }

    @Override
    public void close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            System.out.println(ConstantMessages.CLOSE_SERVER_SOCKET);
        } catch (IOException e) {
            throw new BookStoreException(ExceptionMessage.ERROR_SERVER_SOCKET);
        }
        try {
            if (socket != null) {
                socket.close();
            }
            System.out.println(ConstantMessages.CLOSE_SOCKET);
        } catch (IOException e) {
            throw new BookStoreException(ExceptionMessage.ERROR_SOCKET);
        }
        if (printWriter != null) {
            printWriter.close();
            System.out.println(ConstantMessages.CLOSE_WRITER);
        }
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
                System.out.println(ConstantMessages.CLOSE_WRITER);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}