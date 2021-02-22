package main.java.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Дан текстовый файл file.txt, который содержит список номеров телефоном (один на линии).
 * Необъодимо написать метод, который будет считывать файл и выводить в консоль все валидные номера телефонов.
 * Предполагаем, что "валидный" номер телефона - это строка в одном из двух форматов: (xxx) xxx-xxxx или xxx-xxx-xxxx (х обозначает цифру).
 * Пример:
 * Для файла file.txt со следующим содержанием:
 * 987-123-4567
 * 123 456 7890
 * (123) 456-7890
 * Метод должен вывести на экран
 * 987-123-4567
 * (123) 456-7890
 */

class ValidPhoneNumber {

    void validatorPhoneNumbers(String pathToFile) {
        File file = new File(pathToFile);

        if (!file.exists()) {
            throw new RuntimeException("File with name " + file.getName() + " does not exist");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.matches("\\(\\d{3}\\)\\s\\d{3}\\-\\d{4}") || line.matches("\\d{3}-\\d{3}-\\d{4}")) {
                    System.out.println(line);
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
