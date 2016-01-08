package com.xiodine.problema4;

import com.xiodine.helpers.Tester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 6. Se dau n vectori de numere naturale nenule şi un număr natural k (n,k<500).
 * Elaboraţi un algoritm cu o complexitate cât mai bună care să construiască un şir de n numere cu următoarele
 * proprietăţi:
 * <p>
 * Al i-lea element al şirului este ales din vectorul i.
 * Suma elementelor şirului este egală cu k.
 * <p>
 * Datele de intrare se citesc dintr-un fişier. Prima linie va conţine numerele n şi k. Pe fiecare din următoarele n linii
 * sunt scrise elementele câte unui vector, separate prin spaţii.
 * <p>
 * Se vor afişa elementele unui şir construit cu restricţiile de mai sus. Dacă nu există un şir cu proprietăţile cerute se
 * va afişa 0. O(mk), m=numărul de elemente din şiruri (2,5p) – licenţă 2014
 */

public class Main {
    private static final boolean DEBUG = false;

    private int[] arrayFromString(String str) {
        return Arrays.asList(str.split(" ")).stream().mapToInt(Integer::parseInt).toArray();
    }

    public Main() throws Exception {
        Tester<Integer> tester = new Tester<>(this.getClass(), Integer.class);


        while (tester.has()) {
            int n = tester.next();
            int k = tester.next();

            int[][] dinamica = new int[n + 1][k + 1];

            tester.getScanner().skip("\n");
            for (int i = 1; i <= n; i++) {
                String str = tester.getScanner().nextLine();
                int[] elems = arrayFromString(str);
                int min = Arrays.stream(elems).reduce(elems[0], (x, y) -> x > y ? y : x);

                for (int j = k - min; j >= 0; j--) {
                    final int _j = j;
                    final int _i = i - 1;
                    final int _id = i;
                    if (dinamica[_i][_j] != 0 || j == 0) {
                        Arrays.stream(elems)
                              .filter(x -> _j + x <= k && dinamica[_id][_j + x] == 0)
                              .forEach(elem -> dinamica[_id][_j + elem] = elem);
                    }
                }
                if (DEBUG) {
                    System.out.println();
                    for (int[] a : dinamica)
                        System.out.println(Arrays.toString(a));
                    System.out.println();
                    System.out.println(Arrays.toString(elems));
                    System.out.println(min);
                    System.out.println();
                }
            }
            if (dinamica[n][k] == 0) {
                System.out.println(0);
            } else {
                int index = k;
                int row = n;
                boolean first = true;
                while (row != 0) {
                    if (first)
                        first = false;
                    else
                        System.out.print(", ");
                    System.out.print(dinamica[row][index]);
                    index -= dinamica[row][index];
                    row--;
                }
                System.out.println();
            }
        }
    }
}
