package ru.job4j.multithreding.pool;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.multithreading.pool.EmailNotification;
import ru.job4j.multithreading.pool.User;

public class EmailNotificationTest {
    private EmailNotification emailNotification;


    @Before
    public void setup() {
        emailNotification = new EmailNotification();
    }

    @Test
    public void whenSendToEmailItMustBeCorrect() throws InterruptedException {
        emailNotification.execute(new User("AnotherOneName", "AnotherOneEmail"));
        emailNotification.execute(new User("AndAnotherOneName", "AndAnotherOneEmail"));
        emailNotification.execute(new User("FirstName", "FirstEmail"));
        emailNotification.execute(new User("SecondName", "SecondEmail"));
        emailNotification.execute(new User("ThirdName", "ThirdEmail"));
        emailNotification.execute(new User("FourName", "FourEmail"));
        emailNotification.execute(new User("FiveName", "FiveEmail"));
        emailNotification.execute(new User("SixName", "SixEmail"));
        Thread.sleep(3000);
        emailNotification.close();
    }
}
