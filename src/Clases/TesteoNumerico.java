package Clases;
public class TesteoNumerico{

    public static void main(String[] args) {
        String str = "001-001-9854001";
        boolean isNumeric =  str.matches("[+]?\\d*(\\.\\d+)?");
        System.out.println(isNumeric);
        str = "121xy";
        isNumeric =  str.matches("[+-]?\\d*(\\.\\d+)?");
        System.out.println(isNumeric);
        str = "0x234";
        isNumeric =  str.matches("[+-]?\\d*(\\.\\d+)?");
        System.out.println(isNumeric);
    }
}