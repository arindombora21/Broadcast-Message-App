package HTTP_Downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader {
    private static final int BUFFER_SIZE=4096;//constant throughout program

    public static void downloadFile(String fileURL,String saveDir) throws IOException {//object not rqd for calling since it is static
        URL url=new URL(fileURL);
        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
        int responseCode=httpURLConnection.getResponseCode();

        if(responseCode==HttpURLConnection.HTTP_OK){
            String fileName="";
            String disposition= httpURLConnection.getHeaderField("Content-Disposition");// the Content-Disposition response header is a header indicating if the content is expected to be displayed inline in the browser, that is, as a Web page or as part of a Web page, or as an attachment, that is downloaded and saved locally.
            String contentType= (String) httpURLConnection.getContentType();
            int contentLength=httpURLConnection.getContentLength();

            if(disposition!=null){
                int index=disposition.indexOf("filename");
                if(index>0){
                    fileName=disposition.substring(index+10,disposition.length()-1);
                }
            }else{
                fileName=fileURL.substring(fileURL.lastIndexOf("/")+1,fileURL.length());
            }
            System.out.println(contentType);
            System.out.println(httpURLConnection.getContent().toString());
            System.out.println(disposition);
            System.out.println(contentLength);
            System.out.println(fileName);

            InputStream inputStream=httpURLConnection.getInputStream();
            String saveFilePath=saveDir+ File.separator+fileName;

            FileOutputStream fileOutputStream=new FileOutputStream(saveFilePath);//will write to this path

            int bytesRead=-1;
            byte[] buffer=new byte[BUFFER_SIZE];
            while((bytesRead=inputStream.read(buffer))!=-1){
                fileOutputStream.write(buffer,0,bytesRead);//writing to output stream from buffer i.e. the path specified
            }
        }
    }

}
