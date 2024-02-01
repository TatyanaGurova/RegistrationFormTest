package pages;

import models.Gender;
import models.StudentDataModel;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SubmitFormPage extends PageObject {

    private static final By modalWindowTitleLocator = By.xpath("//*[@class='modal-title h4']");
    private static final By studentNameTextFieldLocator = By.xpath("//td[text()='Student Name']/following-sibling::td");
    private static final By studentEmailTextFieldLocator = By.xpath("//td[text()='Student Email']/following-sibling::td");
    private static final By genderTextFieldLocator = By.xpath("//td[text()='Gender']/following-sibling::td");
    private static final By mobilePhoneTextFieldLocator = By.xpath("//td[text()='Mobile']/following-sibling::td");
    private static final By birthDateTextFieldLocator = By.xpath("//td[text()='Date of Birth']/following-sibling::td");
    private static final By subjectsTextFieldLocator = By.xpath("//td[text()='Subjects']/following-sibling::td");
    private static final By pictureTextFieldLocator = By.xpath("//td[text()='Picture']/following-sibling::td");
    private static final By addressTextFieldLocator = By.xpath("//td[text()='Address']/following-sibling::td");
    private static final By stateAndCityTextFieldLocator = By.xpath("//td[text()='State and City']/following-sibling::td");


    public SubmitFormPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public boolean isDisplayed() {
        return find(modalWindowTitleLocator).isDisplayed();
    }

    public String getModalTitleText() {
        return find(modalWindowTitleLocator).getText();
    }

    public StudentDataModel getStudentData() throws ParseException {
        String[] nameAndSurname = getStrings(studentNameTextFieldLocator);
        String name = nameAndSurname[0];
        String surname = nameAndSurname[1];
        String email = getText(studentEmailTextFieldLocator);
        Gender gender = Gender.convertToGender(getText(genderTextFieldLocator));
        String mobile = getText(mobilePhoneTextFieldLocator);
        Calendar birthDate = getBirthDate();
        List<String> subjects = getSubjects();
        String picture = getText(pictureTextFieldLocator);
        String address = getText(addressTextFieldLocator);
        String[] stateAndCity = getStrings(stateAndCityTextFieldLocator);
        String state = stateAndCity[0];
        String city = stateAndCity[1];
        return new StudentDataModel(name, surname, email, gender, mobile, birthDate, subjects, picture, address, state, city);
    }

    private String getText(By locator) {
        return find(locator).getText();
    }

    private List<String> getSubjects() {
        String[] subjects =  getText(subjectsTextFieldLocator).split(",");
        List<String> result = new ArrayList<>();
        for (String subject : subjects) {
            result.add(subject.trim());
        }
        return result;
    }

    private String[] getStrings(By elementLocator) {
        WebElementFacade element = find(elementLocator);
        String[] result = new String[2];
        String[] res = element.getText().split(" ");
        for (int i = 0; i < 2; i++) {
            if (i < res.length)
                result[i] = res[i];
            else
                result[i] = "";
        }
        return result;
    }

    private Calendar getBirthDate() throws ParseException {
        String birthDate = getText(birthDateTextFieldLocator);
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        Date date = dateFormat.parse(birthDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
