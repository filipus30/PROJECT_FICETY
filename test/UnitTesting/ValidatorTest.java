/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnitTesting;

import ficety.bll.Validator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macos
 */
public class ValidatorTest {
    Validator instance = new Validator();
    
    public ValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isAlphaNumeric method, of class Validator.
     */
    @Test
    public void testIsAlphaNumeric() {
        String s = "123";
        String textNumber = "NotMyNumber1";
        String text = "NotMyNumber";
        
        assertEquals(false, instance.isAlphaNumeric(s));
        assertEquals(false, instance.isAlphaNumeric(textNumber));
        assertEquals(true, instance.isAlphaNumeric(text));
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of isAlpha method, of class Validator.
     */
    @Test
    public void testIsAlpha() {
        String name = "Marcus";
        String name2 = "Marcus Smith";
        String namewithMail = "Marcus@bla.com";
        String nameNumbers = "Marcus2Bad";
        
        assertEquals(true, instance.isAlpha(name));
        assertEquals(false, instance.isAlpha(name2));
        assertEquals(false, instance.isAlpha(namewithMail));
        assertEquals(false, instance.isAlpha(nameNumbers));
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }
       @Test
    public void testIsCharOrSpace() {
        String name = "Marcus";
        String name2 = "Marcus Smith";
        String namewithMail = "Marcus@bla.com";
        String nameNumbers = "Marcus2Bad";
        
        assertEquals(true, instance.checkLettersAndSpaces(name));
        assertEquals(true, instance.checkLettersAndSpaces(name2));
        assertEquals(false, instance.checkLettersAndSpaces(namewithMail));
        assertEquals(false, instance.checkLettersAndSpaces(nameNumbers));
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }

    /**
     * Test of isNumber method, of class Validator.
     */
    @Test
    public void testIsNumber() {
        String numbers = "123";
        String text = "Hello";
        
        assertEquals(true, instance.isNumber(numbers));
        assertEquals(false, instance.isNumber(text));
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of isValidDate method, of class Validator.
     */
    @Test
    public void testIsValidDate() {
        String text = "2020-04-29 01:03:38";
        String falsetext = "2020-04-29T01:03:38"; //Wrong dateTime format
        String falsetext2 = "2020-04-h18 01:03:38"; //extra letter
        String falsetext3 = "2020-04-78 01:03:38"; //date out of bounds.
        
        assertEquals(true, instance.isValidDate(text));
        assertEquals(false, instance.isValidDate(falsetext));
        assertEquals(false, instance.isValidDate(falsetext2));
        assertEquals(false, instance.isValidDate(falsetext3));
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
