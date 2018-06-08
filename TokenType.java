public enum TokenType {
    FUNC_OP("add|remove|get|contains"),
    TYPE_W("type"), TYPE("Set|List"),
    WHILE_W("while"),
    VAR("[a-z][a-zA-Z0-9_]*"),
    NUMBER("0|[1-9][0-9]*"),
    BOOL_OP("<|>|<=|>=|==|!="),
    ASSIGN_OP("="),
    C_OP(";"),
    AR_OP("[*|/|+|-]"),
    O_BRACE("\\{"),
    C_BRACE("\\}"),
    O_BRACKET("\\("),
    C_BRACKET("\\)"),
    GOTO(""),
    GOTO_INDEX(""),
    BOOL("true|false");

    public final String pattern;

    private TokenType(String pattern) {
        this.pattern = pattern;
    }
}
