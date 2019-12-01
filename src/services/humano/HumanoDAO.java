package services.humano;

import database.ConnectionFactory;
import services.Dao;

import java.sql.*;
import java.util.ArrayList;

public class HumanoDAO implements Dao<Humano> {
    private Connection connection;

    public HumanoDAO() {
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
                    "humano",
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
                    "CREATE TABLE IF NOT EXISTS humano(" +
                            "id SERIAL PRIMARY KEY," +
                            "animal_id INT NOT NULL REFERENCES animal(id)," +
                            "sexo VARCHAR (1)," +
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
    public void create(Humano humano) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO humano(animal_id, nome, sexo, created_on) VALUES(?, ?, ?, NOW())"
        );
        preparedStatement.setInt(1, humano.getAnimalId());
        preparedStatement.setString(2, humano.getNome());
        preparedStatement.setString(3, humano.getSexo().name());

        if(preparedStatement.execute()) {
            System.out.println("Human successfully created.");
        } else {
            System.out.println("Human failed to be created.");
        }
    }

    public Humano getById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM humano WHERE id=?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Humano humano = new Humano(
                    resultSet.getInt("id"),
                    resultSet.getInt("animal_id"),
                    resultSet.getString("nome"),
                    Sexo.valueOf(resultSet.getString("sexo"))
            );
            return humano;
        }

        return null;
    }

    @Override
    public ArrayList<Humano> getAll() throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM humano"
        );

        ArrayList<Humano> humanos = new ArrayList<Humano>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Humano humano = new Humano(
                    resultSet.getInt("id"),
                    resultSet.getInt("animal_id"),
                    resultSet.getString("nome"),
                    Sexo.valueOf(resultSet.getString("sexo"))
            );
            humanos.add(humano);
        }

        return humanos;
    }

    public ArrayList<Humano> getAllByAnimalId(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM humano WHERE animal_id=?"
        );
        preparedStatement.setInt(1, id);
        ArrayList<Humano> humanos = new ArrayList<Humano>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Humano humano = new Humano(
                    resultSet.getInt("id"),
                    resultSet.getInt("animal_id"),
                    resultSet.getString("nome"),
                    Sexo.valueOf(resultSet.getString("sexo"))
            );
            humanos.add(humano);
        }

        return humanos;
    }

    @Override
    public void update(Humano humano) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE humano SET nome=? WHERE id=?"
        );
        preparedStatement.setString(1, humano.getNome());
        preparedStatement.setString(2, Integer.toString(humano.getId()));

        if(preparedStatement.execute()) {
            System.out.println("Human successfully updated.");
        } else {
            System.out.println("Human failed to be updated.");
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM humano WHERE id=?"
        );
        preparedStatement.setString(1, Integer.toString(id));

        if(preparedStatement.execute()) {
            System.out.println("Human successfully deleted.");
        } else {
            System.out.println("Human failed to be deleted.");
        }
    }
}
