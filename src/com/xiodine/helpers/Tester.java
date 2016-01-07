package com.xiodine.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Created on 07/01/16.
 * Package ${PACKAGE_NAME}.
 * Project tap_dinamica_varianta_3.
 */
public class Tester<T> {

    private Scanner scanner;
    private Class<T> tClass;
    private String className;

    public Tester(String file, Class<T> tClass) {
        this.tClass = tClass;
        getClassName();
        try {
            scanner = new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean has() throws Exception {
        Class<? extends Scanner> scannerClass = scanner.getClass();
        Method method = scannerClass.getMethod("hasNext" + className);
        return (boolean) method.invoke(scanner);
    }

    private void getClassName() {
        className = tClass.getSimpleName();
        if (className.equals("String"))
            className = "Line";
    }

    public T next() throws Exception {
        if (!has()) {
            return null;
        }
        Object result = scanner.getClass().getMethod("next" + className).invoke(scanner);
        return tClass.cast(result);
    }

}
