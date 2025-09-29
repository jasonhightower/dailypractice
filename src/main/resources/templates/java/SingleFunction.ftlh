<#import "common.ftlh" as com>
package ${pkg};

<@com.solution_imports/>

<@com.javadoc lines=problem.description?split("\n")/>
public class ${problem.name} {

    @NotImplemented
    <@com.func name="solution" parameters=args.input resultType=args.output>
        return ${util.defaultReturn(args.output)?no_esc};
    </@com.func>

}