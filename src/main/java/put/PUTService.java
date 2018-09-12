package put;

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

public class PUTService {

    public static void main(String[] args) throws FileNotFoundException {

        port(8300);

        FileOutputStream logPUT = new FileOutputStream("LogPut");
        DataOutputStream dataPUT = new DataOutputStream(logPUT);

        final Logger loggerPUT = LoggerFactory.getLogger(PUTService.class);

        put("/employee/:id", ((request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            if (id > 0) {
                ConnectionPoolExample connectionPoolExample = new ConnectionPoolExample();
                DataSource dataSource = connectionPoolExample.setUp();
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employee SET name = '" + request.body() + "' WHERE ID = " + id);
                preparedStatement.executeUpdate();

                Date date = new Date();
                dataPUT.writeUTF("\n" + request.ip() + " [" + date + "] " + "PUT " + request.url() + "\n");
                loggerPUT.info("\n" + request.ip() + " [" + date + "] " + "PUT " + request.url() + "\n");

                return response.status();
            } else return 404;
        }));

    }

}
