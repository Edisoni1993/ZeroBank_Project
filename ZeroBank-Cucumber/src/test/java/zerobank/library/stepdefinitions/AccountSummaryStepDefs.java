package zerobank.library.stepdefinitions;

import zerobank.library.pages.DashboardMenuPage;
import zerobank.library.utilities.BrowserUtils;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import java.util.Arrays;
import java.util.List;

public class AccountSummaryStepDefs {

    DashboardMenuPage dashboardMenuPage = new DashboardMenuPage();

    @Then("Account summary page should have the following account types:{}")
    public void page_should_have_to_following_account_types(String types) {

        List<String> listOfItems = Arrays.asList(types.split("\\s*,\\s*"));

        for (int i = 0; i < listOfItems.size(); i++) {
            listOfItems.set(i,listOfItems.get(i).trim());
        }

        String boardText = null;

        WebElement boardElement;

        for (int i = 0; i < listOfItems.size(); i++) {

            boardText = dashboardMenuPage.getBoardHeadByIndex(i+1).getText();
            Assert.assertEquals(boardText,listOfItems.get(i));
            boardElement = dashboardMenuPage.getBoardHeadByName(listOfItems.get(i));
            Assert.assertTrue(boardElement.isDisplayed());
        }
    }

    @Then("{} table must have columns {}, {} and {}")
    public void table_must_have_columns(String tableName, String col1,String col2, String col3) {

        List<String> tableCols = BrowserUtils.getElementsText(dashboardMenuPage.getTableColumnsByTableName(tableName));
        Assert.assertEquals(tableCols.get(0),col1);
        Assert.assertEquals(tableCols.get(1),col2);
        Assert.assertEquals(tableCols.get(2),col3);
    }
}
