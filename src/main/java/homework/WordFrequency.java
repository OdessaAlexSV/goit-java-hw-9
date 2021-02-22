package main.java.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Напишите метод, который будет подсчитывать частоту каждого слова в файле words.txt.
 * Предпалагаем, что:
 * words.txt содержит только слова в нижнем регистре, разделенные пробелом
 * Каждое слово содержит только символы-буквы в нижнем регистре.
 * Слова разделены одим или несколькими пробелами, либо переносом строки.
 * Пример:
 * Для файла words.txt со следующим содержанием:
 * the day is sunny the the
 * the sunny is is
 * Метод должен вернуть частоту:
 * the 4
 * is 3
 * sunny 2
 * day 1
 * Обратите внимание! Вывод на консоль должен быть отсортирован на частоте слов (от наибольшей к наименьшей)
 */

public class WordFrequency {
    public static boolean ASC = true;
    public static boolean DESC = false;

    public void wordFrequency(String pathToWordsFile) {
        File file = new File(pathToWordsFile);

        if (!file.exists()) {
            throw new RuntimeException("File with name " + file.getName() + " does not exist");
        }

        Map<String, Integer> mapWords = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] parts = line.strip().split(" ");
                for (String word : parts) {
                    Integer count = mapWords.get(word);
                    count = (count == null ? 1 : count + 1);
                    mapWords.put(word, count);
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Map<String, Integer> sortedMapAsc = sortByComparator(mapWords, ASC);
        printMap(sortedMapAsc, ASC);

        System.out.println("-".repeat(15));

        Map<String, Integer> sortedMapDesc = sortByComparator(mapWords, DESC);
        printMap(sortedMapDesc, DESC);

    }

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                if (order) {
                    return o2.getValue().compareTo(o1.getValue());
                } else {
                    return o1.getValue().compareTo(o2.getValue());
                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void printMap(Map<String, Integer> map, boolean order)
    {
        if (order) {
            System.out.println("Sorting by DESC :");
        } else {
            System.out.println("Sorting by ASC :");
        }

        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            System.out.println(entry.getKey() + " : "+ entry.getValue());
        }
    }
}


