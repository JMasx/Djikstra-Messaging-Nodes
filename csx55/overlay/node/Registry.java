package csx55.overlay.node;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Registry {
    private int port;
    private List<MessagingNodeInfo> messagingNodes;

    public Registry(int port) {
        this.port = port;
        this.messagingNodes = new ArrayList<>();
    }

    public void start() {
        ServerSocket serverSocket = null;
        try {
             serverSocket = new ServerSocket(port);
            System.out.println("Registry listening on port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new MessagingNodeHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void registerNode(MessagingNodeInfo nodeInfo) {
        messagingNodes.add(nodeInfo);
        System.out.println("Node registered: " + nodeInfo.getHostname() + ":" + nodeInfo.getPort());
    }

    public void deregisterNode(MessagingNodeInfo nodeInfo) {
        messagingNodes.remove(nodeInfo);
        System.out.println("Node deregistered: " + nodeInfo.getHostname() + ":" + nodeInfo.getPort());
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java csx55.overlay.node.Registry <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        Registry registry = new Registry(port);
        registry.start();
    }

    class MessagingNodeHandler implements Runnable {
    private Socket socket;

    public MessagingNodeHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Get input and output streams from the socket
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Read messages from the messaging node and process them
            while (true) {
                Object receivedObject = in.readObject();

                // Check the type of the received object and handle it accordingly
                if (receivedObject instanceof RegisterRequest) {
                    // Handle register request
                    RegisterRequest registerRequest = (RegisterRequest) receivedObject;
                    handleRegisterRequest(registerRequest);
                } else {
                    // Handle other types of messages if needed
                    // For example, if you have other messag    e types like data messages
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling messaging node request: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Received object of unknown class");
        }   
        
        // End of run method
    }

    private void handleRegisterRequest(RegisterRequest registerRequest) {
        // Extract necessary information from the register request
        String ipAddress = registerRequest.getIpAddress();
        int portNumber = registerRequest.getPortNumber();

        // Create a MessagingNodeInfo object with the extracted information
        MessagingNodeInfo nodeInfo = new MessagingNodeInfo(ipAddress, portNumber);

        // Register the messaging node with the registry
        registerNode(nodeInfo);

        // Send a response back to the messaging node
        RegisterResponse response = new RegisterResponse(RegisterRequest.REGISTER_REQUEST, (byte) 0, "Successfully registered");

        sendResponse(response);
    }


    private void sendResponse(RegisterResponse response) {
        // Send the response object back to the messaging node
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(response);
            out.flush();
        } catch (IOException e) {
            System.err.println("Error sending response to messaging node: " + e.getMessage());
        }
    }
}

}

class MessagingNodeInfo {
    private String hostname;
    private int port;

    public MessagingNodeInfo(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }
}
