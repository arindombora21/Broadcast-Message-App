package HTTP_Downloader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String fileURL="https://unsplash.com/photos/_l8ZdgJ9m7w/download?force=true";
        String saveDir="C:\\Users\\DELL\\IdeaProjects\\Networking_Java\\target\\classes\\HTTP_Downloader";
        try{
            Downloader.downloadFile(fileURL,saveDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
