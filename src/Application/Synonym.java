package Application;

public class Synonym {

    private String syName;
    private String syColumn;
    private String syTable;

    public Synonym(String syName, String syColumn, String syTable) {
        this.syName = syName;
        this.syColumn = syColumn;
        this.syTable = syTable;
    }

    public String getSyName() {
        return syName;
    }

    public void setSyName(String syName) {
        this.syName = syName;
    }

    public String getSyColumn() {
        return syColumn;
    }

    public void setSyColumn(String syColumn) {
        this.syColumn = syColumn;
    }

    public String getSyTable() {
        return syTable;
    }

    public void setSyTable(String syTable) {
        this.syTable = syTable;
    }
}