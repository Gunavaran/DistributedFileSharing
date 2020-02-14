package ds.core;

import ds.utils.Log;
import ds.utils.PropertiesFile;
import java.io.*;
import java.util.*;

public class FileManager {
    private FileManager fileManager;
    private ArrayList<String> files;
    private Log log;
    private int fileCount;
    private String fileSeparator = System.getProperty("file.separator");
    private String rootFolder;
    private String userName;

    public FileManager(String username, Log log) {
        this.userName = username;
        this.rootFolder =   "." + fileSeparator + this.userName;
        this.files = new ArrayList<String>();
        this.fileCount = randomInteger(3, 5);
        this.log = log;
        this.initializeFolder();
        log.writeLog("Files: ");
        String[] allFiles = PropertiesFile.getNodeProperty("files").split(",");
        for (int i = 0; i < this.fileCount; i++) {
            while (true) {
                String temp = allFiles[randomInteger(0, allFiles.length - 1)];
                if (!files.contains(temp)) {
                    this.files.add(temp);
                    this.createFile(temp);
                    log.writeLog(temp);
                    break;
                }
            }

        }


    }

//    public static synchronized FileManager getInstance(String username, Log log) {
//        System.out.println(fileManager);
//        if (fileManager == null) {
//            System.out.println("lol");
//            fileManager = new FileManager(username, log);
//        }
//        return fileManager;
//    }

    //creates a random number between min and max (including min and max)
    private int randomInteger(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public void printFileList() {
        System.out.println("Files are: ");
        for (String file : this.files) {
            System.out.print(file + ", ");
        }
        System.out.println("\n");
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

    public void createFile(String fileName) {
        try {
            String absoluteFilePath = this.rootFolder + fileSeparator + fileName;
            File file = new File(absoluteFilePath);
            file.getParentFile().mkdir();
            if (file.createNewFile()) {
                log.writeLog(absoluteFilePath + " File Created");
            } else log.writeLog("File " + absoluteFilePath + " already exists");
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            f.setLength(1024 * 1024 * 8);
        } catch (IOException e) {
            log.writeLog("File creating failed");
            e.printStackTrace();
        }
    }

    public void initializeFolder() {
        File index = new File(rootFolder + fileSeparator);
        if(index.isDirectory()) {
            String[] entries = index.list();
            for(String s: entries){
                File currentFile = new File(index.getPath(), s);
                currentFile.delete();
            }
        }
    }

}
