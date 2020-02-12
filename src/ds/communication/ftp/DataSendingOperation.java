package ds.communication.ftp;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class DataSendingOperation implements Runnable{
    private Socket clientSocket;
    private BufferedReader in = null;

    private String fileSeparator = System.getProperty("file.separator");
    private String rootFolder;

    private String userName;

    public DataSendingOperation(Socket client, String userName) {
        this.clientSocket = client;
        this.userName = userName;
        this.rootFolder =   "." + fileSeparator + this.userName;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            DataInputStream dIn = new DataInputStream(clientSocket.getInputStream());
            String fileName = dIn.readUTF();

            if (fileName != null) {
                sendFile(fileName);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendFile(String fileName) {
        try {
            File file = new File(rootFolder + fileSeparator + fileName);
            File myFile = file;
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            OutputStream os = clientSocket.getOutputStream();

            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();
            fis.close();
//            LOG.fine("File " + file.getName() + " sent to client.");
        } catch (Exception e) {
//            LOG.severe("File does not exist!");
            e.printStackTrace();
        }
    }
}
