package pages;

import models.Gender;
import models.Month;
import models.StudentDataModel;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Fail.fail;

public class RegistrationPage extends PageObject {

    private static final By firstNameInputLocator = By.id("firstName");
    private static final By lastNameInputLocator = By.id("lastName");
    private static final By emailInputLocator = By.id("userEmail");
    private static final By userNumberInputLocator = By.cssSelector("*[id='userNumber-wrapper'] input");
    private static final By dateOfBirthInputLocator = By.id("dateOfBirthInput");
    private static final By yearSelectLocator = By.cssSelector(".react-datepicker__year-select");
    private static final By monthSelectLocator = By.cssSelector(".react-datepicker__month-select");
    private static final By subjectsInputLocator = By.cssSelector("*[class^='subjects'] input");
    private static final By uploadPictureButtonLocator = By.id("uploadPicture");
    private static final By currentAddressInputLocator = By.id("currentAddress");
    private static final By stateSelectLocator = By.xpath("//*[text()='Select State']");
    private static final By citySelectLocator = By.xpath("//*[text()='Select City']");
    private static final By submitButtonLocator = By.id("submit");

    private WebElementFacade elementByText(String text) {
        return find(By.xpath(String.format("//*[text()='%s']", text)));
    }

    private WebElementFacade genderRadioButtonLocator(Gender gender) {
        return find(By.xpath(String.format("//label[text()='%s']", gender.getGender())));
    }

    private WebElementFacade yearItemInSelect(int year) {
        return find(By.xpath(String.format("//*[contains(@class, 'year-dropdown')]//*[@value='%s']", year)));
    }

    private WebElementFacade monthItemInSelect(Month month) {
        return find(By.xpath(String.format("//*[contains(@class, 'month-select')]//*[text()='%s']", month.getEngName())));
    }

    private WebElementFacade dayItem(int day, Month month) {
        return find(By.xpath(String.format("//*[contains(@class, 'datepicker__week')]//*[contains(@aria-label, '%s') and text()=%s]",
                month.getEngName(), day)));
    }

    public RegistrationPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    private void clickBy(By locator) {
        find(locator).click();
    }

    public void openByLinkFillAndSubmit(StudentDataModel studentData) {
        openByLink();
        fillFormAndSubmit(studentData);
    }

    private void openByLink() {
        getDriver().navigate().to("https://demoqa.com/automation-practice-form");
    }

    public void fillFormAndSubmit(StudentDataModel studentData) {
        enterInto(studentData.getFirstName(), firstNameInputLocator);
        enterInto(studentData.getLastName(), lastNameInputLocator);
        enterInto(studentData.getEmail(), emailInputLocator);
        fillGender(studentData);
        enterInto(studentData.getMobilePhone(), userNumberInputLocator);
        fillBirthDate(studentData.getBirthDate());
        fillSubjects(studentData.getSubjects());
        uploadPicture(studentData.getPicturePath());
        fillCurrentAddress(studentData.getCurrentAddress());
        selectValue(studentData.getState(), stateSelectLocator);
        selectValue(studentData.getCity(), citySelectLocator);
        clickWithJs(find(submitButtonLocator));
    }

    private void clickWithJs(WebElementFacade element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    private void selectValue(String value, By selectLocator) {
        WebElementFacade select = find(selectLocator);
        select.click();
        clickWithJs(elementByText(value));
    }

    private void fillCurrentAddress(String currentAddress) {
        enterInto(currentAddress, currentAddressInputLocator);
    }

    private void uploadPicture(String path) {
        uploadFile(uploadPictureButtonLocator, String.format("/%s", path));
    }

    private void fillBirthDate(Calendar birthDate) {
        clickBy(dateOfBirthInputLocator);
        selectYear(birthDate.get(Calendar.YEAR));
        selectMonth(Month.getMonthByNumber(birthDate.get(Calendar.MONTH) + 1));
        selectDay(birthDate.get(Calendar.DAY_OF_MONTH), Month.getMonthByNumber(birthDate.get(Calendar.MONTH) + 1));
    }

    private void selectYear(int year) {
        clickBy(yearSelectLocator);
        yearItemInSelect(year).click();
    }

    private void selectMonth(Month month) {
        clickBy(monthSelectLocator);
        monthItemInSelect(month).click();
    }

    private void selectDay(int dayOfMonth, Month month) {
        dayItem(dayOfMonth, month).click();
    }

    private void fillGender(StudentDataModel studentData) {
        genderRadioButtonLocator(studentData.getGender()).click();
    }

    private void fillSubjects(List<String> subjects) {
        for (String subject :
                subjects) {
            fillSubject(subject);
        }
    }

    private void fillSubject(String subject) {
        enterInto(subject, subjectsInputLocator);
        sendEnterKey(subjectsInputLocator);
    }

    private void sendEnterKey(By elementLocator) {
        find(elementLocator).sendKeys(Keys.ENTER);
    }

    public void enterInto(String value, By elementLocator) {
        WebElementFacade element = find(elementLocator);
        clearElement(element);
        sendKeysElement(value, element);
    }

    private void clearElement(WebElement element) {
        try {
            element.clear();
        } catch (Exception e) {
            fail(String.format("%s## Не смогли очистить поле ввода - %s ##", e.getMessage(), element.toString()));
        }
    }

    private void sendKeysElement(String value, WebElement element) {
        try {
            element.sendKeys(value);
        } catch (Exception e) {
            fail(String.format("%s ## Не смогли заполнить поле ввода - %s ##", e.getMessage(), element.toString()));
        }
    }

    void uploadFile(By locator, String filePath) {
        WebElementFacade xpath = find(locator);
        try {
            upload(filePath).to(xpath);
            waitABit(1000);
        } catch (WebDriverException e) {
            fail(String.format("%s ## Не смогли загрузить файл - %s ##", e.getMessage(), filePath));
        }
    }
}
