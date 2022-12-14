package zerobank.library.stepdefinitions;

import zerobank.library.pages.AccountActivityPage;
import zerobank.library.pages.DashboardMenuPage;
import zerobank.library.utilities.Driver;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardStepDefs {

    DashboardMenuPage dashboardMenuPage = new DashboardMenuPage();
    AccountActivityPage accountActivity = new AccountActivityPage();
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);

    @Given("{} page should have the title {string}")
    public void account_summary_page_should_have_the_title(String page, String expected) {

        String actual = Driver.getDriver().getTitle();
        Assert.assertEquals(expected, actual);
    }
}
