package dtos;

import java.util.Objects;

public class PetDTO {
    private String name;

    private String type;

    private String race;

    private String owner;

    public PetDTO(String name, String type, String race) {this(name, type, race, null);}

    public PetDTO(String name, String type, String race, String owner) {
        this.name = name;
        this.type = type;
        this.race = race;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetDTO petDTO = (PetDTO) o;
        return Objects.equals(name, petDTO.name) && Objects.equals(type, petDTO.type) && Objects.equals(race, petDTO.race) && Objects.equals(owner, petDTO.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, race, owner);
    }
}
