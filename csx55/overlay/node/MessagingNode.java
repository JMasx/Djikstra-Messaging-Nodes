package csx55.overlay.node;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessagingNode {
    private String registryHost;
    private int registryPort;
    private int portNumber;

    public MessagingNode(String registryHost, int registryPort, int portNumber) {
        this.registryHost = registryHost;
        this.registryPort = registryPort;
        this.portNumber = portNumber;
    }

    public void registerWithRegistry() {
        try (Socket socket = new Socket(registryHost, registryPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new RegisterRequest(RegisterRequest.REGISTER_REQUEST, registryHost, portNumber));

            out.flush();

            RegisterResponse response = (RegisterResponse) in.readObject();
            if (response.getStatus() == RegisterResponse.Status.SUCCESS) {
                System.out.println("Registered with registry");
            } else {
                System.out.println("Failed to register with registry: " + response.getAdditionalInfo());
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + registryHost);
        } catch (IOException e) {
            System.err.println("Error communicating with registry: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error reading response from registry: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java csx55.overlay.node.MessagingNode <registry-host> <registry-port> <port>");
            System.exit(1);
        }
    
        String registryHost = args[0];
        int registryPort = Integer.parseInt(args[1]);
        int port = Integer.parseInt(args[2]);
    
        MessagingNode messagingNode = new MessagingNode(registryHost, registryPort, port);
        messagingNode.registerWithRegistry();
    }
}
