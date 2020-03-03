package io.swagger.codegen.languages;

import io.swagger.codegen.CodegenConfig;
import io.swagger.codegen.CodegenType;
import io.swagger.codegen.DefaultCodegen;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Arrays;

public class BslClientCodegen extends DefaultCodegen implements CodegenConfig {

    public BslClientCodegen() {
        super();

        supportsInheritance = false;
        modelTemplateFiles.put("models_common.mustache", ".bsl");
        apiTemplateFiles.put("client.mustache", ".bsl");

        /**
         * docs files.
         */
        modelDocTemplateFiles.clear();
        apiDocTemplateFiles.clear();

        outputFolder = "generated-code" + File.separator + this.getName();

        registerKeywords();
    }

    private void registerKeywords() {
        reservedWords.addAll(Arrays.asList(
            "если", "if",
            "тогда", "then",
            "иначе", "else",
            "иначеесли", "elsif",
            "конецесли", "endif",
            "перем", "var",
            "знач", "val",
            "процедура", "procedure",
            "конецпроцедуры", "endprocedure",
            "функция", "function",
            "конецфункции", "endfunction",
            "для", "for",
            "каждого", "each",
            "из", "in",
            "по", "to",
            "пока", "while",
            "цикл", "do",
            "конеццикла", "enddo",
            "возврат", "return",
            "продолжить", "continue",
            "прервать", "break",
            "попытка", "try",
            "исключение", "except",
            "выполнить", "execute",
            "вызватьисключение", "raise",
            "конецпопытки", "endtry",
            "новый", "new",
            "экспорт", "export",
            "и", "and",
            "или", "or",
            "не", "not",
            "ДобавитьОбработчик", "AddHandler",
            "УдалитьОбработчик", "RemoveHandler",
            "булево", "boolean",
            "число", "number",
            "строка", "string",
            "дата", "date",
            "тип", "type",
            "типзнч", "typeof",
            "вычислить", "eval",
            "стрдлина", "strlen",
            "сокрл", "triml",
            "сокрп", "trimr",
            "сокрлп", "trimall",
            "лев", "left",
            "прав", "right",
            "сред", "mid",
            "найти", "find",
            "врег", "upper",
            "нрег", "lower",
            "трег", "title",
            "символ", "char",
            "кодсимвола", "charcode",
            "пустаястрока", "isblankstring",
            "стрзаменить", "strreplace",
            "стрполучитьстроку", "strgetline",
            "стрчислострок", "strlinecount",
            "стрчисловхождений", "stroccurrencecount",
            "год", "year",
            "месяц", "month",
            "день", "day",
            "час", "hour",
            "минута", "minute",
            "секунда", "second",
            "началогода", "begofyear",
            "началомесяца", "begofmonth",
            "началодня", "begofday",
            "началочаса", "begofhour",
            "началоминуты", "begofminute",
            "началоквартала", "begofquarter",
            "конецгода", "endofyear",
            "конецмесяца", "endofmonth",
            "конецдня", "endofday",
            "конецчаса", "endofhour",
            "конецминуты", "endofminute",
            "конецквартала", "endofquarter",
            "неделягода", "weekofyear",
            "деньгода", "dayofyear",
            "деньнедели", "dayofweek",
            "добавитьмесяц", "addmonth",
            "текущаядата", "currentdate",
            "цел", "int",
            "окр", "round",
            "log",
            "log10",
            "sin",
            "cos",
            "tan",
            "asin",
            "acos",
            "atan",
            "exp",
            "pow",
            "sqrt",
            "мин", "min",
            "макс", "max",
            "формат", "format",
            "информацияобошибке", "errorinfo",
            "описаниеошибки", "errordescription",
            "текущийсценарий", "currentscript"));
    }

    @Override
    public CodegenType getTag() {
        return CodegenType.CLIENT;
    }

    @Override
    public String getName() {
        return "bsl";
    }

    @Override
    public String getHelp() {
        return "Generates a 1C:Enterprise client library.";
    }

    @Override
    public String toOperationId(String operationId) {
        // throw exception if method name is empty (should not occur as an auto-generated method name will be used)
        if (StringUtils.isEmpty(operationId)) {
            throw new RuntimeException("Empty method name (operationId) not allowed");
        }

        // method name cannot use reserved keyword, e.g. return
        if (isReservedWord(operationId)) {
            LOGGER.warn(operationId + " (reserved word) cannot be used as method name. Renamed to " + camelize(sanitizeName("call_" + operationId)));
            operationId = "call_" + operationId;
        }

        return camelize(sanitizeName(operationId));
    }

    @Override
    public String toVarName(String name) {
        // sanitize name
        name = sanitizeName(name);

        // if it's all uppper case, do nothing
        if (name.matches("^[A-Z_]*$")) {
            return name;
        }

        // camelize the variable name
        // pet_id => PetId
        name = camelize(name);

        // for reserved word or word starting with number, append _
        if (isReservedWord(name) || name.matches("^\\d.*")) {
            name = escapeReservedWord(name);
        }

        return name;
    }

    @Override
    public String toParamName(String name) {
        // sanitize name
        return toVarName(name);
    }

    @Override
    public String escapeUnsafeCharacters(String input) {
        return input;
    }
}
