<#import "common.ftlh" as com>
package ${pkg};

<@com.solution_imports/>

<@com.javadoc lines=problem.description?split("\n")/>
public class ${problem.name} {


    @NotImplemented
    public void set(String key, String value, int timestamp) {

    }

    @NotImplemented
    public String get(String key, int timestamp) {
        return null;
    }

}