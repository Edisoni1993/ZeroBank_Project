package zerobank.library.utilities;

import java.util.*;
import zerobank.library.pages.AccountActivityPage;
import zerobank.library.pages.AccountSummaryPage;
import zerobank.library.pages.DashboardMenuPage;
import zerobank.library.pages.LoginPage;

public class StepDefsUtils {

    static AccountActivityPage accountActivity = new AccountActivityPage();
    static LoginPage loginPage = new LoginPage();
    static AccountSummaryPage accountSummaryPage = new AccountSummaryPage();
    static DashboardMenuPage dashboardMenuPagePage = new DashboardMenuPage();

    public static List<String> getListOf(String listOf){

        List<String> colContent;
        colContent = BrowserUtils.getElementsText(accountActivity.allDeposits);

        switch (listOf){
            case "deposit":
                colContent = BrowserUtils.getElementsText(accountActivity.allDeposits);
                break;
            case "withdrawal":
                colContent = BrowserUtils.getElementsText(accountActivity.allWithdrawals);
        }
        return colContent;
    }
}

