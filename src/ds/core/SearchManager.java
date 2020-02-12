package ds.core;

import ds.communication.MessageBroker;
import ds.handlers.QueryHitHandler;
import ds.handlers.SearchHandler;
import ds.utils.ConsoleTable;
import ds.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchManager {

    private MessageBroker messageBroker;
    private Map<Integer, SearchResult> fileDownloadOptions;

    public SearchManager(MessageBroker messageBroker){
        this.messageBroker = messageBroker;
    }

    int doSearch(String query){
        Map<String, SearchResult> searchResults = new HashMap<String, SearchResult>();
//        QueryHitHandler queryHitHandler = QueryHitHandler.getInstance();
        QueryHitHandler queryHitHandler =messageBroker.getQueryHitHandler();

        queryHitHandler.setSearchResutls(searchResults);

        this.messageBroker.doSearch(query);

        System.out.println("Please be patient till the file results are returned ...");

        try{
            Thread.sleep(Constants.SEARCH_TIMEOUT);

        } catch (InterruptedException e){
            e.printStackTrace();
        }

        printSearchResults(searchResults);
        this.clearSearchResults();
        return fileDownloadOptions.size();

    }

    private void printSearchResults(Map<String, SearchResult> searchResults){

        System.out.println("\nFile search results : ");

        ArrayList<String> headers = new ArrayList<String>();
        headers.add("Option No");
        headers.add("FileName");
        headers.add("Source");
        headers.add("Hop count");

        ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();

        int fileIndex = 1;

        this.fileDownloadOptions = new HashMap<Integer, SearchResult>();

        for (String s : searchResults.keySet()){
            SearchResult searchResult = searchResults.get(s);
            this.fileDownloadOptions.put(fileIndex, searchResult);

            ArrayList<String> row1 = new ArrayList<String>();
            row1.add("" + fileIndex);
            row1.add(searchResult.getFileName());
            row1.add(searchResult.getAddress() + ":" + searchResult.getFtpPort());
            row1.add("" + searchResult.getHops());

            content.add(row1);

            fileIndex++;
        }

        if (fileDownloadOptions.size() == 0){
            System.out.println("Sorry. No files are found!!!");

            return;
        }

        ConsoleTable ct = new ConsoleTable(headers,content);
        ct.printTable();

    }

    public SearchResult getFileDetails(int fileIndex){
        return this.fileDownloadOptions.get(fileIndex);
    }

    private void clearSearchResults(){
//        QueryHitHandler queryHitHandler = QueryHitHandler.getInstance();
        QueryHitHandler queryHitHandler = messageBroker.getQueryHitHandler();

        queryHitHandler.setSearchResutls(null);
    }
}
