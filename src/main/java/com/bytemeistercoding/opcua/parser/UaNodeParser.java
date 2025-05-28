/*
 * Copyright (c) 2025 Johannes Kepler University Linz
 * LIT Cyber-Physical Systems Lab
 *
 * Contributors: Mainak Majumder
 *
 */

package com.bytemeistercoding.opcua.parser;

import com.bytemeistercoding.opcua.uanodeset.UANode;
import com.bytemeistercoding.opcua.uanodeset.UANodeSet;

import java.util.ArrayList;
import java.util.List;

public class UaNodeParser {

    public List<UANode> getNodes(UANodeSet nodeset, String nodeClass) {
        List<UANode> nodes = new ArrayList<>();
        for (UANode node : nodeset.getUaNodes()) {
            if (node.getClass().getSimpleName().equals(nodeClass))
                nodes.add(node);
        }
        return nodes;
    }
}
