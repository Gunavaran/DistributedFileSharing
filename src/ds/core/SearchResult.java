package ds.core;

public class SearchResult {
    private String fileName;
    private String address;
    private int ftpPort;
    private int hops;
//    private long timeElapsed;


    public SearchResult(String fileName, String address, int ftpPort, int hops) {
        this.fileName = fileName;
        this.address = address;
        this.ftpPort = ftpPort;
        this.hops = hops;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAddress() {
        return address;
    }

    public int getFtpPort() {
        return ftpPort;
    }

    public int getHops() {
        return hops;
    }
}
