package ds.utils;

import java.util.ArrayList;

public class Log {
    private ArrayList<String> log;

    public Log() {
        this.log = new ArrayList<>();
    }

    public synchronized void writeLog(String message){
        this.log.add(message);
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void printLog() {
        for (int i = 0; i < log.size(); i++) {
            System.out.println(log.get(i));
        }
    }
}
