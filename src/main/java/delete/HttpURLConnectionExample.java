package delete;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionExample {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String POST_URL = "http://localhost:8080/api/01/employee/";

    private static final String ID_UPDATE = "6";

    public static void main(String[] args) throws IOException {

        sendPOST();
        System.out.println("PUT DONE");
    }

    private static void sendPOST() throws IOException {
        URL obj = new URL(POST_URL + ID_UPDATE);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeBytes("BUIVANCUONG");
        dos.flush();
        dos.close();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("PUT Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("PUT request not worked");
        }
    }

}
