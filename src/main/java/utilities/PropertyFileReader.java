package utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader
{
    public InputStream inputstream;
    String result="";
    String assertionresult = "";

    public  String readFile(String key) throws IOException {

        Properties prop = new Properties();
        try {

            String propFilename = "application.properties";
            inputstream = getClass().getClassLoader().getResourceAsStream(propFilename);

            if(inputstream != null){
                prop.load(inputstream);
                result=prop.getProperty(key);
            }

            else{
                throw new FileNotFoundException("Property file " + propFilename + " not found in the classpath");
            }


        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        finally {
            inputstream.close();
        }

        return result;
    }

    public  String readForAssertion(String key) throws IOException {

        Properties prop = new Properties();
        try {

            String propFilename = "jsonasserton.properties";
            inputstream = getClass().getClassLoader().getResourceAsStream(propFilename);

            if(inputstream != null){
                prop.load(inputstream);
                assertionresult=prop.getProperty(key);
            }

            else{
                throw new FileNotFoundException("Property file " + propFilename + " not found in the classpath");
            }


        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        finally {
            inputstream.close();
        }

        return assertionresult;
    }
}
