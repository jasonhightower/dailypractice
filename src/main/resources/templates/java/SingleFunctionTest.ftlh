<#import "common.ftlh" as com>
package ${pkg};

<@com.test_imports/>

@ExtendWith(NotImplementedCondition.class)
public class ${problem.name}Test {

    private ${problem.name} classUnderTest;

    @BeforeEach
    void setUp() {
        this.classUnderTest = new ${problem.name}Test();
    }

    <#-- for each test case create a test function -->
    @Test
    <#-- ability to loop through each test case -->
    <#-- Name of each Test case  -->
    <#-- input values for each arg -->
    <#-- expected output for each case -->
    <#-- Optional: how to verify/assert the results -->
    <#-- Optional: how to adapt actual output to expected output type (perhaps a custom function to handle this conversion) -->
    <@com.func name="testCaseX" input=args.input returnType=args.output returnDefault=util.defaultReturn(args.output)/>

}