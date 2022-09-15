package jsonserversuite;

import org.testng.annotations.DataProvider;

public class TestingData {

    @DataProvider(name = "DataForPost")
    public Object[][] dataForTest(){

        // First Way
        Object[][] data = new Object[2][3];

        data[0][0] = "Jake";
        data[0][1] = "Gyllenhall";
        data[0][2] = 2;

        data[1][0] = "Robert";
        data[1][1] = "De Niro";
        data[1][2] = 1;

        return data;

        // Second straight forward way
//        return new Object[][]{
//                {"Morgan", "Freeman", 2},
//                {"Gal", "Gadot", 1}
//        };
    }

}
