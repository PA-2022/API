package pa.codeup.codeup.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseCode {
    private String  output;

    public ResponseCode(){

    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
