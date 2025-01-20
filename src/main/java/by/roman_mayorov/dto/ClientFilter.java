package by.roman_mayorov.dto;

public class ClientFilter {

    private final int age;
    private final String first_name;
    private final int limit;

    public ClientFilter(int age, String first_name, int limit) {
        this.age = age;
        this.first_name = first_name;
        this.limit = limit;
    }

    public int getAge() {
        return age;
    }

    public int getLimit() {
        return limit;
    }

    public String getFirst_name() {
        return first_name;
    }
}
