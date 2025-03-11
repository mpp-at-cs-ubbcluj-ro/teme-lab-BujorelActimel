package ro.mpp2024;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UsersDBRepository implements UserRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public UsersDBRepository(Properties props) {
        logger.info("Initializing UsersDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public List<User> findByUsername(String username) {
        logger.traceEntry("finding by username {}", username);
        Connection con = dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from users where username = ?")) {
            preStmt.setString(1, username);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String usernameDb = result.getString("username");
                    String email = result.getString("email");
                    String password = result.getString("password");
                    java.sql.Date birthdate = result.getDate("birthdate");
                    User user = new User(usernameDb, email, password, birthdate);
                    user.setId(id);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(users);
        return users;
    }

    @Override
    public List<User> findByEmail(String email) {
        logger.traceEntry("finding by email {}", email);
        Connection con = dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from users where email = ?")) {
            preStmt.setString(1, email);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String emailDb = result.getString("email");
                    String password = result.getString("password");
                    java.sql.Date birthdate = result.getDate("birthdate");
                    User user = new User(username, emailDb, password, birthdate);
                    user.setId(id);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(users);
        return users;
    }

    @Override
    public void add(User user) {
        logger.traceEntry("adding user {}", user);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement(
                "insert into users(username, email, password, birthdate) values (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            preStmt.setString(1, user.getUsername());
            preStmt.setString(2, user.getEmail());
            preStmt.setString(3, user.getPassword());
            preStmt.setDate(4, user.getBirthdate());
            int result = preStmt.executeUpdate();
            logger.trace("Added {} instances", result);

            try (ResultSet generatedKeys = preStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer id, User user) {
        logger.traceEntry("updating user {}", user);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement(
                "update users set username=?, email=?, password=?, birthdate=? where id=?")) {
            preStmt.setString(1, user.getUsername());
            preStmt.setString(2, user.getEmail());
            preStmt.setString(3, user.getPassword());
            preStmt.setDate(4, user.getBirthdate());
            preStmt.setInt(5, id);
            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit();
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from users")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String email = result.getString("email");
                    String password = result.getString("password");
                    java.sql.Date birthdate = result.getDate("birthdate");
                    User user = new User(username, email, password, birthdate);
                    user.setId(id);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(users);
        return users;
    }
}