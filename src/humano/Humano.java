package humano;

public class Humano {
    int id;
    String nome;
    Sexo sexo;

    public Humano(String nome, Sexo sexo) {
        this.nome = nome;
        this.sexo = sexo;
    }

    public Humano(int id, String nome, Sexo sexo) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Sexo getSexo() {
        return sexo;
    }
}


