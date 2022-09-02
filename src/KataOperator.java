public enum KataOperator {
    PLUS(43,"+"),
    MINUS(45, "-"),
    MULTIPLY(42, "*"),
    DIVIDE(47, "/");

    private final String translation;
    private final int code;

    KataOperator(int code, String translation) {
        this.code = code;
        this.translation = translation;
    }

    public int getCode() {
        return code;
    }

    public String toRegexp() {
        if (translation.equals("+") || translation.equals("*"))
        return "\\"+ translation;
        else return translation;
    }

    public int getResult(int firstArgument, int secondArgument) {
        return switch (this.name()) {
            case "PLUS" -> firstArgument + secondArgument;
            case "MINUS" -> firstArgument - secondArgument;
            case "MULTIPLY" -> firstArgument * secondArgument;
            case "DIVIDE" -> firstArgument / secondArgument;
            default -> 0;
        };

    }
}
