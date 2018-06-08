public class Token {
    public final TokenType type;
    public String data;

    public Token(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }

    public String toString() {
        return type + " " + data;
    }
}