package services.human;

import database.ConnectionFactory;
import services.Dao;

import java.sql.*;
import java.util.ArrayList;

public class HumanDAO implements Dao<Human> {
    private Connection connection;

    public HumanDAO() {
        try {
            connection = ConnectionFactory.getConnection();
        } catch(SQLException e) {
            System.out.println("Database Connection Failed." + e);
        }
    }

    @Override
    public boolean doesTableExist() throws SQLException {
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet tables = databaseMetaData.getTables(
                    null,
                    null,
                    "human",
                    null
            );
            return tables.next();
        } catch (SQLException e) {
            System.out.println("Database Connection Failed. " + e);
        }
        return false;
    }

    @Override
    public void createTable() throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS human(" +
                            "id SERIAL PRIMARY KEY," +
                            "pet_id INT NOT NULL REFERENCES pet(id)," +
                            "name VARCHAR(255)," +
                            "gender VARCHAR (1)" +
                            ")"
            );
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Database Connection Failed. " + e);
        }
    }

    @Override
    public void create(Human human) throws SQLException{
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO human(pet_id, name, gender) VALUES(?, ?, ?)"
            );
            preparedStatement.setInt(1, human.getPetId());
            preparedStatement.setString(2, human.getName());
            preparedStatement.setString(3, human.getGender().name());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Failed to create human. " + e);
        }
    }

    public Human getById(int id) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM human WHERE id=?"
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Human human = new Human(
                        resultSet.getInt("id"),
                        resultSet.getInt("pet_id"),
                        resultSet.getString("name"),
                        Gender.valueOf(resultSet.getString("gender"))
                );
                return human;
            }
        } catch (SQLException e) {
            System.out.println("Failed to human by id. " + e);
        }

        return null;
    }

    @Override
    public ArrayList<Human> getAll() throws SQLException{
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM human"
            );

            ArrayList<Human> humans = new ArrayList<Human>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Human human = new Human(
                        resultSet.getInt("id"),
                        resultSet.getInt("pet_id"),
                        resultSet.getString("name"),
                        Gender.valueOf(resultSet.getString("gender"))
                );
                humans.add(human);
            }

            return humans;
        } catch (SQLException e) {
            System.out.println("Failed to get all humans. " + e);
        }

        return null;
    }

    public ArrayList<Human> getAllByPetId(int id) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM human WHERE pet_id=?"
            );
            preparedStatement.setInt(1, id);
            ArrayList<Human> humans = new ArrayList<Human>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Human human = new Human(
                        resultSet.getInt("id"),
                        resultSet.getInt("pet_id"),
                        resultSet.getString("name"),
                        Gender.valueOf(resultSet.getString("gender"))
                );
                humans.add(human);
            }

            return humans;
        } catch (SQLException e) {
            System.out.println("Failed to get all humans by animal id." + e);
        }

        return null;
    }

    @Override
    public void update(Human human) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE human SET pet_id=?, name=?, gender=? WHERE id=?"
            );
            preparedStatement.setInt(1, human.getPetId());
            preparedStatement.setString(2, human.getName());
            preparedStatement.setString(3, human.getGender().name());
            preparedStatement.setInt(4, human.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Failed to update human." + e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM human WHERE id=?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Failed to delete human." + e);
        }

    }
}
