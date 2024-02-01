package tests;

import models.StudentDataModel;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import steps.RegistrationSteps;

import java.text.ParseException;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class StudentRegistrationTest {

    @Managed(driver = "chrome")
    protected WebDriver driver;

    @Steps
    protected RegistrationSteps registrationSteps;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "c:\\ChromeDriver\\120\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        registrationSteps = new RegistrationSteps(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void registrationFormTest() throws ParseException {
        StudentDataModel studentData = new StudentDataModel();

        registrationSteps.openByLinkFillAndSubmit(studentData);

        assertReflectionEquals("После заполнения формы не появилось модальное окно-подтверждение", true, registrationSteps.isSubmitFormDisplay());
        assertReflectionEquals("Заголовок модального окна-подтверждения отличается от ожидаемого", "Thanks for submitting the form", registrationSteps.getModalTitleText());
        assertReflectionEquals("Данные студента в окне-подтверждении отличаюся от введенных пользователем", studentData, registrationSteps.getStudentData());
    }
}
