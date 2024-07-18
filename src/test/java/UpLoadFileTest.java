import org.junit.jupiter.api.Test;

public class UpLoadFileTest {
    @Test
    public void test1(){
        String fileName = "ererwe.jpg";
        String suffex = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffex);
    }
}
