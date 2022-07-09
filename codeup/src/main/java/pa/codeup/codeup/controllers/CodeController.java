package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pa.codeup.codeup.entities.ExternalCode;
import pa.codeup.codeup.services.CodeService;

import javax.script.ScriptException;

@RestController
public class CodeController {


    private final CodeService codeService;

    @Autowired
    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/code")
    @ResponseBody
    public String executeCode(@RequestBody ExternalCode externalCode) throws ScriptException {
        if (externalCode.getLanguage().equals("JS")) {
            return codeService.executeJs(externalCode.getCode());
        } else {
            return "bad code type";
        }
    }
}
