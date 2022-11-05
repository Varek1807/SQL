package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginInput = $("[data-test-id=login] input");
    private SelenideElement passwordInput = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");
    private SelenideElement errorButton = $("[data-test-id=error-notification]");

    public void insertData(String login, String password) {
        loginInput.setValue(login);
        passwordInput.setValue(password);
        loginButton.click();
    }

    public void incorrectPassword(String login, String password) {
        loginInput.doubleClick().sendKeys(Keys.DELETE);
        passwordInput.doubleClick().sendKeys(Keys.DELETE);
        insertData(login, password);
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
        errorButton.click();
    }

    public void blocked(String login, String password) {
        loginInput.doubleClick().sendKeys(Keys.DELETE);
        passwordInput.doubleClick().sendKeys(Keys.DELETE);
        insertData(login, password);
        errorNotification.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Ошибка! " + "Превышено количество попыток! Система временно заблокирована!"));
    }
}
