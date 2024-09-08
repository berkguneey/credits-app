package com.project.credits.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtil {

    public static LocalDate calculateDueDate(LocalDate dueDate) {
        DayOfWeek dayOfWeek = dueDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            dueDate = dueDate.plusDays(2); // Move to Monday
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            dueDate = dueDate.plusDays(1); // Move to Monday
        }
        return dueDate;
    }

}
