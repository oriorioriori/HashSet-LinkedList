import java.util.LinkedList;
import java.util.Queue;

public class Parser {
    private static Token token;
    private static Token tempVar;
    private static Queue<Token> tokens;

    public static void parse(Queue<Token> input) throws Exception {
        tokens = new LinkedList<>(input);

        while(!tokens.isEmpty()) {
            lang();
        }
    }

    private static void match() {
        token = tokens.peek();
    }

    private static void lang() throws Exception {
        expr();
    }

    private static void expr() throws Exception {
        try {
            whileExpr();
        }
        catch (Exception ex) {
            try {
                declarationExpr();
            }
            catch (Exception ex1) {
                try {
                    funcEcpr();
                }
                catch (Exception ex2) {
                    assignExpr();
                }
            }
        }
    }

    private static void funcEcpr() throws Exception {
        var();
        funcOp();
        arExpr();
        cOp();
    }

    private static void whileExpr() throws Exception {
        whileW();
        term();
        body();
    }

    private static void term() throws Exception {
        oBracket();
        boolExpr();
        cBracket();
    }

    private static void boolExpr() throws Exception {
        try {
            arExpr();
        }
        catch (Exception ex) {
            operand();
        }
        boolOp();
        try {
            arExpr();
        }
        catch (Exception ex) {
            operand();
        }
    }

    private static void operand() throws Exception {
        try {
            var();
        }
        catch (Exception ex) {
            try {
                number();
            }
            catch (Exception ex2) {
                bracketExpr();
            }
        }
    }

    private static void bracketExpr() throws Exception {
        oBracket();
        inBrackets();
        cBracket();
    }

    private static void inBrackets() throws Exception {
        try {
            bracketExpr();
        }
        catch (Exception ex) {
            arExpr();
        }
    }

    private static void arExpr() throws Exception {
        operand();
        while (true) {
            try {
                arOp();
                operand();
            } catch (Exception ex) {
                return;
            }
        }
    }

    private static void body() throws Exception {
        oBrace();
        bodyExpr();
        cBrace();
    }

    private static void bodyExpr() throws Exception {
        expr();
        while (true) {
            try {
                expr();
            } catch (Exception ex) {
                return;
            }
        }
    }

    private static void assignExpr() throws Exception {
        var();
        assignOp();
        try {
            arExpr();
        }
        catch (Exception ex) {
            operand();
        }
        cOp();
    }

    private static void declarationExpr() throws Exception {
        var();
        typeW();
        type();
        cOp();
    }

    private static void whileW() throws Exception {
        match();
        if (token.type != TokenType.WHILE_W) {
            throw new Exception("ошибка в методе whileW на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void typeW() throws Exception {
        match();
        if (token.type != TokenType.TYPE_W) {
            ((LinkedList)tokens).addFirst(tempVar);
            throw new Exception("ошибка в методе typeW на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void type() throws Exception {
        match();
        if (token.type != TokenType.TYPE) {
            throw new Exception("ошибка в методе type на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void oBracket() throws Exception {
        match();
        if (token.type != TokenType.O_BRACKET) {
            throw new Exception("ошибка в методе oBracket на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void cBracket() throws Exception {
        match();
        if (token.type != TokenType.C_BRACKET) {
            throw new Exception("ошибка в методе cBracket на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void boolOp() throws Exception {
        match();
        if (token.type != TokenType.BOOL_OP) {
            throw new Exception("ошибка в методе boolOp на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void funcOp() throws Exception {
        match();
        if (token.type != TokenType.FUNC_OP) {
            ((LinkedList)tokens).addFirst(tempVar);
            throw new Exception("ошибка в методе funcOp на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void var() throws Exception {
        match();
        if (token.type != TokenType.VAR) {
            throw new Exception("ошибка в методе var на токене " + token.type + " " + token.data);
        }
        tempVar = tokens.poll();
    }

    private static void number() throws Exception {
        match();
        if (token.type != TokenType.NUMBER) {
            throw new Exception("ошибка в методе number на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void arOp() throws Exception {
        match();
        if (token.type != TokenType.AR_OP) {
            throw new Exception("ошибка в методе arOp на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void oBrace() throws Exception {
        match();
        if (token.type != TokenType.O_BRACE) {
            throw new Exception("ошибка в методе oBrace на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void cBrace() throws Exception {
        match();
        if (token.type != TokenType.C_BRACE) {
            throw new Exception("ошибка в методе cBrace на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void assignOp() throws Exception {
        match();
        if (token.type != TokenType.ASSIGN_OP) {
            throw new Exception("ошибка в методе assignOp на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

    private static void cOp() throws Exception {
        match();
        if (token.type != TokenType.C_OP) {
            throw new Exception("ошибка в методе cOp на токене " + token.type + " " + token.data);
        }
        tokens.remove();
    }

}
