package hacker;

public enum Result {
    WRONG_LOGIN("Wrong login!"),
    WRONG_PASSWORD("Wrong password!"),
    BAD_REQUEST("Bad request!"),
    EXCEPTION("Exception happened during login"),
    SUCCESS("Connection success!");

    final String result;

    Result(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
