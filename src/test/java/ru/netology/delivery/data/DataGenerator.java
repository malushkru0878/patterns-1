package ru.netology.delivery.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static Random random = new Random();

    private DataGenerator() {
    }

    public static String generateDate(int shift, int range) {
        return LocalDate.now().plusDays(0 + shift).plusDays(random.nextInt(range))
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        String[] city = new String[]{"Москва", "Майкоп", "Санкт-Петербург", "Уфа", "Великий Новгород", "Нарьян-Мар",
                "Ростов-на-Дону", "Нижний Новгород", "Владимир", "Архангельск"};
        return city[random.nextInt(city.length)];
    }

    public static String generateInvalidCity() {
        String[] city = new String[]{"Ковров", "Южа", "Сосновый бор", "Камешково", "Зеленый Новгород", "Нарьян-Смак",
                "Ростов-на-Горе", "Нижний Сад", "Владимировец", "Сухум"};
        return city[random.nextInt(city.length)];
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().fullName();
    }

    public static String generateComplexName() {
        String[] name = new String[]{"Арсений Новиков-Прибой", "Владимир Иванович Немирович-Данченко",
                "Всеволод Александрович Овчина-Оболенский-Телепнев", "Николай Андреевич Римский-Корсаков", "Мартин Андерсен-Нексё"};
        return name[random.nextInt(name.length)];
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

}