package pa.codeup.codeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pa.codeup.codeup.entities.ExternalCode;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.UserRepository;
import pa.codeup.codeup.services.CodeService;

import javax.script.ScriptException;
import java.util.List;

@Controller
public class CodeController {

	@Autowired
	private CodeService codeService;

	@GetMapping("/code")
	@ResponseBody
	public String executeCode(@RequestBody ExternalCode externalCode) throws ScriptException {
		if(externalCode.getLanguage().equals("JS")){
			return codeService.executeJs(externalCode.getCode());
		}
		else {
			return "bad code type";
		}
	}
}
