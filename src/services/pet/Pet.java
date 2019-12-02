package services.pet;

public class Pet {
    private int id;
    private String name, breed;

    public Pet(String name, String breed) {
        this.name = name;
        this.breed = breed;
    }

    public Pet(int id, String name, String breed) {
        this.id = id;
        this.name = name;
        this.breed = breed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }
}
