import animal.AnimalDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        AnimalDAO animalDao = new AnimalDAO();
        if(!animalDao.doesTableExist()) {
            animalDao.createTable();
        }

        int selectedOption;

        do {
            Scanner reader = new Scanner(System.in);
            System.out.print("Digita o número da opção: ");
            selectedOption = reader.nextInt();
        } while(selectedOption != 9);

        System.out.println("Obrigado, volte sempre.");
    }
}
