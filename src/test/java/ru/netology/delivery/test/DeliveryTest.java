package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.*;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        var secondMeetingDate = DataGenerator.generateDate(5, 4);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Встреча успешно запланирована на  " + firstMeetingDate));
        $("[data-test-id='success-notification'] button[type='button'] .icon_name_close").click();
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(secondMeetingDate);
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] button[type='button'] .button__text").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Встреча успешно запланирована на  " + secondMeetingDate));
    }

    @Test
    @DisplayName("Should not change the date in the message")
    void shouldConstantDateInMessage() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        var secondMeetingDate = DataGenerator.generateDate(5, 4);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Встреча успешно запланирована на  " + firstMeetingDate));
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(secondMeetingDate);
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Встреча успешно запланирована на  " + firstMeetingDate));
    }

    @Test
    void shouldCityNotInList() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id=city] input").setValue(DataGenerator.generateInvalidCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldEmptyInCityField() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldDateLessThreeDays() {
        var firstMeetingInvalidDate = DataGenerator.generateDate(1, 1);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingInvalidDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldEmptyInDateField() {
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldEmptyNameField() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue("");
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldComplexName() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateComplexName());
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
    }

    @Test
    void shouldLatinLettersInNameField() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("en"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldEmptyPhoneField() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue("");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldPhoneWithoutPlusSymbol() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue("89012345678");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldMoreElevenCharactersInPhone() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue("+79012345678999");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
    }

    @Test
    void shouldLessElevenCharactersInPhone() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue("+7901234567");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldOperatorCodeInPhone() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue("+70012345678");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldCheckboxNotMarked() {
        var firstMeetingDate = DataGenerator.generateDate(4, 5);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[class='button__text']").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}
