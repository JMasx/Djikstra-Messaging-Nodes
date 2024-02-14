package csx55.overlay.node;

public class RegisterResponse {
    private int messageType;
    private byte statusCode;
    private String additionalInfo;

    // Enum for status codes
    public enum Status {
        SUCCESS,
        FAILURE
    }

    // Constructor
    public RegisterResponse(int messageType, byte statusCode, String additionalInfo) {
        this.messageType = messageType;
        this.statusCode = statusCode;
        this.additionalInfo = additionalInfo;
    }

    // Getters and Setters
    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public byte getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(byte statusCode) {
        this.statusCode = statusCode;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    // Method to get the status enum
    public Status getStatus() {
        return (statusCode == 0) ? Status.SUCCESS : Status.FAILURE;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "RegisterResponse{" +
                "messageType=" + messageType +
                ", statusCode=" + statusCode +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
