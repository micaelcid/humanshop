package services.animal;

public class Animal {
    private int id;
    private String nome, raca;

    public Animal(String nome, String raca) {
        this.nome = nome;
        this.raca = raca;
    }

    public Animal(int id, String nome, String raca) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }
}