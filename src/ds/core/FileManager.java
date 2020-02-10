package ds.core;

import ds.utils.Log;
import ds.utils.PropertiesFile;

import java.util.*;

public class FileManager {
    private static FileManager fileManager;
    private ArrayList<String> files;
    private Log log;
    private int fileCount;

    private FileManager(Log log) {
        this.files = new ArrayList<String>();
        this.fileCount = randomInteger(3, 5);
        this.log = log;
        log.writeLog("Files: ");
        String[] allFiles = PropertiesFile.getNodeProperty("files").split(",");
        for (int i = 0; i < this.fileCount; i++) {
            while (true) {
                String temp = allFiles[randomInteger(0, allFiles.length - 1)];
                if (!files.contains(temp)) {
                    this.files.add(temp);
                    log.writeLog(temp);
                    break;
                }
            }

        }


    }

    public static synchronized FileManager getInstance(Log log) {
        if (fileManager == null) {
            fileManager = new FileManager(log);

        }
        return fileManager;
    }

    //creates a random number between min and max (including min and max)
    private int randomInteger(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public void printFileList() {
        System.out.println("Files are: ");
        for (String file : this.files) {
            System.out.print(file + " ");
        }
    }

    public Set<String> searchForFile(String query){
        //hashset is not internally synchronized
        Set<String> result = new HashSet<>();
        String[] querySplit = query.split(" ");

        for(String qSplit: querySplit){
            for(String file : this.files){
                String[] fileSplit = file.split(" ");
                for(String fSplit : fileSplit){
                    //we ignore upper and lower cases
                    if(fSplit.toLowerCase().equals(qSplit.toLowerCase())){
                        result.add(file);
                    }
                }
            }
        }
        return result;

    }

}
