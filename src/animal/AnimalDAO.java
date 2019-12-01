package animal;

import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;

public class AnimalDAO {
    private Connection connection;

    public AnimalDAO() {
        try {
            connection = ConnectionFactory.getConnection();
        } catch(SQLException e) {
            System.out.println("Conexão com o banco de dados falhou." + e);
        }
    }

    public boolean doesTableExist() throws SQLException {
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet tables = databaseMetaData.getTables(
                    null,
                    null,
                    "animal",
                    null
            );
            return tables.next();
        } catch (SQLException e) {
            System.out.println("Database Connection Failed. " + e);
        }
        return false;
    }

    public void createTable() throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS animal(" +
                            "id SERIAL PRIMARY KEY," +
                            "nome VARCHAR(255) NOT NULL," +
                            "raca VARCHAR(255)," +
                            "created_on TIMESTAMP NOT NULL" +
                            ")"
            );

            if(preparedStatement.execute()) {
                System.out.println("Table successfully created.");
            } else {
                System.out.println("Table failed to be created.");
            }
        } catch (SQLException e) {
            System.out.println("Database Connection Failed. " + e);
        }
    }

    public void create(Animal animal) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO animal(nome, raca) VALUES(?, ?)"
        );
        preparedStatement.setString(1, animal.getName());
        preparedStatement.setString(2, animal.getBreed());

        if(preparedStatement.execute()) {
            System.out.println("Pet successfully created.");
        } else {
            System.out.println("Pet failed to be created.");
        }
    }

    public void getAll() throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM animal"
        );

        ArrayList<Animal> animals = new ArrayList<Animal>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Animal animal = new Animal(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("raca")
            );
            animals.add(animal);

            System.out.println("------------------------------------");
            System.out.println("ID: " + animal.getId());
            System.out.println("Name:  " + animal.getName());
            System.out.println("Breed:  " + animal.getBreed());
        }
    }

    public void update(Animal animal) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE animal SET nome=?, raca=? WHERE id=?"
        );
        preparedStatement.setString(1, animal.getName());
        preparedStatement.setString(2, animal.getBreed());
        preparedStatement.setString(3, Integer.toString(animal.getId()));

        if(preparedStatement.execute()) {
            System.out.println("Pet successfully updated.");
        } else {
            System.out.println("Pet failed to be updated.");
        }
    }

    public void delete(int id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM animal WHERE id=?"
        );
        preparedStatement.setString(1, Integer.toString(id));

        if(preparedStatement.execute()) {
            System.out.println("Pet successfully deleted.");
        } else {
            System.out.println("Pet failed to be deleted.");
        }
    }
}
