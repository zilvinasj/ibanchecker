package com.demo.ibanchecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public AppTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(AppTest.class);
    }

    /**
     * Test a few IBANS
     * 
     * @throws IOException
     */
    public void testApp() throws IOException
    {

        List<String> lines = Files.lines(Paths.get("src/main/resources/ibans.txt")).collect(Collectors.toList());
        assert (!App.validateIBAN(lines.get(0)));
        assert (App.validateIBAN(lines.get(1)));
        assert (App.validateIBAN(lines.get(2)));
        assert (!App.validateIBAN(lines.get(3)));
        assert (!App.validateIBAN(lines.get(4)));
        assert (!App.validateIBAN("zzz"));
        assert (!App.validateIBAN("ZA123567890-"));

    }
}
