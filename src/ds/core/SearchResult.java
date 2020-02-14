package ds.core;

public class SearchResult {
    private String fileName;
    private String address;
    private int ftpPort;
    private int hops;
    private long timeElapsed;


    public SearchResult(String fileName, String address, int ftpPort, int hops, long timeElapsed) {
        this.fileName = fileName;
        this.address = address;
        this.ftpPort = ftpPort;
        this.hops = hops;
        this.timeElapsed = timeElapsed;
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

    public long getTimeElapsed() {
        return timeElapsed;
    }
}
