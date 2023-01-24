import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String a, b, c, d;

        System.out.println("Selamat datang di game 24 solver\n");
        System.out.println("Silahkan pilih metode input:");
        System.out.println("1. Input manual");
        System.out.println("2. Random");
        String inputMethod = input.next();
        while (!(inputMethod.equals("1") || inputMethod.equals("2"))) {
            System.out.println("Pilihan tidak sesuai\n");
            System.out.println("Pilih metode input:");
            System.out.println("1. Input manual");
            System.out.println("2. Random");
            inputMethod = input.next();
        }

        // Menerima input manual
        if (inputMethod.equals("1")) {
            System.out.println("Silahkan masukkan input: ");
            a = input.next();
            b = input.next();
            c = input.next();
            d = input.next();
            while (invalidInput(a) || invalidInput((b)) || invalidInput(c) || invalidInput(d)) {
                System.out.println("Masukan tidak sesuai");
                a = input.next();
                b = input.next();
                c = input.next();
                d = input.next();
            }
        }
        // Menerima input random
        else {
            String[] inputList = {"A", "2", "3", "4" , "5", "6", "7", "8" , "9", "10", "J", "Q", "K"};
            Random rand = new Random();
            a = inputList[rand.nextInt(inputList.length)];
            b = inputList[rand.nextInt(inputList.length)];
            c = inputList[rand.nextInt(inputList.length)];
            d = inputList[rand.nextInt(inputList.length)];
        }
        System.out.printf("Input anda = %s %s %s %s%n%n", a, b, c, d);

        // Mengubah input dari string ke double
        double var1 = strToDouble(a);
        double var2 = strToDouble(b);
        double var3 = strToDouble(c);
        double var4 = strToDouble(d);

        long start = System.nanoTime();
        ArrayList<String> solution = searchSolution(var1, var2, var3, var4);
        long end = System.nanoTime();
        long executionTime = end - start;
        int nSolution = solution.size();

        if (nSolution == 0) {
            System.out.println("Tidak ada solusi");
        }
        else {
            System.out.printf("%d solusi ditemukan%n", nSolution);
            for (String s : solution) {
                System.out.println(s);
            }
        }
        System.out.printf("Waktu eksekusi: %d nanoseconds%n%n", executionTime);
        System.out.println("Apakah ingin menyimpan solusi?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");

        String save = input.next();
        while (!(save.equals("1") || save.equals("2"))) {
            System.out.println("Pilihan tidak sesuai\n");
            System.out.println("Apakah ingin menyimpan solusi?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            save = input.next();
        }

        if (save.equals("1")) {
            System.out.println("Ketik nama file untuk menyimpan solusi: ");
            String fileName = input.next();
            FileWriter writer = new FileWriter(String.format("%s.txt", fileName));
            for (String str: solution) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        }
        System.out.println("Keluar program?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");

        String exit = input.next();
        while (!(exit.equals("1") || exit.equals("2"))) {
            System.out.println("Pilihan tidak sesuai\n");
            System.out.println("Keluar program?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            exit = input.next();
        }
        if (exit.equals("2")) {
            main(args);
        }
    }

    public static boolean invalidInput (String var) {
        return !var.equals("A") && !var.equals("2") && !var.equals("3") && !var.equals("4") && !var.equals("5") && !var.equals("6") && !var.equals("7") && !var.equals("8") && !var.equals("9") && !var.equals("10") && !var.equals("J") && !var.equals("Q") && !var.equals("K");
    }

    public static double strToDouble (String var) {
        return switch (var) {
            case "A" -> 1;
            case "J" -> 11;
            case "Q" -> 12;
            case "K" -> 13;
            default -> Double.parseDouble(var);
        };
    }

    public static ArrayList<String> searchSolution (double var1, double var2, double var3, double var4) {
        ArrayList<String> result = new ArrayList<>();
        List<Double> input = new ArrayList<>();
        input.add(var1);
        input.add(var2);
        input.add(var3);
        input.add(var4);
        List<Double> input1;
        List<Double> input2;
        List<Double> input3;
        double a, b, c, d;

        for (int i = 0; i < input.size(); i++) {
            a = input.get(i);
            if (i != 0 && a == input.get(0)) {
                break;
            }
            else {
                input1 = new ArrayList<>(input);
                input1.remove(i);
            }
            for (int j = 0; j < input1.size(); j++) {
                b = input1.get(j);
                if (j != 0 && b == input1.get(0)) {
                    break;
                }
                else {
                    input2 = new ArrayList<>(input1);
                    input2.remove(j);
                }
                for (int k = 0; k < input2.size(); k++) {
                    c = input2.get(k);
                    if (k != 0 && c == input2.get(0)) {
                        break;
                    }
                    else {
                        input3 = new ArrayList<>(input2);
                        input3.remove(k);
                        d = input3.get(0);
                        result.addAll(singleSolution(a,b,c,d));
                    }
                }
            }
        }
        return result;
    }

    public static ArrayList<String> singleSolution (double var1, double var2, double var3, double var4) {
        ArrayList<String> result = new ArrayList<>();
        double firstOp1, secOp1, finalOp1;
        double firstOp2, secOp2, finalOp2;
        double firstOp3, secOp3, finalOp3;
        double firstOp4, secOp4, finalOp4;

        for (int i = 0; i < 4; i++) {
            firstOp1 = oneOperation(i,var1,var2);
            firstOp2 = oneOperation(i,var2,var3);
            firstOp3 = firstOp1;
            firstOp4 = firstOp2;
            for (int j = 0; j < 4; j++) {
                secOp1 = oneOperation(j,firstOp1,var3);
                secOp2 = oneOperation(j,var1,firstOp2);
                secOp3 = oneOperation(j,var3,var4);
                secOp4 = oneOperation(j,firstOp4,var4);
                for (int k = 0; k < 4; k++) {
                     finalOp1 = oneOperation(k,secOp1,var4);
                     finalOp2 = oneOperation(k,secOp2,var4);
                     finalOp3 = oneOperation(k,firstOp3,secOp3);
                     finalOp4 = oneOperation(k,var1,secOp4);
                    // ((var1 Op var2) Op var3) Op var4
                    if (Math.abs(finalOp1 - 24) < 0.0001) {
                        result.add(solutionToString(i,j,k,1,var1,var2,var3,var4));
                    }
                    // (var1 Op (var2 Op var3)) Op var4
                    if (Math.abs(finalOp2 - 24) < 0.0001) {
                        result.add(solutionToString(j,i,k,2,var1,var2,var3,var4));
                    }
                    // (var1 Op var2) Op (var3 Op var4)
                    if (Math.abs(finalOp3 - 24) < 0.0001) {
                        result.add(solutionToString(i,k,j,3,var1,var2,var3,var4));
                    }
                    // var1 Op ((var2 Op var3) Op var4)
                    if (Math.abs(finalOp4 - 24) < 0.0001) {
                        result.add(solutionToString(k,i,j,4,var1,var2,var3,var4));
                    }
                }
            }
        }
        return result;
    }

    public static double oneOperation (int i, double var1, double var2) {
        if (i == 0) {
            return (var1 + var2);
        }
        else if (i == 1) {
            return (var1 - var2);
        }
        else if (i == 2) {
            return (var1 * var2);
        }
        else {
            if (var2 == 0) {
                return (-999);
            }
            else {
                return (var1 / var2);
            }
        }
    }

    public static String solutionToString (int i, int j, int k, int l, double a, double b, double c, double d) {
        String result;
        long var1 = Math.round(a);
        long var2 = Math.round(b);
        long var3 = Math.round(c);
        long var4 = Math.round(d);

        if (l == 1) {
            result = String.format("((%d %s %d) %s %d) %s %d", var1, operatorToString(i), var2, operatorToString(j), var3, operatorToString(k), var4);
        }
        else if (l == 2) {
            result = String.format("(%d %s (%d %s %d)) %s %d", var1, operatorToString(i), var2, operatorToString(j), var3, operatorToString(k), var4);
        }
        else if (l == 3) {
            result = String.format("(%d %s %d) %s (%d %s %d)", var1, operatorToString(i), var2, operatorToString(j), var3, operatorToString(k), var4);
        }
        else {
            result = String.format("%d %s ((%d %s %d) %s %d)", var1, operatorToString(i), var2, operatorToString(j), var3, operatorToString(k), var4);
        }
        return result;
    }

    public static String operatorToString (int i) {
        return switch (i) {
            case (0) -> "+";
            case (1) -> "-";
            case (2) -> "*";
            default -> "/";
        };
    }
}