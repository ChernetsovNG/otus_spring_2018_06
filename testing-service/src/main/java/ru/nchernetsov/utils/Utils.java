package ru.nchernetsov.utils;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {

    /**
     * Сравнить содержимое двух списков
     * @param listA - первый список
     * @param listB - второй список
     * @param <T> - тип элементов
     * @return - равны ли списки
     */
    public static <T> boolean listEquals(List<T> listA, List<T> listB) {
        return listA.containsAll(listB) && listB.containsAll(listA);
    }

    /**
     * Получить список файлов в папке внутри папки resources
     * @param folder - папка внутри папки resources
     * @return - список файлов
     */
    public static List<String> getFileNamesFromResourceFolder(String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        assert url != null;
        String path = url.getPath();
        return Arrays.stream(Objects.requireNonNull(new File(path).listFiles()))
            .map(File::getName)
            .collect(Collectors.toList());
    }
}
