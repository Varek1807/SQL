package ru.netology.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    DataHelper.AuthInfo user;
    LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
        user = DataHelper.getAuthInfo();
        loginPage = new LoginPage();
    }

    @AfterAll
    public static void afterAll() {
        DataHelper.setDown();
    }

    @Test
    public void verification() {
        DataHelper.deleteVerificationCode();
        loginPage.insertData(user.getLogin(), user.getPassword());
        VerificationPage verification = new VerificationPage();
        verification.insertCode(DataHelper.getAuthCode(user.getLogin()));
        DashboardPage dashboard = new DashboardPage();
        dashboard.visibleDashboardPage();
    }


    @Test
    public void ifWrongPasswordEnteredThreeTimes() {
        loginPage.incorrectPassword(user.getLogin(), DataHelper.getWrongPassword());
        loginPage.incorrectPassword(user.getLogin(), DataHelper.getWrongPassword());
        loginPage.blocked(user.getLogin(), DataHelper.getWrongPassword());
    }
}
