package pa.codeup.codeup.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pa.codeup.codeup.dto.ResponseOutputDAO;
import pa.codeup.codeup.entities.ExternalCode;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.StringWriter;


@Service
public class CodeService {

    private final RestTemplate restTemplate;

    public CodeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    // public String executeJs(String code) throws ScriptException {

    //     StringWriter scriptOutput = new StringWriter();

    //     ScriptEngine graalEngine = new ScriptEngineManager().getEngineByName("graal.js");
    //     graalEngine.getContext().setWriter(scriptOutput);

    //     graalEngine.eval(code);
    //     return scriptOutput.toString();

    // }

    public ResponseEntity<Object> executeCode(ExternalCode code){
        String url = "http://46.105.14.4:8099/codeEditor";
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, code, Object.class);
        // Object responseBody = responseEntity.getBody();
        
        return responseEntity;
    }

    
}
