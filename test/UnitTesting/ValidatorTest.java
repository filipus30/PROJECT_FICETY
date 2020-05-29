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
        System.out.println("isAlphaNumeric");
        String s = "";
        boolean expResult = false;
        boolean result = instance.isAlphaNumeric(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAlpha method, of class Validator.
     */
    @Test
    public void testIsAlpha() {
        System.out.println("isAlpha");
        String name = "";
        boolean expResult = false;
        boolean result = instance.isAlpha(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isNumber method, of class Validator.
     */
    @Test
    public void testIsNumber() {
        System.out.println("isNumber");
        String text = "";
        boolean expResult = false;
        boolean result = instance.isNumber(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValidDate method, of class Validator.
     */
    @Test
    public void testIsValidDate() {
        System.out.println("isValidDate");
        String text = "";
        boolean expResult = false;
        boolean result = instance.isValidDate(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
