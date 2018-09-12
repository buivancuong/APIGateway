package post;

import get.GETService;
import jdbc.ConnectionPoolExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import static spark.Spark.*;

public class POSTService {

    public static void main(String[] args) throws FileNotFoundException {

        port(8100);

        FileOutputStream logPOST = new FileOutputStream("LogPOST");
        DataOutputStream dataPOST = new DataOutputStream(logPOST);

        final Logger loggerPOST = LoggerFactory.getLogger(GETService.class);

        post("/employee", ((request, response) -> {
//            System.out.println("POST Service " + request.body());
            if (request.body().length() > 0) {
                ConnectionPoolExample connectionPoolExample = new ConnectionPoolExample();
                DataSource dataSource = connectionPoolExample.setUp();
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee (name) VALUES ('" + request.body() + "')");
                preparedStatement.executeUpdate();

                Date date = new Date();
                dataPOST.writeUTF("\n" + request.ip() + " [" + date + "] " + "POST " + request.url() + "\n");
                loggerPOST.info("\n" + request.ip() + " [" + date + "] " + "POST " + request.url() + "\n");

                return response.status();
            } else return 404;
        }));

    }
}
