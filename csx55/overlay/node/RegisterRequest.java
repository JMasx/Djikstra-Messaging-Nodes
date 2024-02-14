package csx55.overlay.node;

public class RegisterRequest {
    public static final int REGISTER_REQUEST = 1;
    private int messageType;
    private String ipAddress;
    private int portNumber;

    // Constructor
    public RegisterRequest(int messageType, String ipAddress, int portNumber) {
        this.messageType = messageType;
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    // Getters and Setters
    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "messageType=" + messageType +
                ", ipAddress='" + ipAddress + '\'' +
                ", portNumber=" + portNumber +
                '}';
    }
}
