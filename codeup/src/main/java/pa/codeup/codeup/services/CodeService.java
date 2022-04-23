package pa.codeup.codeup.services;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.StringWriter;


public class CodeService {
    public String executeJs(String code) throws ScriptException {

        StringWriter scriptOutput = new StringWriter();

        ScriptEngine graalEngine = new ScriptEngineManager().getEngineByName("graal.js");
        graalEngine.getContext().setWriter(scriptOutput);

        graalEngine.eval(code);
        return scriptOutput.toString();

    }
}
