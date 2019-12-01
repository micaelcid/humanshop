package services.humano;

public class Humano {
    private int id, animalId;
    private String nome;
    private Sexo sexo;

    public Humano(int animalId, String nome, Sexo sexo) {
        this.animalId = animalId;
        this.nome = nome;
        this.sexo = sexo;
    }

    public Humano(int id, int animalId, String nome, Sexo sexo) {
        this.id = id;
        this.animalId = animalId;
        this.nome = nome;
        this.sexo = sexo;
    }

    public int getId() {
        return id;
    }

    public int getAnimalId() {
        return animalId;
    }

    public String getNome() {
        return nome;
    }

    public Sexo getSexo() {
        return sexo;
    }
}


