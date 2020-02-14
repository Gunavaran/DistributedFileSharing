package ds.utils;

import java.util.ArrayList;

public class Log {
    private int QUERY_RECEIVED_COUNT;
    private int QUERY_FORWARDED_COUNT;
    private int SEARCHOK_SENT_COUNT;
    private ArrayList<String> log;
    private ArrayList<Long> latency;
    private ArrayList<Integer> hops;

    public Log() {
        this.log = new ArrayList<>();
        this.latency = new ArrayList<>();
        this.hops = new ArrayList<>();
        this.QUERY_FORWARDED_COUNT = 0;
        this.QUERY_RECEIVED_COUNT = 0;
        this.SEARCHOK_SENT_COUNT = 0;
    }

    public synchronized void writeLog(String message) {
        this.log.add(message);
    }

    public synchronized void writeLatency(long timeElapsed){
        this.latency.add(timeElapsed);
    }

    public synchronized void writeHops(int hop){
        this.hops.add(hop);
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public ArrayList<Long> getLatency() {
        return latency;
    }

    public ArrayList<Integer> getHops() {
        return hops;
    }

    public void printLog() {
        for (int i = 0; i < log.size(); i++) {
            System.out.println(log.get(i));
        }

    }

    public int getQUERY_RECEIVED_COUNT() {
        return QUERY_RECEIVED_COUNT;
    }

    public int getQUERY_FORWARDED_COUNT() {
        return QUERY_FORWARDED_COUNT;
    }

    public int getSEARCHOK_SENT_COUNT() {
        return SEARCHOK_SENT_COUNT;
    }

    public synchronized void increment_QUERY_RECEIVED_COUNT() {
        this.QUERY_RECEIVED_COUNT++;
    }

    public synchronized void incrementQUERY_FORWARDED_COUNT() {
        this.QUERY_FORWARDED_COUNT++;
    }

    public synchronized void incrementSEARCHOK_SENT_COUNT() {
        this.SEARCHOK_SENT_COUNT++;
    }

    public synchronized void setQUERY_RECEIVED_COUNT(int QUERY_RECEIVED_COUNT) {
        this.QUERY_RECEIVED_COUNT = QUERY_RECEIVED_COUNT;
    }

    public synchronized void setQUERY_FORWARDED_COUNT(int QUERY_FORWARDED_COUNT) {
        this.QUERY_FORWARDED_COUNT = QUERY_FORWARDED_COUNT;
    }

    public synchronized void setSEARCHOK_SENT_COUNT(int SEARCHOK_SENT_COUNT) {
        this.SEARCHOK_SENT_COUNT = SEARCHOK_SENT_COUNT;
    }

    public synchronized void setLatency(ArrayList<Long> latency) {
        this.latency = latency;
    }

    public synchronized void setHops(ArrayList<Integer> hops) {
        this.hops = hops;
    }
}
