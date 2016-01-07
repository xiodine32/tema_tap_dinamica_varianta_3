package com.xiodine;

import java.util.Scanner;

public class Main {

    private static final int PROBLEM = 1;
    private static final int MAX_PROBLEM = 4;
    private static final boolean ASK = false;

    public static void main(String[] args) {
        //noinspection PointlessBooleanExpression
        if (!ASK) {
            run(PROBLEM);
        } else {
            int userInput;
            while ((userInput = getUserInput()) != 0) {
                run(userInput);
            }
        }
    }

    private static int getUserInput() {
        System.out.println("Select from this list:\n");
        for (int i = 0; i <= MAX_PROBLEM; i++) {
            if (i != 0)
                System.out.println("  " + i + ". Problem " + (i + 2));
            else
                System.out.println("  0. Exit");
        }
        System.out.print("\nEnter the list number: ");
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

    private static void run(int id) {
        System.out.println();
        if (id > MAX_PROBLEM) {
            System.out.println("Problem not found!\n");
            return;
        }
        try {
            Class<?> theClass = getClass(id);
            long timerDuration = runTimer(theClass);
            System.out.printf("\nTime elapsed: %.8fs\n\n", timerDuration / 1000000000.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long runTimer(Class<?> theClass) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        long timerStart = System.nanoTime();
        theClass.getConstructor().newInstance();
        long timerEnd = System.nanoTime();
        return timerEnd - timerStart;
    }

    private static Class<?> getClass(int id) throws ClassNotFoundException {
        return Class.forName("com.xiodine.problema" + id + ".Main");
    }
}
