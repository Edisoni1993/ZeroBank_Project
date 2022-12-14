package zerobank.library.stepdefinitions;

import zerobank.library.pages.AccountActivityPage;
import zerobank.library.pages.DashboardMenuPage;
import zerobank.library.pages.LoginPage;
import zerobank.library.utilities.BrowserUtils;
import zerobank.library.utilities.ConfigurationReader;
import zerobank.library.utilities.Driver;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AccountActivityStepDefs {

    AccountActivityPage accountActivity = new AccountActivityPage();
    LoginPage loginPage = new LoginPage();
    DashboardMenuPage dashboardMenuPage = new DashboardMenuPage();

    @Given("In the Account drop down default option should be {}.")
    public void in_the_Account_drop_down_default_option_should_be(String typeAccount) {

        Select options = new Select(accountActivity.accountOpts);
        String actualFirstOption = options.getFirstSelectedOption().getText();
        Assert.assertEquals("First option is not as it should be :" + typeAccount, typeAccount, actualFirstOption);
    }

    @Then("Account drop down should have the following options:{}")
    public void account_drop_down_should_have_the_following_options(String options) {

        Select optionsAcc = new Select(accountActivity.accountOpts);

        List<String> actualList = BrowserUtils.getElementsText(optionsAcc.getOptions());
        List<String> allOptionsExp = BrowserUtils.getCommaList(options);

        for (int i = 0; i < actualList.size(); i++) {
            Assert.assertEquals(actualList.get(i), allOptionsExp.get(i));
        }
    }

    @Then("Transactions table should have column names {}")
    public void transactions_table_should_have_column_names(String listOfColumns) {

        List<String> expectedColNames = BrowserUtils.getCommaList(listOfColumns);
        List<String> actualColNames = BrowserUtils.getElementsText(accountActivity.colNames);

        for (int i = 0; i < expectedColNames.size(); i++) {
            Assert.assertEquals(expectedColNames.get(i), actualColNames.get(i));
        }
    }

    @Given("the user is logged in")
    public void the_user_is_logged_in() {
        String url = ConfigurationReader.getProperty("url") + "/login.html";
        Driver.getDriver().get(url);
        loginPage.login("username", "password");
    }

    @When("the user clicks on {} link on the {} page")
    public void the_user_clicks_on_on_the_Account_Summary_page(String clickOnLink, String page) {
        dashboardMenuPage.goToMenu(page);
        Driver.getDriver().findElement(By.linkText(clickOnLink)).click();
    }

    @Then("Account drop down should have {} selected")
    public void account_drop_down_should_have_Savings_selected(String typeAccount) {

        Select allOptions = new Select(accountActivity.accountOpts);
        String actualOption = allOptions.getFirstSelectedOption().getText();
        Assert.assertEquals("The first option is not matching", typeAccount, actualOption);
    }

    @When("the user enters date range from {string} to {string}")
    public void the_user_enters_date_range_from_to(String fromDate, String toDate) {

        accountActivity.fromDateInput.clear();
        accountActivity.fromDateInput.sendKeys(fromDate);
        accountActivity.toDateInput.clear();
        accountActivity.toDateInput.sendKeys(toDate);
    }

    @When("clicks search")
    public void clicks_search() {

        accountActivity.findButton.click();
    }

    @Then("results table should only show transactions dates between {string} to {string}")
    public void results_table_should_only_show_transactions_dates_between_to(String fromDate, String toDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date fromD = dateFormat.parse(fromDate);
            Date afterD = dateFormat.parse(toDate);
            BrowserUtils.wait(3);

            List<String> datesString = BrowserUtils.getElementsText(accountActivity.allDates);
            List<Date> actualDates = new ArrayList<>();

            for (String stringDate : datesString) {
                actualDates.add(dateFormat.parse(stringDate));
            }
            for (Date actualDate : actualDates) {
                boolean isBetween =
                        (actualDate.after(fromD) || actualDate.equals(fromD)) &&
                                (actualDate.before(afterD) || actualDate.equals(afterD));
                Assert.assertTrue("Date range is not as expected", isBetween);
            }
        } catch (ParseException e) {
            Assert.fail("Wrong date format, correct format should be: yyyy-MM-dd");
        }
    }

    @Then("the results should be sorted by most recent date")
    public void the_results_should_be_sorted_by_most_recent_date() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> datesString = BrowserUtils.getElementsText(accountActivity.allDates);
        List<Date> actualDates = new ArrayList<>();

        try {
            for (String stringDate : datesString) {
                actualDates.add(dateFormat.parse(stringDate));
            }
        } catch (ParseException e) {
            Assert.fail("Wrong date format, correct format should be: yyyy-MM-dd");
        }
        List<Date> sortedDate = new ArrayList<>(actualDates);

        sortedDate.sort(Collections.reverseOrder());
        for (int i = 0; i < sortedDate.size(); i++) {
            Assert.assertEquals(sortedDate.get(i), (actualDates.get(i)));
        }
    }

    @Then("the results table should only not contain transactions dated {string}")
    public void the_results_table_should_only_not_contain_transactions_dated(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> datesString = BrowserUtils.getElementsText(accountActivity.allDates);
        List<Date> actualDates = new ArrayList<>();
        Date dateNotAcceptable = null;

        try {
            dateNotAcceptable = dateFormat.parse(date);
            for (String stringDate : datesString) {
                actualDates.add(dateFormat.parse(stringDate));
            }
        } catch (ParseException e) {
            Assert.fail("Wrong date format please follow this: yyyy-MM-dd");
        }

        for (Date actualDate : actualDates) {
            Assert.assertNotEquals(actualDate, dateNotAcceptable);
        }
    }

    @When("the user enters description {string}")
    public void the_user_enters_description(String word) {

        accountActivity.descrInput.clear();
        accountActivity.descrInput.sendKeys(word);
    }


    @Then("results table should {} show descriptions containing {string}")
    public void results_table_should_only_show_descriptions_containing(String notShow, String word) {
        BrowserUtils.wait(1);

        List<String> allDescriptions = BrowserUtils.getElementsText(accountActivity.allDescriptions);

        if (notShow.equalsIgnoreCase("not")) {

            for (String allDescription : allDescriptions) {
                Assert.assertFalse(allDescription.contains(word));
            }
        } else {
            for (String allDescription : allDescriptions) {
                Assert.assertTrue(allDescription.contains(word));
            }
        }
    }

    @Then("results table should show {} result under {}")
    public void results_table_should_show_at_least_one_result_under(String show, String column) {

        List<String> colsContent = new ArrayList<>();

        switch (column.toLowerCase()) {
            case "deposit":
                colsContent = BrowserUtils.getElementsText(accountActivity.allDeposits);
                break;
            case "withdrawal":
                colsContent = BrowserUtils.getElementsText(accountActivity.allWithdrawals);
        }

        int sizeOfItems = colsContent.size();

        if (show.toLowerCase().contains("one")) {

            Assert.assertTrue(sizeOfItems >= 1);
        } else if (show.equalsIgnoreCase("no")) {
            for (String list : colsContent) {
                Assert.assertTrue(list.isEmpty());
            }
        } else {
            Assert.fail("Word undefined");
        }
    }

    @When("user selects type {string}")
    public void user_selects_type_Withdrawal(String type) {

        Select types = new Select (accountActivity.selectType);
        types.selectByVisibleText(type);
        accountActivity.findButton.click();
        BrowserUtils.wait(3);
    }
}
