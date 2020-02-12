package ds.core;

public class SearchResult {
    private String fileName;
    private String address;
    private int port;
    private int tcpPort;
    private int hops;
//    private long timeElapsed;


    public SearchResult(String fileName, String address, int port, int tcpPort, int hops) {
        this.fileName = fileName;
        this.address = address;
        this.port = port;
        this.tcpPort = tcpPort;
        this.hops = hops;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public int getHops() {
        return hops;
    }
}
