import java.io.IOException;
import java.lang.System;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        String inputString, resultString;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            inputString = scanner.nextLine();
            if (inputString.equalsIgnoreCase("quit")) {
                System.out.println("Расчеты окончены.");
                break;
            }
            resultString = calc(inputString);
            System.out.println(resultString);
        }
    }

    public static String calc(String inputString) throws IOException {

        final boolean FIRST=true, SECOND=false;
        boolean foundOperator;
        int result;
        String resultString, isArabian1, isArabian2, isRoman1, isRoman2;
        String[] arguments;
        KataOperator kataOperator = null;
        Digit roman1, roman2;

        foundOperator = false;
        for (KataOperator element : KataOperator.values()) {
            if (inputString.indexOf(element.getCode()) > 0) {
                kataOperator = element;
                foundOperator = true;
                break;
            }
        }
        if (!foundOperator)
            throw new IOException("Во входной строке не найден оператор.");

        arguments = inputString.split(kataOperator.toRegexp());
        if (arguments.length != 2)
            throw new IOException("Задано неверное число операндов. Во входной строке должно быть два операнда.");

        arguments[0] = arguments[0].trim();
        arguments[1] = arguments[1].trim();

        isArabian1 = isArabian(arguments[0],FIRST);
        isArabian2 = isArabian(arguments[1],SECOND);

        if (isArabian1.isEmpty() && isArabian2.isEmpty()) {
            result = kataOperator.getResult(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
            resultString = Integer.toString(result);
        } else {
            isRoman1 = isRoman(arguments[0], FIRST);
            isRoman2 = isRoman(arguments[1], SECOND);
            if (isRoman1.isEmpty() && isRoman2.isEmpty()) {
                roman1 = Digit.valueOf(arguments[0]);
                roman2 = Digit.valueOf(arguments[1]);
                result = kataOperator.getResult(roman1.getArabian(), roman2.getArabian());
                if (result<=0)
                    throw new IOException("Получили отрицательный или нулевой результат в римских цифрах.");
                //переводим в римские цифры, допустимый диапазон от 1 до 100
                resultString = Digit.getRoman(result);
            } else
                if ((isArabian1.isEmpty() && isRoman2.isEmpty())
                        || (isRoman1.isEmpty() && isArabian2.isEmpty())
                ) {
                    throw new IOException("Операнды заданы в разных системах.");
                } else
                    throw new IOException("Операнды заданы неверно."
                            + isArabian1 + " " +isArabian2 + " " + isRoman1 + " " + isRoman2);
        }

        return resultString;
    }


    private static String isArabian(String input, boolean operandNumber) {
        int x;
        double d;
        String firstOrSecond = operandNumber ? "первый" : "второй";
        try {
            x = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            return "Операнд " + input + " не распознан как арабская цифра.";
        }
        d = Double.parseDouble(input);
        if (x <= 0) return "Задан отрицательный или нулевой " + firstOrSecond + " операнд.";
        if (x > 10) return "Задан " + firstOrSecond + " операнд больше 10.";
        if ((int)d != x) return "Задан дробный " + firstOrSecond + " операнд.";
        return ""; //успешно можно преобразовать в арабскую цифру
    }

    private static String isRoman(String input, boolean operandNumber) {
        Digit y;
        String firstOrSecond = operandNumber ? "первый" : "второй";
        try {
            y = Digit.valueOf(input.toUpperCase());

        } catch (IllegalArgumentException iae) {
            return "Операнд " + input + " не распознан как римская цифра.";
        }
        if (y.getArabian() > 10) return "Задан " + firstOrSecond + " операнд больше 10.";
        return ""; //успешно можно преобразовать в римскую цифру
    }
}