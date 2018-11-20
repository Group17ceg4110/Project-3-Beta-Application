package ceg4110_f18_g19.isitfood.client;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CommunicationModule {
    public static String request(File f)
    {
        try {
            String boundary = Long.toHexString(System.currentTimeMillis());
            URL url = new URL("http://www.whatever_the_domain_name_of_the_web_server_is.com"); //TODO: actually determine the URL to connect to for communication with the server.
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.println("--" + boundary);
                writer.println("Content-Disposition: form-data; name=\"b64image\"");
                writer.println();

                FileInputStream fileInputStreamReader = new FileInputStream(f);
                byte[] fileBytes = new byte[(int)f.length()];
                fileInputStreamReader.read(fileBytes);

                writer.println(Base64.encodeToString(fileBytes, Base64.DEFAULT));
                writer.println("--" + boundary + "--");

                InputStream response = connection.getInputStream();
                byte[] responseBytes = new byte[response.available()];
                response.read(responseBytes);
                return new String(responseBytes);
            } finally {
                if (writer != null) writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "No Response";
    }
}
