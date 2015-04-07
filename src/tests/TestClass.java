package tests;

import static org.junit.Assert.*;

import org.junit.*;

public class TestClass {
	
	@Test
	public void mySimpleTest(){
        Assert.assertEquals(  2,  1 + 1  );
    }
	
	@Test
	public void mySimpleTest2(){
        Assert.assertEquals(  3,  1 + 1 + 1 );
    }

}
