/*
 * Copyright (c) 2025 Johannes Kepler University Linz
 * LIT Cyber-Physical Systems Lab
 *
 * Contributors: Mainak Majumder
 *
 */

package com.bytemeistercoding.opcua.parser;

public class NodeIdParser {

    public int getNamespaceIndex(String nodeIdString) {
        if (nodeIdString.startsWith("ns")) {
            return Integer.parseInt(nodeIdString.substring(nodeIdString.indexOf("=") + 1, nodeIdString.indexOf(';')));
        }
        return 0;
    }

    public char getIdentifierType(String nodeIdString) {
        if (nodeIdString.startsWith("ns")) {
            return nodeIdString.substring(nodeIdString.indexOf(';') + 1).charAt(0);
        } else
            return nodeIdString.charAt(0);
    }

    public String getIdentifierValue(String nodeIdString) {
        if (nodeIdString.startsWith("ns")) {
            String identifierString = nodeIdString.substring(nodeIdString.indexOf(';') + 1);
            return identifierString.substring(identifierString.indexOf("=") + 1);
        } else return nodeIdString.substring(nodeIdString.indexOf('=') + 1);
    }
}
