<#macro javadoc lines pad="">
<#list lines>
${pad}/**
<#items as line>${pad} * ${line?no_esc}
</#items>
${pad} */
</#list>
</#macro>

<#macro func_params params><#list params as inputParam>${inputParam.type?no_esc} ${inputParam.name}<#sep>, </#sep></#list></#macro>

<#macro solution_imports>
import java.util.*;
import com.hightower.dailypractice.core.NotImplemented;
import com.hightower.dailypractice.core.model.*;
</#macro>

<#macro test_imports>
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.hightower.dailypractice.core.model.*;
import static org.junit.jupiter.api.Assertions.*;
import com.hightower.dailypractice.core.NotImplementedCondition;
import org.junit.jupiter.api.extension.ExtendWith;
</#macro>

<#macro func name parameters resultType>
    public ${resultType?no_esc} ${name}(<@func_params params=parameters/>) {
        <#nested>
    }
</#macro>