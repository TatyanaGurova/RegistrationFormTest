package models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Month {

    January("January", 1),
    February("February", 2),
    March("March", 3),
    April("April", 4),
    May("May", 5),
    June("June", 6),
    July("July", 7),
    August("August", 8),
    September("September", 9),
    October("October", 10),
    November("November", 11),
    December("December", 12);

    private final String engName;
    private final int number;

    public static Month getMonthByName(String monthName) {
        for (Month month : values()) {
            if (month.engName.equals(monthName)) {
                return month;
            }
        }
        return null;
    }

    public static Month getMonthByNumber(int monthNumber) {
        for (Month month : values()) {
            if (month.number == monthNumber) {
                return month;
            }
        }
        return null;
    }
}
