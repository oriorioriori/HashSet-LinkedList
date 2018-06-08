import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Poliz {
    private static LinkedList<Token> poliz = new LinkedList<>();

    public static LinkedList<Token> makePoliz(Queue<Token> input) {
        while (!input.isEmpty()) {
            Token token = input.peek();
            if (token.type != TokenType.WHILE_W) {
                makePolizFromExpr(input);
            }
            else {
                makePolizFromWhile(input);
            }
        }

        return poliz;
    }

    private static void makePolizFromWhile(Queue<Token> input) {
        Queue<Token> boolExpr = new LinkedList<>();
        input.poll();
        Token token = input.poll();
        int index = poliz.size();
        while (token.type != TokenType.O_BRACE) {
            boolExpr.add(token);
            token = input.poll();
        }

        makePolizFromExpr(boolExpr);
        poliz.add(new Token(TokenType.GOTO_INDEX, Integer.toString(p(poliz.size(), input))));
        poliz.add(new Token(TokenType.GOTO, "!F"));

        Queue<Token> expr = new LinkedList<>();
        token = input.poll();
        while (token.type != TokenType.C_BRACE) {
            if (token.type == TokenType.WHILE_W) {
                makePolizFromExpr(expr);
                makePolizFromWhile(input);
            }
            if (token.type != TokenType.WHILE_W)
                expr.add(token);
            token = input.poll();
        }
        makePolizFromExpr(expr);

        poliz.add(new Token(TokenType.GOTO_INDEX, Integer.toString(index)));
        poliz.add(new Token(TokenType.GOTO, "!"));
    }

    private static int p(int size, Queue<Token> tokens) {
        int p = size;
        int i = 1;

        Queue<Token> newtokens = new LinkedList<>(tokens);
        Token newtoken = newtokens.poll();

        while (i > 0){
            if (newtoken.type == TokenType.WHILE_W) {
                i++;
                p--;
            }
            if (newtoken.type == TokenType.C_BRACE) {
                i--;
            }
            newtoken = newtokens.poll();
            if (newtoken.type != TokenType.C_OP) {
                p++;
            }
        }
        p+=3;

        return p;
    }

    private static void makePolizFromExpr(Queue<Token> input) {
        Stack<Token> stack = new Stack<>();

        while (!input.isEmpty()) {
            Token token = input.peek();

            if (token.type == TokenType.WHILE_W) {
                break;
            }

            if (token.type == TokenType.TYPE_W) {
                stack.add(token);
            }

            if (token.type == TokenType.TYPE) {
                poliz.add(token);
                poliz.add(stack.pop());
            }

            token = input.poll();

            //Если лексема является числом или переменной, добавляем ее в ПОЛИЗ-массив.
            if (token.type == TokenType.VAR || token.type == TokenType.NUMBER) {
                poliz.add(token);
            }

            //Если лексема является бинарной операцией, тогда:
            if (token.type == TokenType.AR_OP || token.type == TokenType.BOOL_OP || token.type == TokenType.ASSIGN_OP || token.type == TokenType.FUNC_OP) {
                if (!stack.empty()) {
                    while (getPriorOfOp(token.data) >= getPriorOfOp(stack.peek().data)) {
                        poliz.add(stack.pop());
                    }
                }
                stack.push(token);
            }

            //Если лексема является открывающей скобкой, помещаем ее в стек.
            if (token.type == TokenType.O_BRACKET) {
                stack.push(token);
            }

            if (token.type == TokenType.C_BRACKET) {
                if (!stack.empty()) {
                    while (!stack.empty() && stack.peek().type != TokenType.O_BRACKET) {
                        poliz.add(stack.pop());
                    }
                    if (!stack.empty() && stack.peek().type == TokenType.O_BRACKET) {
                        stack.pop();
                    }
                }
            }

            if (token.type == TokenType.C_OP) {
                while (!stack.empty()) {
                    poliz.add(stack.pop());
                }
            }
        }

        while (!stack.empty()) {
            poliz.add(stack.pop());
        }
    }

    private static int getPriorOfOp(String op) {
        if (op.equals("*") || op.equals("/"))
            return 0;
        else if (op.equals("*") || op.equals("/"))
            return 1;
        else if (op.equals("+") || op.equals("-"))
            return 2;
        else if (op.equals(">") || op.equals(">=") || op.equals("<") || op.equals("<=") || op.equals("==") || op.equals("!="))
            return 3;
        else
            return 4;
    }
}
