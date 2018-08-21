package org.yufan.result;

import java.util.List;

public class EasyUIResult<T> {

    private Integer total;

    private List<T> rows;

    public EasyUIResult(){

    }

    public EasyUIResult(Integer total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
