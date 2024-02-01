package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDataModel {

    private String firstName = "Мария";
    private String lastName = "Иванова";
    private String email = "test@test.ru";
    private Gender gender = Gender.Female;
    private String mobilePhone = "1234567890";
    private Calendar birthDate = new GregorianCalendar(1991, Calendar.FEBRUARY, 3);
    private List<String> subjects = Arrays.asList("Biology", "English");
    private String picturePath = "otkrytki.JPG";
    private String currentAddress = "Russia, Penza";
    private String state = "Haryana";
    private String city = "Panipat";
}
