import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lexer {
    public static LinkedList<Token> lex(String input) {
        LinkedList<Token> tokens = new LinkedList<Token>();
        boolean flag; //флаг изменения type
        TokenType tempo = null; //текущее значение type
        String s = ""; //текущее значение data
        int i = 0; //индекс перемещения по строке
        while (input.length() > i) {
            flag = false;
            if (input.charAt(i) == ' ' || input.charAt(i) == '\n' || input.charAt(i) == '\t') { //если пробел, перенос строки или табуляция, то игнорируем
                i++;
                if (tempo != null) {
                    tokens.add(new Token(tempo, s));
                    tempo = null;
                    s = "";
                }
                continue;
            }
            s += input.charAt(i); //считываем следующий символ

            for (TokenType t : TokenType.values()) { //определяем тип токена
                Pattern pattern = Pattern.compile(t.pattern);
                Matcher matcher = pattern.matcher(s);
                if (matcher.matches()) {
                    tempo = t;
                    flag = true; //если определили успешно, то изменяем текущее значение токена и идем дальше
                    break;
                }
            }
            if (tempo != null && !flag) { //если тип токена не определился при считывании нового символа, то возвращаемся назад на 1 символ и добавляем токен
                s = s.substring(0, s.length()-1);
                i--;
                tokens.add(new Token(tempo, s));
                tempo = null;
                s = "";
            }
            i++;
        }
        tokens.add(new Token(tempo, s)); //добавляем последний токен
        return tokens;
    }


}