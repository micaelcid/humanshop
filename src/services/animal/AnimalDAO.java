package services.animal;

import database.ConnectionFactory;
import services.Dao;

import java.sql.*;
import java.util.ArrayList;

public class AnimalDAO implements Dao<Animal> {
    private Connection connection;

    public AnimalDAO() {
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
                    "animal",
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

    @Override
    public void create(Animal animal) throws SQLException{
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO animal(nome, raca, created_on) VALUES(?, ?, NOW())"
            );
            preparedStatement.setString(1, animal.getNome());
            preparedStatement.setString(2, animal.getRaca());
        } catch (SQLException e) {
            System.out.println("Failed to create animal. " + e);
        }
    }

    @Override
    public Animal getById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from animal WHERE id=?;"
        );
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Animal animal = new Animal(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("raca")
            );
            return animal;
        }

        return null;
    }

    @Override
    public ArrayList<Animal> getAll() throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM animal"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Animal> animals = new ArrayList<Animal>();
        while(resultSet.next()) {
            Animal animal = new Animal(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("raca")
            );
            animals.add(animal);
        }

        return animals;
    }

    public Animal getByHumanId(int id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select A.id, A.nome, A.raca from animal A join humano H ON A.id = H.animal_id WHERE H.id=?;"
        );
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Animal animal = new Animal(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("raca")
            );
            return animal;
        }

        return null;
    }

    @Override
    public void update(Animal animal) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE animal SET nome=?, raca=? WHERE id=?"
        );
        preparedStatement.setString(1, animal.getNome());
        preparedStatement.setString(2, animal.getRaca());
        preparedStatement.setString(3, Integer.toString(animal.getId()));

        if(preparedStatement.execute()) {
            System.out.println("Pet successfully updated.");
        } else {
            System.out.println("Pet failed to be updated.");
        }
    }

    @Override
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
