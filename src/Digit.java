public enum Digit {
    I(1),
    II(2),
    III(3),
    IV(4),
    V(5),
    VI(6),
    VII(7),
    VIII(8),
    IX(9),
    X(10),
    XX(20),
    XXX(30),
    XL(40),
    L(50),
    LX(60),
    LXX(70),
    LXXX(80),
    XC(90),
    C(100);

    private final int arabian;

    Digit(int arabian) {

        this.arabian = arabian;
    }

    public int getArabian() {
        return arabian;
    }

    public static String getRoman(int a) {
        int decimal, modulo;
        String result = "";

        //проверяем на недопустимые
         if(a < 0 || a > 100) return "";

        //берем, какие возможно, без преобразований
        if (a <= 10 || a == 50 || a == 100) {
            for (Digit e : values()) {
                if (e.arabian == a) {
                    return e.name();
                }
            }
        }
        //десятки
        decimal = a - a % 10;
        for (Digit e : values()) {
            if (e.arabian == decimal) {
                result = e.name();
            }
        }
        //единицы
        modulo = a % 10;
        for (Digit e : values()) {
            if (e.arabian == modulo) {
                result = result + e.name();
                break;
            }
        }
        return result;
    }
}
