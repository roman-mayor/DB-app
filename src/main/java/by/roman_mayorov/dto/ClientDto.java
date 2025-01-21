package by.roman_mayorov.dto;

public class ClientDto {

    private final Long id;
    private final String description;

    private ClientDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    public static ClientDto getInstance(Long id, String description) {
        return new ClientDto(id, description);
    }
}
