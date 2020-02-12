package ds.core;

public class Neighbour{
    private String ip;
    private int port;
    private String username;
    private int clientPort;

    public Neighbour(String ip, int port, String username, int clientPort){
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.clientPort = clientPort;
    }

    public String getIp(){
        return this.ip;
    }

    public String getUsername(){
        return this.username;
    }

    public int getPort(){
        return this.port;
    }

    public boolean equals(String address, int port) {
        return ((this.port == port ) & this.ip.equals(address));
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getClientPort() {
        return clientPort;
    }
}
