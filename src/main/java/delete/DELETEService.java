package delete;

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

public class DELETEService {

    public static void main(String[] args) throws FileNotFoundException {

        port(8200);

        FileOutputStream logDELETE = new FileOutputStream("LogDELETE");
        DataOutputStream dataDELETE = new DataOutputStream(logDELETE);

        final Logger loggerDELETE = LoggerFactory.getLogger(DELETEService.class);

        delete("/employee/:id", ((request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            if (id > 0) {
                ConnectionPoolExample connectionPoolExample = new ConnectionPoolExample();
                DataSource dataSource = connectionPoolExample.setUp();
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE ID = " + id);
                preparedStatement.executeUpdate();

                Date date = new Date();
                dataDELETE.writeUTF("\n" + request.ip() + " [" + date + "] " + "DELETE " + request.url() + "\n");
                loggerDELETE.info("\n" + request.ip() + " [" + date + "] " + "DELETE " + request.url() + "\n");

                return response.status();
            } else return 404;
        }));

    }

}
