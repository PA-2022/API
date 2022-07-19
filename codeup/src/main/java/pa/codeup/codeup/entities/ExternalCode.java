package pa.codeup.codeup.entities;

public class ExternalCode {
    private String code;
    private String langage;

    public ExternalCode(String code, String langage) {
        this.code = code;
        this.langage = langage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return langage;
    }

    public void setLanguage(String langage) {
        this.langage = langage;
    }
}
