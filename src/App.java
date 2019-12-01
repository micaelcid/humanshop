import services.animal.*;
import services.humano.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        AnimalDAO animalDao = new AnimalDAO();
        HumanoDAO humanoDao = new HumanoDAO();

        if(!animalDao.doesTableExist()) {
            animalDao.createTable();
        }

        if(!humanoDao.doesTableExist()) {
            humanoDao.createTable();
        }

        int selectedOption;

        Scanner optionReader = new Scanner(System.in);
        Scanner reader = new Scanner(System.in);
        while(true) {
            System.out.println("1 - Cadastrar animal");
            System.out.println("9 - Sair");
            System.out.print("Digita o número da opção: ");
            selectedOption = optionReader.nextInt();
            if(selectedOption != 9) {
                switch (selectedOption) {
                    case 1:

                        System.out.print("Digita o nome: ");
                        String nome = reader.nextLine();
                        System.out.print("Digita a raça: ");
                        String raca = reader.nextLine();

                        Animal animal = new Animal(nome, raca);
                        animalDao.create(animal);

                        System.out.println("Animal cadastrado com sucesso");
                        break;
                }
            }
            else {
                break;
            }
        }

        System.out.println("Obrigado, volte sempre.");
    }
}
