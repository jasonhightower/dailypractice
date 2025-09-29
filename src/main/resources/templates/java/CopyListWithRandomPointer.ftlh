<#import "common.ftlh" as com>
package ${pkg};

<@com.solution_imports/>

<@com.javadoc lines=problem.description?split("\n")/>
public class ${problem.name} {

    private static class LinkNodeWithRandom<T> extends LinkNode<T> {
        public LinkNodeWithRandom<T> random;

        public LinkNodeWithRandom(T value) {
            super(value);
        }
    }

    @NotImplemented
    public LinkNodeWithRandom solution(LinkNodeWithRandom node) {
        return null;
    }

}