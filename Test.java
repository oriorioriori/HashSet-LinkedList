import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {
        String input = "i = 0; a type List; c type List; b type Set; b add 8; while (i < 5) {a add 5; b add 5; i = i + 1;} c add 5; c add 8; c remove 0";
        LinkedList<Token> tokens = Lexer.lex(input);
        try {
            Parser.parse(tokens);
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
        LinkedList<Token> testPoliz = Poliz.makePoliz(tokens);
        int i = 0;
        for (Token token : testPoliz) {
            System.out.println(i + " " + token);
            i++;
        }
        PolizCalculation.calculate(testPoliz);
    }
}
