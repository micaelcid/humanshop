import services.pet.*;
import services.human.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        PetDAO petDao = new PetDAO();
        HumanDAO humanDao = new HumanDAO();

        if(!petDao.doesTableExist()) {
            petDao.createTable();
        }

        if(!humanDao.doesTableExist()) {
            humanDao.createTable();
        }

        Pet pet;
        ArrayList<Pet> pets;
        Human human;
        ArrayList<Human> humans;
        int selectedOption, id;
        String name, breed, genderStr;
        Gender gender;

        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("------------- MENU -------------");
            System.out.println("1 - Cadastrar pet");
            System.out.println("2 - Listar pets");
            System.out.println("3 - Consultar pet");
            System.out.println("4 - Consultar pet por humano");
            System.out.println("5 - Atualizar pet");
            System.out.println("6 - Deletar pet");
            System.out.println("7 - Cadastrar humano");
            System.out.println("8 - Listar humanos");
            System.out.println("9 - Consultar humano");
            System.out.println("10 - Listar humanos por pet");
            System.out.println("11 - Atualizar humano");
            System.out.println("12 - Deletar humano");
            System.out.println("99 - Sair");
            System.out.print("Digita o número da opção: ");
            selectedOption = scanner.nextInt();
            scanner.nextLine();
            if(selectedOption != 99) {
                switch (selectedOption) {
                    case 1:
                        System.out.print("Digita o nome: ");
                        name = scanner.nextLine();
                        System.out.print("Digita a raça: ");
                        breed = scanner.nextLine();
                        if(name != "" && breed != "") {
                            pet = new Pet(name, breed);
                            petDao.create(pet);

                            System.out.println("Pet cadastrado com sucesso.");
                        } else {
                            System.out.println("Por favor, digite um nome e uma raça.");
                        }
                        break;
                    case 2:
                        pets = petDao.getAll();
                        if(pets.size() > 0) {
                            System.out.println("-----------------------");
                            for(Pet p : pets) {
                                System.out.println("ID: " + p.getId());
                                System.out.println("Nome: " + p.getName());
                                System.out.println("Raca: " + p.getBreed());
                                System.out.println("-----------------------");
                            }
                        } else {
                           System.out.println("Nenhum pet encontrado.");
                        }

                        break;
                    case 3:
                        System.out.print("Digita o ID do pet: ");
                        id = scanner.nextInt();
                        pet = petDao.getById(id);
                        if(pet != null) {
                            System.out.println("-----------------------");
                            System.out.println("ID: " + pet.getId());
                            System.out.println("Nome: " + pet.getName());
                            System.out.println("Raca: " + pet.getBreed());
                            System.out.println("-----------------------");
                        } else {
                            System.out.println("Pet não encontrado.");
                        }
                        break;
                    case 4:
                        System.out.print("Digita o ID do humano: ");
                        id = scanner.nextInt();
                        pet = petDao.getByHumanId(id);
                        if(pet != null) {
                            System.out.println("-----------------------");
                            System.out.println("ID: " + pet.getId());
                            System.out.println("Nome: " + pet.getName());
                            System.out.println("Raca: " + pet.getBreed());
                            System.out.println("-----------------------");
                        } else {
                            System.out.println("Pet não encontrado.");
                        }
                        break;
                    case 5:
                        System.out.print("Digita o ID do pet: ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        pet = petDao.getById(id);
                        if(pet != null) {
                            System.out.print("Digita o nome: ");
                            name = scanner.nextLine();
                            System.out.print("Digita a raça: ");
                            breed = scanner.nextLine();
                            if(name != "" && breed != "") {
                                pet = new Pet(id, name, breed);
                                petDao.update(pet);
                                System.out.println("Pet atualizado com sucesso.");
                            } else {
                                System.out.println("Por favor, digite o ID do pet, um nome e uma raça.");
                            }
                        } else {
                            System.out.println("Pet não encontrado.");
                        }
                        break;
                    case 6:
                        System.out.print("Digita o ID do pet: ");
                        id = scanner.nextInt();
                        pet = petDao.getById(id);
                        if(pet != null) {
                            petDao.delete(id);
                            System.out.println("Pet deletado com sucesso.");
                        } else {
                            System.out.println("Pet não encontrado.");
                        }
                        break;
                    case 7:
                        System.out.print("Digita o ID do dono (pet): ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        pet = petDao.getById(id);
                        if(pet != null) {
                            System.out.print("Digita o nome: ");
                            name = scanner.nextLine();
                            System.out.print("Digita o sexo (F para femea e M para macho): ");
                            genderStr = scanner.nextLine();
                            if(name != "" && (genderStr.equals("M") || genderStr.equals("F"))){
                                gender = Gender.valueOf(genderStr);
                                human = new Human(id, name, gender);
                                humanDao.create(human);
                                System.out.println("Humano criado com sucesso.");
                            } else {
                                System.out.println("Por favor, digite o nome e o sexo corretamente.");
                            }
                        }  else {
                            System.out.println("O pet com ID " + id + " não existe.");
                        }
                        break;
                    case 8:
                        humans = humanDao.getAll();
                        if(humans.size() > 0) {
                            System.out.println("-----------------------");
                            for(Human h : humans) {
                                System.out.println("ID: " + h.getId());
                                System.out.println("Pet ID: " + h.getPetId());
                                System.out.println("Nome: " + h.getName());
                                System.out.println("Sexo: " + h.getGender());
                                System.out.println("-----------------------");
                            }
                        } else {
                            System.out.println("Nenhum humano encontrado.");
                        }
                        break;
                    case 9:
                        System.out.print("Digita o ID do humano: ");
                        id = scanner.nextInt();
                        human = humanDao.getById(id);
                        if(human != null) {
                            System.out.println("-----------------------");
                            System.out.println("ID: " + human.getId());
                            System.out.println("Pet ID: " + human.getPetId());
                            System.out.println("Nome: " + human.getName());
                            System.out.println("Sexo: " + human.getGender());
                            System.out.println("-----------------------");
                        } else {
                            System.out.println("Humano não encontrado.");
                        }
                        break;
                    case 10:
                        System.out.print("Digita o ID do pet: ");
                        id = scanner.nextInt();
                        humans = humanDao.getAllByPetId(id);
                        if(humans.size() > 0) {
                            System.out.println("-----------------------");
                            for(Human h : humans) {
                                System.out.println("ID: " + h.getId());
                                System.out.println("Pet ID: " + h.getPetId());
                                System.out.println("Nome: " + h.getName());
                                System.out.println("Sexo: " + h.getGender());
                                System.out.println("-----------------------");
                            }
                        } else {
                            System.out.println("Nenhum humano encontrado.");
                        }
                        break;
                    case 11:
                        System.out.print("Digita o ID do humano: ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        human = humanDao.getById(id);
                        if(human != null) {
                            System.out.print("Digita o ID do dono (pet): ");
                            int petId = scanner.nextInt();
                            scanner.nextLine();
                            pet = petDao.getById(petId);
                            if(pet != null) {
                                System.out.print("Digita o nome: ");
                                name = scanner.nextLine();
                                System.out.print("Digita o sexo (F para femea e M para macho): ");
                                genderStr = scanner.nextLine();
                                if(name != "" && (genderStr.equals("M") || genderStr.equals("F"))){
                                    gender = Gender.valueOf(genderStr);
                                    human = new Human(id, petId, name, gender);
                                    humanDao.update(human);
                                    System.out.println("Humano atualizado com sucesso.");
                                } else {
                                    System.out.println("Por favor, digite o nome e o sexo corretamente.");
                                }
                            } else {
                                System.out.println("Pet não encontrado.");
                            }
                        }  else {
                            System.out.println("Humano não encontrado.");
                        }
                        break;
                    case 12:
                        System.out.print("Digita o ID do humano: ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        human = humanDao.getById(id);
                        if(human != null) {
                            humanDao.delete(id);
                            System.out.println("Humano deletado com sucesso.");
                        }  else {
                            System.out.println("Humano não encontrado.");
                        }
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
            else {
                break;
            }
        }

        System.out.println("Obrigado, volte sempre.");
    }
}
