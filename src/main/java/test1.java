import utilities.PropertyFileReader;

import java.io.IOException;

public class test1
{

    public static  void  main(String args[]) throws IOException {
        PropertyFileReader prop = new PropertyFileReader();
        String val=prop.readFile("");
        System.out.println(val);
    }
}
