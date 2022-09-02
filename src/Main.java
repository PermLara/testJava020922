import java.io.IOException;
import java.lang.System;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        int result;
        boolean foundOperator;
        String resultString, isArabian1, isArabian2, isRoman1, isRoman2;
        String[] arguments;
        KataOperator kataOperator = null;
        Digit roman1, roman2;

        //в цикле считываем и решаем
        while (true) {
            //получили данные
            String inputLine = scanner.nextLine();
            if (inputLine.equalsIgnoreCase("quit")) {
                System.out.println("Расчеты окончены. Было здорово!");
                break;
            }
            //ищем в строке оператор
            foundOperator = false;
            for (KataOperator element : KataOperator.values()) {
                if (inputLine.indexOf(element.getCode()) > 0) {
                    kataOperator = element;
                    foundOperator = true;
                    break;
                }
            }
            if (!foundOperator)
            throw new IOException("Во входной строке не найден оператор.");

            //разбиваем входную строку на 2 части по оператору
            arguments = inputLine.split(kataOperator.toRegexp());
            if (arguments.length != 2)
            throw new IOException("Задано неверное число операндов. Во входной строке должно быть два операнда.");

            arguments[0] = arguments[0].trim();
            arguments[1] = arguments[1].trim();

            //определить, в какой системе заданы операнды
            isArabian1 = isArabian(arguments[0],0);
            isArabian2 = isArabian(arguments[1],1);
            if (isArabian1.length() == 0 && isArabian2.length() == 0) {
                result = kataOperator.getResult(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
                resultString = Integer.toString(result);
            } else {
                isRoman1 = isRoman(arguments[0],0);
                isRoman2 = isRoman(arguments[1],1);
                if (isRoman1.length() == 0 && isRoman2.length() == 0) {
                    roman1 = Digit.valueOf(arguments[0]);
                    roman2 = Digit.valueOf(arguments[1]);
                    result = kataOperator.getResult(roman1.getArabian(), roman2.getArabian());
                    //результат должен быть не отрицательный
                    if (result<=0)
                    throw new IOException("Получили отрицательный или нулевой результат в римских цифрах.");
                    //переводим в римские цифры, допустимый диапазон от 1 до 100
                    resultString = Digit.getRoman(result);
                } else
                    //операнды в разных системах
                    if ((isArabian1.length() == 0 && isRoman2.length() == 0)
                        || (isRoman1.length() == 0 && isArabian2.length() == 0)
                        ) {
                        throw new IOException("Операнды заданы в разных системах.");
                    } else
                    throw new IOException("Операнды заданы неверно."
                            + isArabian1 + " " +isArabian2 + " " + isRoman1 + " " + isRoman2);
            }

            System.out.println(resultString);
        }
    }

    private static String isArabian(String input, int operandNumber) {
        int x;
        double d;
        String firstOrSecond = operandNumber == 0 ? "первый" : "второй";
        try {
            x = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            return "Операнд " + input + " не распознан как арабская цифра.";
        }
        //проверить на нецелые числа
        d = Double.parseDouble(input);
        if (x <= 0) return "Задан отрицательный или нулевой " + firstOrSecond + " операнд.";
        if (x > 10) return "Задан " + firstOrSecond + " операнд больше 10.";
        if ((int)d != x) return "Задан дробный " + firstOrSecond + " операнд.";
        return ""; //успешно можно преобразовать в арабскую цифру
    }

    private static String isRoman(String input, int operandNumber) {
        Digit y;
        String firstOrSecond = operandNumber == 0 ? "первый" : "второй";
        try {
            y = Digit.valueOf(input.toUpperCase());

        } catch (IllegalArgumentException iae) {
            return "Операнд " + input + " не распознан как римская цифра.";
        }
        if (y.getArabian() > 10) return "Задан " + firstOrSecond + " операнд больше 10.";
        return ""; //успешно можно преобразовать в римскую цифру
    }
}