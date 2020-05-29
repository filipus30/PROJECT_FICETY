/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.bll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author macos
 */
public class ValidatorTest {
    
    public ValidatorTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
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
        boolean result = Validator.isAlphaNumeric(s);
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
        Validator instance = new Validator();
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
        Validator instance = new Validator();
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
        Validator instance = new Validator();
        boolean expResult = false;
        boolean result = instance.isValidDate(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
