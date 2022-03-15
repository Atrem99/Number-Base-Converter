package NumberBaseConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Main {
    private static final StringBuilder sb = new StringBuilder();
    private static final ArrayList<String> arrayList = new ArrayList<>();
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static final Scanner scanner = new Scanner(System.in);
    private static String[] lineSplit;
    private static int sourceBase;
    private static int targetBase;
    private static BigDecimal index;
    private static BigDecimal result = BigDecimal.ZERO;
    private static boolean flag = true;

    public static void main(String[] args) {
        readBases();
    }

    public static void readBases() {
        while (flag) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String[] input = scanner.nextLine().split(" ");
            if (input[0].equals("/exit")) {
                flag = false;
            } else {
                sourceBase = Integer.parseInt(input[0]);
                targetBase = Integer.parseInt(input[1]);
                redInputForUser();
            }
        }
    }

    public static void redInputForUser() {
        while (flag) {
            arrayList.clear();
            System.out.printf("Enter number in base %s to convert to base %d (To go back type /back) ", sourceBase, targetBase);
            String inputForUser = scanner.nextLine();

            if (inputForUser.equals("/back")) {
                readBases();

            } else if (sourceBase == 10 && targetBase < 10) {
                readFromDecimalToLower(inputForUser);
                System.out.print("Conversion result: ");
                for (String list : arrayList) {
                    System.out.print(list);
                }
                System.out.println();

            } else if (sourceBase == 10 && targetBase > 10) {
                readFromDecimalToLarge(inputForUser);
                System.out.print("Conversion result: ");
                for (String list : arrayList) {
                    System.out.print(list);
                }
                System.out.println();

            } else if (sourceBase < 10 && targetBase == 10) {
                readFromSmallerToDecimal(inputForUser);
                System.out.println("Conversion result: " + result);

            } else if (sourceBase > 10 && targetBase == 10) {
                readFromLargerToDecimal(inputForUser);
                System.out.println("Conversion result: " + result);


            } else if (sourceBase < 10 && targetBase < 10) {
                readFromSmallerToDecimal(inputForUser);
                readFromDecimalToLower(String.valueOf(result));
                System.out.print("Conversion result: ");
                for (String list : arrayList) {
                    System.out.print(list);
                }
                System.out.println();

            } else if (sourceBase > 10 && targetBase > 10) {
                readFromLargerToDecimal(inputForUser);
                readFromDecimalToLarge(String.valueOf(result));
                System.out.print("Conversion result: ");
                for (String list : arrayList) {
                    System.out.print(list);
                }
                System.out.println();

            } else if (sourceBase > 10) {
                readFromLargerToDecimal(inputForUser);
                readFromDecimalToLower(String.valueOf(result));
                System.out.print("Conversion result: ");
                for (String list : arrayList) {
                    System.out.print(list);
                }
                System.out.println();

            } else if (sourceBase < 10) {
                readFromSmallerToDecimal(inputForUser);
                readFromDecimalToLarge(String.valueOf(result));
                System.out.print("Conversion result: ");
                for (String list : arrayList) {
                    System.out.print(list);
                }
                System.out.println();
            }
        }
    }

    public static void readFromDecimalToLower(String inputForUser) {
        lineSplit = inputForUser.split("\\.");
        if (lineSplit[0].equals("0")) {
            arrayList.add("0");
        }
        BigInteger first = new BigInteger(lineSplit[0]);
        while (first.compareTo(BigInteger.ZERO) > 0) {
            BigInteger remainder = first.remainder(BigInteger.valueOf(targetBase));
            arrayList.add(String.valueOf(remainder));
            first = first.subtract(remainder).divide(BigInteger.valueOf(targetBase));
        }
        Collections.reverse(arrayList);
        int count = 0;
        if (lineSplit.length > 1 && lineSplit[1] != null) {
            BigDecimal second = new BigDecimal(lineSplit[1]);
            arrayList.add(".");
            int degree = lineSplit[1].length();
            second = second.divide(BigDecimal.TEN.pow(degree),5, RoundingMode.HALF_DOWN);
            while (count != 5) {
                second = second.multiply(BigDecimal.valueOf(targetBase));
                String[] a = String.valueOf(second).split("\\.");
                BigDecimal num0 = new BigDecimal(a[0]);
                if (a[0].isEmpty()) {
                    arrayList.add("0");
                } else {
                    arrayList.add(String.valueOf(num0));
                }
                count += 1;
                second = second.subtract(num0);

            }
        }
    }

    public static void readFromDecimalToLarge(String inputForUser) {
        lineSplit = inputForUser.split("\\.");
        BigInteger userSourceBase = new BigInteger(lineSplit[0]);

        while (userSourceBase.compareTo(BigInteger.ONE) >= 0) {
            BigInteger remainder = userSourceBase.remainder(BigInteger.valueOf(targetBase));
            if (remainder.compareTo(BigInteger.TEN.subtract(BigInteger.ONE)) > 0) {
                remainder = remainder.subtract(BigInteger.TEN);
                int index = Integer.parseInt(String.valueOf(remainder));
                arrayList.add(String.valueOf(alphabet.charAt(index)));
            } else {
                arrayList.add(String.valueOf(remainder));
            }
            userSourceBase = userSourceBase.subtract(remainder).divide(BigInteger.valueOf(targetBase));
        }
        Collections.reverse(arrayList);

        int count = 0;
        if (lineSplit.length > 1 && lineSplit[1] != null) {
            arrayList.add(".");
            BigDecimal second = new BigDecimal(lineSplit[1]);
            int degree = lineSplit[1].length();
            second = second.divide((BigDecimal.TEN.pow(degree)), 5, RoundingMode.HALF_DOWN);

            while (count != 5) {
                second = second.multiply(BigDecimal.valueOf(targetBase));
                String[] a = String.valueOf(second).split("\\.");
                BigDecimal num0 = new BigDecimal(a[0]);
                if (a[0].isEmpty()) {
                    arrayList.add("0");
                } else if (num0.compareTo(BigDecimal.TEN.subtract(BigDecimal.ONE)) > 0) {
                    BigDecimal i = num0.subtract(BigDecimal.TEN);
                    int index = Integer.parseInt(String.valueOf(i));
                    arrayList.add(String.valueOf(alphabet.charAt(index)));
                } else if (num0.compareTo(BigDecimal.TEN.subtract(BigDecimal.ONE)) <= 0) {
                    arrayList.add(String.valueOf(num0));
                }
                count++;
                second = second.subtract(num0);
            }
        }
    }

    public static void readFromSmallerToDecimal(String inputForUser) {
        lineSplit = inputForUser.split("\\.");
        sb.delete(0, Integer.MAX_VALUE);
        sb.append(lineSplit[0]);
        sb.reverse();

        result = BigDecimal.ZERO;
        for (int i = 0; i < sb.length(); i++) {
            index = BigDecimal.valueOf(Integer.parseInt(String.valueOf(sb.charAt(i))));
            result = result.add(BigDecimal.valueOf(sourceBase).pow(i).multiply(index));
        }

        if (lineSplit.length > 1 && lineSplit[1] != null) {
            for (int i = 0; i < lineSplit[1].length(); i++) {
                result = result.add(BigDecimal.valueOf(Long.parseLong(String.valueOf(lineSplit[1].charAt(i))))
                        .divide((BigDecimal.valueOf(sourceBase).pow(i + 1)), 5, RoundingMode.HALF_DOWN));
            }
        }
    }

    public static void readFromLargerToDecimal(String inputForUser) {
        lineSplit = inputForUser.split("\\.");
        sb.delete(0, Integer.MAX_VALUE);
        sb.append(lineSplit[0]);
        sb.reverse();

        result = BigDecimal.ZERO;
        for (int i = 0; i < sb.length(); i++) {
            for (int j = 0; j < alphabet.length(); j++) {
                if (sb.charAt(i) == alphabet.charAt(j)) {
                    index = BigDecimal.valueOf(j + 10);
                } else if (Character.isDigit(sb.charAt(i))) {
                    index = BigDecimal.valueOf(Long.parseLong(String.valueOf(sb.charAt(i))));
                }
            }
            result = result.add(BigDecimal.valueOf(sourceBase).pow(i).multiply(index));
        }

        if (lineSplit.length > 1 && lineSplit[1] != null) {
            for (int i = 0; i < lineSplit[1].length(); i++) {
                for (int j = 0; j < alphabet.length(); j++) {

                    if (lineSplit[1].charAt(i) == alphabet.charAt(j)) {
                        index = BigDecimal.valueOf(j + 10);
                    } else if (Character.isDigit(lineSplit[1].charAt(i))) {
                        index = BigDecimal.valueOf(Long.parseLong(String.valueOf(lineSplit[1].charAt(i))));
                    }
                }
                result = result.add(index.divide((BigDecimal.valueOf(sourceBase).pow(i + 1)), 5, RoundingMode.HALF_DOWN));
            }
        }
    }
}