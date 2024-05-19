package spring_security.spring_security.DTO;

public record CreateUserRequest(String username, String password, String roles) {
}
