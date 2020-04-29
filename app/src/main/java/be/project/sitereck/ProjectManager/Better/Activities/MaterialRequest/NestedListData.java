package be.project.sitereck.ProjectManager.Better.Activities.MaterialRequest;

import java.util.List;

import be.project.sitereck.ProjectManager.POJO.MatReqItemClass;

public class NestedListData {
    String ProName;
    List<MatReqItemClass> list;

    public NestedListData(String proName, List<MatReqItemClass> list) {
        ProName = proName;
        this.list = list;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public List<MatReqItemClass> getList() {
        return list;
    }

    public void setList(List<MatReqItemClass> list) {
        this.list = list;
    }
}
