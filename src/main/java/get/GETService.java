package get;

import com.google.gson.Gson;
import jdbc.ConnectionPoolExample;
import model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;

import static spark.Spark.*;

public class GETService {

    public static void main(String[] args) throws FileNotFoundException {

        port(8000);

        FileOutputStream logGET = new FileOutputStream("LogGET");
        DataOutputStream dataGET = new DataOutputStream(logGET);

        final Logger loggerGET = LoggerFactory.getLogger(GETService.class);

        HashMap<String, String> cacheGET = new HashMap<>();

        get("employees/:id", ((request, response) -> {
            String idEmployee = request.params(":id");
            if (cacheGET.containsKey(idEmployee)) {
                return cacheGET.get(idEmployee);
            } else {
                ConnectionPoolExample connectionPoolExample = new ConnectionPoolExample();
                DataSource dataSource = connectionPoolExample.setUp();

                Connection connection = dataSource.getConnection();
                String sql = "SELECT * FROM employee WHERE id = " + request.params(":id");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                Date date = new Date();
                dataGET.writeUTF("\n" + request.ip() + " [" + date + "] " + "GET " + request.url() + "\n");
                loggerGET.info("\n" + request.ip() + " [" + date + "] " + "GET " + request.url() + "\n");

                String result = null;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String position = resultSet.getString("position");
                    String age = resultSet.getString("age");
                    String gender = resultSet.getString("gender");

                    Employee employee = new Employee.BuilderEmployee().setIdEmployee(String.valueOf(id)).setNameEmployee(name).setPositionEmployee(position).setAgeEmployee(age).setGenderEmployee(gender).builder();
                    Gson gson = new Gson();
                    result = gson.toJson(employee);
                }
                cacheGET.put(idEmployee, result);
                connection.close();
                return result;
            }
        }));

    }

}
