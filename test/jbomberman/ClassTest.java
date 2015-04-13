package jbomberman;

import org.junit.*;

public class ClassTest {
	
	@Test
	public void mySimpleTest(){
        Assert.assertEquals(  2,  1 + 1  );
    }
	
	@Test
	public void mySimpleTest2(){
        Assert.assertEquals(  3,  1 + 1 + 1 );
    }

}
