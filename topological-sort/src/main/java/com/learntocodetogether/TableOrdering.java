package com.learntocodetogether;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(staticName = "of")
public class TableOrdering implements Ordering {
    private final String tableName;
    private final List<String> foreignKeyTables;

    @Override
    public String getNode() {
        return this.tableName;
    }

    @Override
    public List<String> getDependencies() {
        return this.foreignKeyTables;
    }
}
