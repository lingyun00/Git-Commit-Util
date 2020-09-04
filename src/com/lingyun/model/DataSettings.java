package com.lingyun.model;

import java.util.List;

public class DataSettings {

    private String template;
    private List<TypeAlias> typeAliases;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<TypeAlias> getTypeAliases() {
        return typeAliases;
    }

    public void setTypeAliases(List<TypeAlias> typeAliases) {
        this.typeAliases = typeAliases;
    }


}
