package test.ioc.context; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* FileSystemXmlApplicationContext Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 18, 2018</pre> 
* @version 1.0 
*/
public class FileSystemXmlApplicationContextTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getBean(String name) 
* 
*/ 
@Test
public void testGetBean() throws Exception { 
//TODO: Test goes here...
    FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContextTest();
    System.out.printf(context.getBean(""));
} 


} 
