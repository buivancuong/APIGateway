package gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import static spark.Spark.*;

public class APIGateway {

    private static final String API_VER = "api/01";

    public static void main(String[] args) throws FileNotFoundException {

        port(8080);

        FileOutputStream logRequest = new FileOutputStream("LogRequest");
        DataOutputStream dataRequest = new DataOutputStream(logRequest);

        final Logger loggerRequest = LoggerFactory.getLogger(APIGateway.class);

        get("/" + API_VER + "/employees/:id", ((request, response) -> {

            URL urlGET = new URL("http://localhost:8000/employees/" + request.params(":id"));
            HttpURLConnection connection = (HttpURLConnection) urlGET.openConnection();
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("User-agent", "Mozilla/5.0");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String string;
            StringBuffer stringBuffer = new StringBuffer();
            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string);
            }
            bufferedReader.close();
            connection.disconnect();

            Date date = new Date();
            dataRequest.writeUTF("\n" + request.ip() + " [" + date + "] " + "GET " + request.url() + "\n");
            loggerRequest.info("\n" + request.ip() + " [" + date + "] " + "GET " + request.url() + "\n");

            return stringBuffer;
        }));

        post("/" + API_VER + "/employee", ((request, response) -> {
            System.out.println("API Gateway " + request.body());
            URL urlPOST = new URL("http://localhost:8100/employee");
            HttpURLConnection connection = (HttpURLConnection) urlPOST.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(request.body());
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();

            Date date = new Date();
            dataRequest.writeUTF("\n" + request.ip() + " [" + date + "] " + "POST " + request.url() + "\n");
            loggerRequest.info("\n" + request.ip() + " [" + date + "] " + "POST " + request.url() + "\n");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String string;
            StringBuffer stringBuffer = new StringBuffer();

            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string);
            }
            bufferedReader.close();

            return bufferedReader;
        }));

        delete("/" + API_VER + "/employee/:id", ((request, response) -> {
            URL urlDELETE = new URL("http://localhost:8200/employee/" + request.params(":id"));
            HttpURLConnection connection = (HttpURLConnection) urlDELETE.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(request.params(":id"));
            dataOutputStream.flush();
            dataOutputStream.close();

            Date date = new Date();
            dataRequest.writeUTF("\n" + request.ip() + " [" + date + "] " + "DELETE " + request.url() + "\n");
            loggerRequest.info("\n" + request.ip() + " [" + date + "] " + "DELETE " + request.url() + "\n");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String string;
            StringBuffer stringBuffer = new StringBuffer();

            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string);
            }
            bufferedReader.close();

            return bufferedReader;
        }));

        put("/" + API_VER + "/employee/:id", ((request, response) -> {
            URL urlPUT = new URL("http://localhost:8300/employee/" + request.params(":id"));
            HttpURLConnection connection = (HttpURLConnection) urlPUT.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(request.body());
            dataOutputStream.flush();
            dataOutputStream.close();

            Date date = new Date();
            dataRequest.writeUTF("\n" + request.ip() + " [" + date + "] " + "PUT " + request.url() + "\n");
            loggerRequest.info("\n" + request.ip() + " [" + date + "] " + "PUT " + request.url() + "\n");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String string;
            StringBuffer stringBuffer = new StringBuffer();

            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string);
            }
            bufferedReader.close();

            return bufferedReader;

        }));

    }

}
