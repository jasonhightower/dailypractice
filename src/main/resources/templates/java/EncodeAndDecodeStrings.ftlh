<#import "common.ftlh" as com>
package ${pkg};

<@com.solution_imports/>

<@com.javadoc lines=problem.description?split("\n")/>
public class ${problem.name} {

    @NotImplemented
    public String encode(String[] strs) {
        return null;
    }

    @NotImplemented
    public String[] decode(String encoded) {
        return null;
    }

}