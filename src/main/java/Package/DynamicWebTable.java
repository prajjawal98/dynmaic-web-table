package Package;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DynamicWebTable {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://demo.opencart.com/admin/index.php");
        driver.manage().window().maximize();

        //login
        WebElement username = driver.findElement(By.name("username"));
        username.clear();
        username.sendKeys("demo");

        WebElement password = driver.findElement(By.name("password"));
        username.clear();
        username.sendKeys("demo");

        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

        //sales-order
        driver.findElement(By.xpath("//a[normalize-space()='Sales']")).click();
        driver.findElement(By.xpath("//a[normalize-space()='Orders']")).click();


        String text = driver.findElement(By.xpath("//div[@class='col-sm-6 text-right']")).getText();
        System.out.println(text);


        int total_pages = Integer.valueOf(text.substring(text.indexOf("(") + 1, text.indexOf("Pages") - 1));
        System.out.println("Total Number of pages:" + total_pages);

        // for(int p=1;p<=total_pages;p++){
        for (int p = 1; p <= 5; p++) {                //(Here we have multiple pages to that we put the number of pages only  5)

            WebElement active_page = driver.findElement(By.xpath("//ul[@class='pagination']/li//span"));
            System.out.println("Active pages: " + active_page.getText());
            active_page.click();

            //to check total no. of rows in a page
            int rows = driver.findElements(By.xpath("//table[@class='table table-bordered table-hover']//tbody/tr")).size();
            System.out.println("total rows in a page " + rows);

            String pageNumber = Integer.toString(p + 1);
            driver.findElement(By.xpath("//ul[@Class='pagination']//li//a[text()='" + pageNumber + "']")).click();


            for (int r = 1; r <= rows; r++) {
                String OrderId = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']//tbody//tr[" + r + "]//td[2]")).getText();

                String CustomerName = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']//tbody//" + "tr[" + r + "]//td[3]")).getText();

                String Status = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']//tbody//tr[" + r + "]//td[4]")).getText();

                if (Status.equals("Pending"))
                    System.out.println("pending details are " + OrderId + " ," + CustomerName + Status);

            }

        }
        driver.close();
    }





}