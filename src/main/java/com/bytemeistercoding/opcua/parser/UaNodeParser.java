/*
 * Copyright (c) 2025 Johannes Kepler University Linz
 * LIT Cyber-Physical Systems Lab
 *
 * Contributors: Mainak Majumder
 *
 */

package com.bytemeistercoding.opcua.parser;

import com.bytemeistercoding.opcua.uanodeset.NodeIdAlias;
import com.bytemeistercoding.opcua.uanodeset.UANode;
import com.bytemeistercoding.opcua.uanodeset.UANodeSet;
import com.bytemeistercoding.opcua.uanodeset.UriTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UaNodeParser {

    HashMap<Integer, String> namespaceIndexMap = new HashMap<>();

    public void generateNamespaceIndexMap(UANodeSet nodeset) {
        UriTable namespaceTable = nodeset.getNamespaceUris();
        int index = 1;
        for (String namespace : namespaceTable.getUri()) {
            namespaceIndexMap.put(index++, namespace);
        }
    }

    private void parseAliasTable(UANodeSet nodeset) {
        Map<String, Integer> aliasTable = new HashMap<>();
        for (NodeIdAlias alias : nodeset.getAliases().getAliases()) {
            aliasTable.put(alias.getAlias(), Integer.parseInt(alias.getValue().substring(2)));
        }
    }

    public List<UANode> getNodes(UANodeSet nodeset, String nodeClass) {
        List<UANode> nodes = new ArrayList<>();
        for (UANode node : nodeset.getUaNodes()) {
            if (node.getClass().getSimpleName().equals(nodeClass))
                nodes.add(node);
        }
        return nodes;
    }
}
