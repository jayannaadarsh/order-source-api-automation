import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;

public class Login_ValidTest extends BaseTest {

   @Test
    public void validLoginTest()
    {
        //System.out.println("starting execution");
        Assert.assertEquals(200,response.getStatusCode());

        //System.out.println("end login valid test");
    }


}
