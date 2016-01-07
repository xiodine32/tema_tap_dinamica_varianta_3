package com.xiodine.problema1;

import com.xiodine.helpers.Tester;

import java.util.ArrayList;

/**
 * 3. Se dă un cuvânt formate numai cu litere.
 * a) Să se determine câte palindromuri (subsecvenţe egale cu inversele lor) conţine cuvântul O(n2)
 * b) Să se descompună şirul în număr minim de palindromuri.
 * <p>
 * Exemplu:
 * <p>
 * pentru abcbaabc – se obţin 3 palindromuri: a, b, cbaabc;
 * pentru aaacaaba se obțin 3 palindromuri: aa, aca, aba.
 * <p>
 * O(n2) (2,5)
 */
@SuppressWarnings("unused")
public class Main {

    public Main() throws Exception {

        // stiai ca trebuie sa fie ceva dubios si cu reflection.
        Tester<String> tester = new Tester<>("src/com/xiodine/problema1/input.in", String.class);
        while (tester.has())
            run(tester.next());
    }

    private void run(String text) {
        int n = text.length();

        // dinamica[start][end] = palindrom? (true / false)
        boolean[][] dinamica = new boolean[n][n];
        // numarul maxim de bucati in care trebuie impartit pana la lungimea respectiva.
        ArrayListInteger[] bucati = new ArrayListInteger[n];

        for (int i = 0; i < n; i++)
            bucati[i] = new ArrayListInteger();

        for (int end = 0; end < n; end++) {
            // setez pe maxim numarul de bucati de cuvinte (impart in fiecare loc)
            for (int i = 0; i <= end; i++)
                bucati[end].add(i);

            for (int start = 0; start <= end; start++) {
                // daca literele pot forma un palindrom si ori e cuvantul ce mult de 2 litere ori ce e in interior e
                // palindrom
                if (text.charAt(start) == text.charAt(end) && (end - start <= 1 || dinamica[start + 1][end - 1])) {
                    dinamica[start][end] = true;

                    // daca a trebuit sa impartim
                    if (start > 0) {

                        // luam minimum intre minimul curent si cel de pana la unde am impartit
                        int min = bucati[end].size();
                        if (min > bucati[start - 1].size() + 1) {
                            bucati[end] = (ArrayListInteger) bucati[start - 1].clone();
                            bucati[end].add(start);
                        }
                    } else {

                        // [0..end] e palindrom, lel.
                        bucati[end].clear();
                    }
                }

            }
        }

        // impart in cuvinte
        int splitBegin = 0;
        System.out.print(text + " => ");
        for (Integer split : bucati[n - 1]) {
            System.out.print(text.substring(splitBegin, split) + " + ");
            splitBegin += split - splitBegin;
        }
        System.out.println(text.substring(splitBegin));
    }

    private class ArrayListInteger extends ArrayList<Integer> {
    }
}

