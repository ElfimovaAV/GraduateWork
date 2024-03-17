package ru.gb.backend.models;

public enum DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    private static final DayOfWeek[] days = DayOfWeek.values();

    public static DayOfWeek getDayOfWeek(int i) {
        return days[i];
    }

    public static int daysGetLength() {
        return days.length;
    }
}
