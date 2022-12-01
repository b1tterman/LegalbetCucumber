package legalbet;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;


import static com.codeborne.selenide.Selenide.$x;


public class TestStepDefinitions {

    private final SelenideElement bukmekerMenu = $x("//li[@class=\"panel__item  panel__item_bookmakers\"]");
    private final SelenideElement legalnieBukmekeri = $x("//a[@href=\"/bukmekerskye-kontory/sets/legalnye-v-rossii/\"]");
    private final SelenideElement lastBonus10k = $x("(//div[@class='bonus ']//a[contains(text(), '10 000')]//ancestor-or-self::td)[last()]//following::td//a");
    private final SelenideElement otzivyCount = $x("(//div[@class='bonus ']//a[contains(text(), '10 000')]//ancestor-or-self::td)[last()]//following::td//a");
    private final SelenideElement nameBukmeker = $x("(//div[@class='bonus ']//a[contains(text(), '10 000')]//ancestor-or-self::td)[last()]//preceding-sibling::td//a");
    //private final SelenideElement otzivyCountInside = $x("h2[@class='heading' and normalize-space(text()) = 'Отзывы']//span[@class='count']");
    private final SelenideElement otzivyCountInside = $x("//div[@class=\"feedbacks__header-count\"]");


    private Integer count1;
    private Integer count2;


    @io.cucumber.java.AfterStep
    public void makeScreenshot(){
        Selenide.screenshot(System.currentTimeMillis() + "step");
    }

    public void setUp(){
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.driverManagerEnabled = true;
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;
    }

    @Before
    public void init(){
        setUp();
    }

    @After
    public void tearDown()
    {
        Selenide.closeWebDriver();
    }

    @Given("Открываю сайт {string}")
    public void openWebSite(String url) {

        Selenide.open(url);

    }

    @When("Открыть меню Букмекеры")
    public void chooseBukmekers(){
        bukmekerMenu.hover();
    }

    @And("Выбрать Все легальные букмекеры")
    public void chooseLegalBukmeker(){
        legalnieBukmekeri.click();
    }

    @And("Из списка букмекеров, у которых бонус {int}, выбрать последнего и запомнить количество отзывов. Логировать количество отзывов и название букмекера.")
    public void chooseLast10kBukmekerAndReviewCount(int arg0) {
        count1 = Integer.parseInt(otzivyCount.getText());
        System.out.println("Количество отзывов: " + count1);
        String s = nameBukmeker.getAttribute("href");
        String[] parts = s.split("/");
        System.out.println("Название букмекера '" + parts[4] + "'");
        lastBonus10k.click();

    }

    @And("Зайти на данного букмекера и найти количество отзывов")
    public void checkBukmekerReviews() {
        count2 = Integer.parseInt(otzivyCountInside.getText());
        System.out.println("Количество отзывов на странице букмекера: " + count2);
    }


    @Then("Сравнить количество отзывов с прошлой страницей, вывести в лог результат")
    public void checkTwoReviewCounts() {
        org.junit.Assert.assertEquals(count1, count2);
    }
}
