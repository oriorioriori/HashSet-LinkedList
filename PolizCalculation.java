import java.util.*;

public class PolizCalculation {
    public static Map<String, String> types = new HashMap<>();
    public static Map<String, Object> values  = new HashMap<>();

    public static void calculate(LinkedList<Token> poliz) {
        Stack<Token> stack = new Stack<>();
        for (int i = 0; i < poliz.size(); i++) {
            Token token = poliz.get(i);

            if (token.type == TokenType.VAR || token.type == TokenType.NUMBER || token.type == TokenType.GOTO_INDEX || token.type == TokenType.TYPE) {
                stack.add(token);
            }

            if (token.type == TokenType.TYPE_W) {
                Token type = stack.pop();
                Token var = stack.pop();
                if (!values.containsKey(var.data)) {
                    types.put(var.data, type.data);
                    if (!type.data.equals("Number")) {
                        values.put(var.data, null);
                    } else {
                        values.put(var.data, 0);
                    }
                }
            }

            if (token.type == TokenType.FUNC_OP) {
                Token t2 = stack.pop();
                Token t1 = stack.pop();
                if (values.get(t1.data) == null) {
                    if (types.get(t1.data).equals("List")) {
                        values.put(t1.data, new CustomLinkedList());
                    }
                    else if (types.get(t1.data).equals("Set")) {
                        values.put(t1.data, new CustomHashSet());
                    }
                }
                if (types.get(t1.data).equals("List")) {
                    if (t2.type == TokenType.NUMBER) {
                        if (token.data.equals("add")) {
                            ((CustomLinkedList) values.get(t1.data)).add(t2.data);
                        } else if (token.data.equals("get")) {
                            stack.add(new Token(TokenType.NUMBER, (String) ((CustomLinkedList) values.get(t1.data)).get(Integer.parseInt(t2.data))));
                        } else if (token.data.equals("remove")) {
                            ((CustomLinkedList) values.get(t1.data)).remove(Integer.parseInt(t2.data));
                        } else if (token.data.equals("contains")) {
                            ((CustomLinkedList) values.get(t1.data)).contains(t2.data);
                        }
                    }
                    else if (t2.type == TokenType.VAR) {
                        if (token.data.equals("add")) {
                            ((CustomLinkedList) values.get(t1.data)).add(values.get(t2.data));
                        } else if (token.data.equals("get")) {
                            stack.add(new Token(TokenType.NUMBER, (String) ((CustomLinkedList) values.get(t1.data)).get((Integer)values.get(t2.data))));
                        } else if (token.data.equals("remove")) {
                            ((CustomLinkedList) values.get(t1.data)).remove((Integer)(values.get(t2.data)));
                        } else if (token.data.equals("contains")) {
                            stack.add(new Token(TokenType.BOOL, Boolean.toString(((CustomLinkedList) values.get(t1.data)).contains(values.get(t2.data)))));
                        }
                    }
                }
                else if (types.get(t1.data).equals("Set")) {
                    if (t2.type == TokenType.NUMBER) {
                        if (token.data.equals("add")) {
                            ((CustomHashSet) values.get(t1.data)).add(t2.data);
                        } else if (token.data.equals("remove")) {
                            ((CustomHashSet) values.get(t1.data)).remove(Integer.parseInt(t2.data));
                        } else if (token.data.equals("contains")) {
                            ((CustomHashSet) values.get(t1.data)).contains(t2.data);
                        }
                    }
                    else if (t2.type == TokenType.VAR) {
                        if (token.data.equals("add")) {
                            ((CustomHashSet) values.get(t1.data)).add(values.get(t2.data));
                        } else if (token.data.equals("remove")) {
                            ((CustomHashSet) values.get(t1.data)).remove(values.get(t2.data));
                        } else if (token.data.equals("contains")) {
                            stack.add(new Token(TokenType.BOOL, Boolean.toString(((CustomHashSet) values.get(t1.data)).contains(values.get(t2.data)))));
                        }
                    }
                }
            }

            if (token.type == TokenType.BOOL_OP) {
                int a1, a2;
                Token t2 = stack.pop();
                Token t1 = stack.pop();
                if (t1.type == TokenType.VAR) {
                    a1 = (Integer)values.get(t1.data);
                }
                else {
                    a1 = Integer.parseInt(t1.data);
                }
                if (t2.type == TokenType.VAR) {
                    a2 = (Integer)values.get(t2.data);
                }
                else {
                    a2 = Integer.parseInt(t2.data);
                }
                if (token.data.equals("==")) {
                    if (a1 == a2) {
                        stack.add(new Token(TokenType.BOOL, "true"));
                    }
                    else {
                        stack.add(new Token(TokenType.BOOL, "false"));
                    }
                }
                if (token.data.equals("<=")) {
                    if (a1 <= a2) {
                        stack.add(new Token(TokenType.BOOL, "true"));
                    }
                    else {
                        stack.add(new Token(TokenType.BOOL, "false"));
                    }
                }
                if (token.data.equals(">=")) {
                    if (a1 >= a2) {
                        stack.add(new Token(TokenType.BOOL, "true"));
                    }
                    else {
                        stack.add(new Token(TokenType.BOOL, "false"));
                    }
                }
                if (token.data.equals("<")) {
                    if (a1 < a2) {
                        stack.add(new Token(TokenType.BOOL, "true"));
                    }
                    else {
                        stack.add(new Token(TokenType.BOOL, "false"));
                    }
                }
                if (token.data.equals(">")) {
                    if (a1 > a2) {
                        stack.add(new Token(TokenType.BOOL, "true"));
                    }
                    else {
                        stack.add(new Token(TokenType.BOOL, "false"));
                    }
                }
                if (token.data.equals("!=")) {
                    if (a1 != a2) {
                        stack.add(new Token(TokenType.BOOL, "true"));
                    }
                    else {
                        stack.add(new Token(TokenType.BOOL, "false"));
                    }
                }
            }

            if (token.type == TokenType.ASSIGN_OP) {
                int a2;
                Token t1, t2;
                if (!stack.empty())
                    t2 = stack.pop();
                else
                    continue;
                if (!stack.empty())
                    t1 = stack.pop();
                else
                    continue;
                if (t2.type == TokenType.VAR) {
                    a2 = (Integer)values.get(t2.data);
                }
                else {
                    a2 = Integer.parseInt(t2.data);
                }
                values.put(t1.data, a2);
            }

            if (token.type == TokenType.AR_OP) {
                int a1, a2;
                Token t1, t2;
                if (!stack.empty())
                    t2 = stack.pop();
                else
                    continue;
                if (!stack.empty())
                    t1 = stack.pop();
                else
                    continue;
                if (t1.type == TokenType.VAR) {
                    a1 = (Integer)values.get(t1.data);
                }
                else {
                    a1 = Integer.parseInt(t1.data);
                }
                if (t2.type == TokenType.VAR) {
                    a2 = (Integer)values.get(t2.data);
                }
                else {
                    a2 = Integer.parseInt(t2.data);
                }
                if (token.data.equals("*")) {
                    stack.add(new Token(TokenType.NUMBER, Integer.toString(a1 * a2)));
                }
                if (token.data.equals("/")) {
                    stack.add(new Token(TokenType.NUMBER, Integer.toString(a1 / a2)));
                }
                if (token.data.equals("+")) {
                    stack.add(new Token(TokenType.NUMBER, Integer.toString(a1 + a2)));
                }
                if (token.data.equals("-")) {
                    stack.add(new Token(TokenType.NUMBER, Integer.toString(a1 - a2)));
                }
            }

            if (token.type == TokenType.GOTO) {
                Token index = stack.pop();

                if (token.data.equals("!F")) {
                    Token res = stack.pop();
                    if (res.data.equals("true")) {
                        continue;
                    }
                    else {
                        i = Integer.parseInt(index.data) - 1;
                    }
                }
                else {
                    i = Integer.parseInt(index.data) - 1;
                }
            }
        }
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
