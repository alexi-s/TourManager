package service;

import java.util.List;
import java.util.regex.Pattern;

public class Utility {

    public static boolean checkId(String str) {
        Pattern pattern = Pattern.compile("\\d+");
        return pattern.matcher(str).matches();
    }

    public static String[] stringParser(String str) {
        str = str.replaceAll("[^A-Za-z\\s]+", "");
        return str.split("\\s+");
    }

    public static <T> void printList(T collection) {
        List list = (List) collection;
        if (list.size() == 0) {
            System.out.println("No records.");
        } else {
            for (Object item : list) {
                System.out.println("\t" + item);
            }
        }
    }
}
