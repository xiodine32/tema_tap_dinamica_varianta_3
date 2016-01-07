package com.xiodine.problema2;

import com.xiodine.helpers.Tester;

/**
 * 4. O mască pentru cuvinte este un şir de caractere care, pe lângă litere, poate conţine şi două caractere cu
 * semnificaţie specială: ‘?’ înlocuieşte un singur caracter, iar ‘*’ înlocuieşte zero, unul sau mai multe caractere.
 * Se citesc un cuvânt şi o mască. Să se determine dacă masca se potriveşte cuvântului citit şi, în caz afirmativ, să se
 * detalieze modul în care cuvântul se potriveşte cu masca.
 * Exemplu: Cuvântul problemele se potriveşte pe masca pr?b*le (‘?’ <-> ‘o’ şi ‘*’ <-> „leme”)
 * https://leetcode.com/problems/wildcard-matching/ (2p)
 */

@SuppressWarnings("unused")
public class Main {
    public Main() throws Exception {
        Tester<String> tester = new Tester<>(this.getClass(), String.class);
        while (tester.has()) {
            final String text = tester.next();
            String[] strings = text.split(" ");
            System.out.println(text + " => " + matches(strings[0], strings[1]));
        }
    }

    private boolean matches(String query, String match) {
        if (query.isEmpty() && match.isEmpty())
            return true;
        if (match.isEmpty())
            return false;
        if (query.isEmpty()) {
            for (char x : match.toCharArray()) {
                if (x != '*')
                    return false;
            }
            return true;
        }
        char q = query.charAt(0);
        char m = match.charAt(0);
        if (m == '*') {
            return matches(query, match.substring(1)) || matches(query.substring(1), match);
        } else if (m == '?' || q == m) {
            return matches(query.substring(1), match.substring(1));
        }
        return false;
    }
}

