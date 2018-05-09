package application;

public class Function {
    private String functionName;
    private String functionOperator;
    private String functionType;

    public Function(String functionName, String functionOperator, String functionType) {
        this.functionName = functionName;
        this.functionOperator = functionOperator;
        this.functionType = functionType;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionOperator() {
        return functionOperator;
    }

    public void setFunctionOperator(String functionOperator) {
        this.functionOperator = functionOperator;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }
}
