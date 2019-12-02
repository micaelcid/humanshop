package services.pet;

import database.ConnectionFactory;
import services.Dao;

import java.sql.*;
import java.util.ArrayList;

public class PetDAO implements Dao<Pet> {
    private Connection connection;

    public PetDAO() {
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
                    "pet",
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
                    "CREATE TABLE IF NOT EXISTS pet(" +
                            "id SERIAL PRIMARY KEY," +
                            "name VARCHAR(255) NOT NULL," +
                            "breed VARCHAR(255)" +
                            ")"
            );
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Database Connection Failed. " + e);
        }
    }

    @Override
    public void create(Pet pet) throws SQLException{
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO pet(name, breed) VALUES(?, ?)"
            );
            preparedStatement.setString(1, pet.getName());
            preparedStatement.setString(2, pet.getBreed());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Failed to create pet. " + e);
        }
    }

    @Override
    public Pet getById(int id) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from pet WHERE id=?;"
            );
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Pet pet = new Pet(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("breed")
                );
                return pet;
            }
        } catch(SQLException e) {
            System.out.println("Failed to get pet. " + e);
        }

        return null;
    }

    @Override
    public ArrayList<Pet> getAll() throws SQLException{
        ArrayList<Pet> pets = new ArrayList<Pet>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM pet"
            );

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Pet pet = new Pet(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("breed")
                );
                pets.add(pet);
            }
        } catch(SQLException e) {
            System.out.println("Failed to get all pets. " + e);
        }

        return pets;
    }

    public Pet getByHumanId(int id) throws SQLException{
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select A.id, A.name, A.breed from pet A join human H ON A.id = H.pet_id WHERE H.id=?;"
            );
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Pet pet = new Pet(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("breed")
                );
                return pet;
            }
        } catch(SQLException e) {
            System.out.println("Failed to get pet by human id. " + e);
        }

        return null;
    }

    @Override
    public void update(Pet pet) throws SQLException{
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE pet SET name=?, breed=? WHERE id=?"
            );
            preparedStatement.setString(1, pet.getName());
            preparedStatement.setString(2, pet.getBreed());
            preparedStatement.setInt(3, pet.getId());
            preparedStatement.execute();
        } catch(SQLException e) {
            System.out.println("Failed to update pet. " + e);
        }
    }

    @Override
    public void delete(int id) throws SQLException{
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM human WHERE pet_id=?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(
                    "DELETE FROM pet WHERE id=?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch(SQLException e) {
            System.out.println("Failed to delete pet. " + e);
        }
    }
}
