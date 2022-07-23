package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pa.codeup.codeup.dto.ResponseOutputDAO;
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

    @PostMapping("/code")
    @ResponseBody
    public ResponseEntity<Object> executeCode(@RequestBody ExternalCode externalCode) throws ScriptException {
        // if (externalCode.getLanguage().equals("JS")) {
        //     String codeOutput = codeService.executeJs(externalCode.getCode());
        //     return ResponseEntity.ok(new ResponseOutputDAO(codeOutput));
        // } else 
        if (externalCode.getLanguage().equals("python")) {
            return ResponseEntity.ok(this.codeService.executePython(externalCode));
        }
         else {
            return ResponseEntity.ok(new ResponseOutputDAO("Language not supported"));
        }
    }
}
