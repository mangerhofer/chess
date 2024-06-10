package model;

public record UserData(String username, String password, String email) {
    public UserData setUser(String username) {
        return new UserData(username, this.password, this.email);
    }

//    public String toString() {
//        return new Gson().toJson(this);
//    }
}
