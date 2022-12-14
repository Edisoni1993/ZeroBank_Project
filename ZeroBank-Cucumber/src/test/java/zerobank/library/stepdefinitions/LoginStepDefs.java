package zerobank.library.stepdefinitions;

import zerobank.library.pages.DashboardMenuPage;
import zerobank.library.pages.LoginPage;
import zerobank.library.utilities.Driver;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class LoginStepDefs {
    LoginPage loginPage = new LoginPage();
    DashboardMenuPage dashboardMenuPage = new DashboardMenuPage();

    @Then("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String login, String password) {
        loginPage.login(login,password);
    }

    @Then("the {} page should be displayed")
    public void the_Account_summary_page_should_be_displayed(String pageName) {
        String currentTitle = Driver.getDriver().getTitle();
        String selectedMenu = dashboardMenuPage.selMenu.getText();

        Assert.assertEquals("Page name is not as expected", pageName,selectedMenu);
        Assert.assertTrue("Title is not as expected", currentTitle.toLowerCase().contains(pageName.toLowerCase()));
    }

    @Then("Error message {} should be displayed.")
    public void error_message_should_be_displayed(String errorMessage) {
        Assert.assertEquals("Error message does NOT match",errorMessage,loginPage.errorMessage.getText());
    }

    @Then("I login with valid credentials")
    public void i_login_with_valid_credentials() {

        loginPage.login("username","password");
    }






}
