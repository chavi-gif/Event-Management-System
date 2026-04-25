package com.example.event.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ImageUploadService {

    private static final String IMGBB_API_KEY = "707440266c2d7fcb7b39d860615d8f0c";

    public String uploadToImgBB(MultipartFile file) throws Exception {
        String boundary = "---" + System.currentTimeMillis();
        String LINE_FEED = "\r\n";

        URL url = new URL("https://api.imgbb.com/1/upload?key=" + IMGBB_API_KEY);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (OutputStream outputStream = connection.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true)) {

            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + file.getOriginalFilename() + "\"").append(LINE_FEED);
            writer.append("Content-Type: " + file.getContentType()).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            outputStream.write(file.getBytes());
            outputStream.flush();

            writer.append(LINE_FEED);
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Extract URL from response
            String json = response.toString();
            int start = json.indexOf("\"url\":\"") + 7;
            int end = json.indexOf("\"", start);
            String imageUrl = json.substring(start, end);

            // Remove backslashes from URL (fixes the https:\/\/ issue)
            return imageUrl.replace("\\/", "/");
        }

        return null;
    }
}