package services.human;

public class Human {
    private int id, petId;
    private String name;
    private Gender gender;

    public Human(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public Human(int petId, String name, Gender gender) {
        this.petId = petId;
        this.name = name;
        this.gender = gender;
    }

    public Human(int id, int petId, String name, Gender gender) {
        this.id = id;
        this.petId = petId;
        this.name = name;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public int getPetId() {
        return petId;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }
}


