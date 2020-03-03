package io.swagger.codegen.bsl;

import io.swagger.codegen.CodegenModel;
import io.swagger.codegen.DefaultCodegen;
import io.swagger.codegen.languages.BslClientCodegen;
import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.junit.Test;
import org.testng.Assert;

import java.util.Map;

public class BslModelsGenTest {

    @Test
    public void canGenerateModelForBsl(){
        final Swagger swagger
                = new SwaggerParser()
                .read("src/test/resources/2_0/petstore.json");
        final DefaultCodegen codegen = new BslClientCodegen();
        final Map<String, Model> models = swagger.getDefinitions();

        CodegenModel model = codegen.fromModel("User", models.get("User"), models);
        Assert.assertEquals(model.name, "User");
    }

}
