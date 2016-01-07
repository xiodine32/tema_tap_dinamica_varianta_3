package com.xiodine.problema3;

import com.xiodine.helpers.Tester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 5. Generalizarea problemei 10 (joc pentru două persoane) de la Greedy Pentru jocul descris în problema 10 de la
 * Greedy, renunţând la ipoteza că numărul de elemente n este par, determinaţi dacă primul jucător are o strategie de
 * câştig şi, în caz afirmativ, cu cât va câştiga minim (cu cât va fi mai mare sigur suma lui decât a adversarului).
 *
 * Implementaţi un joc de două persoane în care pentru primul jucător mută calculatorul conform strategiei optime
 * determinate, iar pentru al doilea joacă utilizatorul. La fiecare pas anunţaţi-l pe utilizator cu cât sunteţi
 * sigur că va fi mai mare suma obţinută de calculator faţă de a sa O(n2)
 * (vezi şi http://www.infoarena.ro/problema/joculet) (3p).
 */

@SuppressWarnings("unused")
public class Main {


    public Main() throws Exception {
        Tester<String> tester = new Tester<>(this.getClass(), String.class);
        while (tester.has()) {
            run(Arrays.asList(tester.next().split(" ")).stream().mapToInt(Integer::parseInt).toArray());
        }
    }

    private int sum(ArrayList<Integer> list) {
        return list.stream().reduce(0, (x, y) -> x + y);
    }

    private ArrayList<Integer> calculator;
    private ArrayList<Integer> user;
    private int indexStart;
    private int indexLength;
    private int n;
    private int badChoises;
    int [][] dif;

    private void run(int[] array) {

        n = array.length;
        badChoises = 0;
        calculator = new ArrayList<>();
        user = new ArrayList<>();
        indexStart = 0;
        indexLength = n - 1;

        algorithm(array);

        boolean firstPlayer = true;
        while (indexStart <= indexLength) {
            if (!firstPlayer && indexStart != indexLength) {
                int approx = dif[n - 1][0] + badChoises * 2;
                System.out.println("Calculatorul va castiga cu diferenta de: " + approx + "\n");
            }
            displayBoard(array);
            if (firstPlayer) {
                computerLogic(array);
            } else {
                userLogic(array);
            }
            displayStats();
            firstPlayer = !firstPlayer;
        }
    }

    private void displayStats() {
        System.out.println();
        System.out.println("Calculator are: " + calculator + " = " + sum(calculator));
        System.out.println("Player are: " + user + " = " + sum(user));
        System.out.println("Calculator castiga cu: " + (sum(calculator) - sum(user)));
        System.out.println();
    }

    private void userLogic(int[] array) {
        if (indexStart == indexLength) {
            user.add(array[indexStart]);
            System.out.println("Player alege " + array[indexStart]);
            indexStart++;
            return;
        }

        System.out.print("Player alege (0 left, 1 right): ");
        Scanner in = new Scanner(System.in);
        int picked;
        int otherPicked;
        int computerPicked = computerGetChoice() ? array[indexStart] : array[indexLength];

        if (in.nextInt() == 0) {
            picked = array[indexStart];
            otherPicked = array[indexLength];
            indexStart++;
        } else {
            picked = array[indexLength];
            otherPicked = array[indexStart];
            indexLength--;
        }

        if (picked != computerPicked) {
            badChoises += Math.abs(otherPicked - picked);
        }

        user.add(picked);
        System.out.println("Player alege " + picked);
    }

    private void computerLogic(int[] array) {
        if (indexLength == indexStart) {
            calculator.add(array[indexStart]);
            System.out.println("Calculator alege " + array[indexStart]);
            indexStart++;
            return;
        }

        int theChoice1 = dif[0][indexStart];
        int theChoice2 = dif[0][indexLength];

        boolean left = computerGetChoice();

        if (left) {
            System.out.println("Calculator alege " + theChoice1);
            calculator.add(theChoice1);
            indexStart++;
        } else {
            calculator.add(theChoice2);
            System.out.println("Calculator alege " + theChoice2);
            indexLength--;
        }
    }

    private boolean computerGetChoice() {
        int choice1 = dif[0][indexStart] - dif[(indexLength - indexStart) - 1][indexStart + 1];
        int choice2 = dif[0][indexLength] - dif[(indexLength - indexStart) - 1][indexStart];

        return choice1 > choice2;
    }

    private void displayBoard(int[] array) {
        System.out.print("Board: ");
        for (int i = indexStart; i <= indexLength; i++)
            System.out.print(array[i] + (i + 1 <= indexLength ? ", ":""));
        System.out.println("\n");
    }

    private void algorithm(int[] array) {
        dif = new int[n][n];
        for (int size = 0; size < n; size++) {
            for (int index = 0; index < n - size; index++) {
                if (size == 0) {
                    dif[size][index] = array[index];
                    continue;
                }
                int choice1 = dif[0][index] - dif[size - 1][index + 1];
                int choice2 = dif[0][index + size] - dif[size - 1][index];
                dif[size][index] = choice1 > choice2 ? choice1 : choice2;
            }
        }
    }
}
