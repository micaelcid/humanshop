package animal;

import java.sql.Timestamp;

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

    public String getName() {
        return nome;
    }

    public String getBreed() {
        return raca;
    }
}
