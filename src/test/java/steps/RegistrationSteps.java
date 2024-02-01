package steps;

import lombok.NoArgsConstructor;
import models.StudentDataModel;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebDriver;
import pages.RegistrationPage;
import pages.SubmitFormPage;

import java.text.ParseException;

@NoArgsConstructor
public class RegistrationSteps {

    protected RegistrationPage registrationPage;
    protected SubmitFormPage submitFormPage;

    public RegistrationSteps(WebDriver webDriver) {
        registrationPage = new RegistrationPage(webDriver);
        submitFormPage = new SubmitFormPage(webDriver);
    }

    @Step
    public void openByLinkFillAndSubmit(StudentDataModel studentData) {
        registrationPage.openByLinkFillAndSubmit(studentData);
    }

    @Step
    public boolean isSubmitFormDisplay() {
        return submitFormPage.isDisplayed();
    }

    @Step
    public String getModalTitleText() {
        return submitFormPage.getModalTitleText();
    }

    @Step
    public StudentDataModel getStudentData() throws ParseException {
        return submitFormPage.getStudentData();
    }
}
