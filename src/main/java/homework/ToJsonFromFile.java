package main.java.homework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Дан текстовый файл file.txt, необходимо считать файл в список объектов User и создать новый файл user.json.
 * Предполагаем, что каждая строка содержит одинаковое количество "колонок", разделенный пробелом.
 *
 * Пример:
 * Для файла file.txt со следующим содержанием:
 *   name age
 *   alice 21
 *   ryan 30
 *
 * Новый файл должен иметь следующий вид:
 *[
 *   {
 *    "name": "alice",
 *    "age":21
 *   },
 *   {
 *    "name": "ryan",
 *    "age":30
 *   }
 *]
 */

class ToJsonFromFile {
    public void readFromFileWriteToJson(String pathFromFile, String pathToJson) {
        extractUsersFromFile(pathFromFile);
        createJsonFromUsersList(pathToJson, extractUsersFromFile(pathFromFile));
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToJson))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static List<User> extractUsersFromFile(String pathToFile) {
        File file = new File(pathToFile);
        if (!file.exists()) {
            throw new RuntimeException("File with name " + file.getName() + " does not exist");
        }

        List<User> users = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] parts = line.strip().split(" ");
                if (!parts[0].trim().equals("name") && !parts[1].trim().equals("age")) {
                    users.add(new User(parts[0], Integer.parseInt(parts[1])));
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return users;
    }

    private static void createJsonFromUsersList(String pathToFile, List<User> users) {
        File file = new File(pathToFile);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.print("File with name " + file.getName() + " does not exist");
            }
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathToFile))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(users);
            bufferedWriter.write(json);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
