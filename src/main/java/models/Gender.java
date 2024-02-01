package models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Gender {

    Male("Male"),
    Female("Female"),
    Other("Other");

    @Getter
    private final String gender;

    public static Gender convertToGender(String gender) {
        for (Gender g : values()) {
            if (g.gender.equalsIgnoreCase(gender)) {
                return g;
            }
        }
        return null;
    }
}
