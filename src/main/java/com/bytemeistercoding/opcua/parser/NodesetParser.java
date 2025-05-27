/*
 * Copyright (c) 2025 Johannes Kepler University Linz
 * LIT Cyber-Physical Systems Lab
 *
 * Contributors: Mainak Majumder
 *
 */

package com.bytemeistercoding.opcua.parser;

import com.bytemeistercoding.opcua.uanodeset.UANodeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NodesetParser {
    private static final Logger logger = LoggerFactory.getLogger(NodesetParser.class);

    private File validateNodesetFile(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        if (!file.isFile()) {
            throw new IOException("Not a valid file: " + filePath);
        }

        if (!file.canRead()) {
            throw new IOException("Cannot read file: " + filePath);
        }
        return file;
    }

    public UANodeSet parseNodesetFile(String filePath) throws JAXBException, IOException {
        File nodeSetFile = validateNodesetFile(filePath);
        try {
            JAXBContext context = JAXBContext.newInstance(UANodeSet.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            logger.info("Parsing nodeset file from: {}", filePath);
            return (UANodeSet) unmarshaller.unmarshal(nodeSetFile);
        } catch (JAXBException e) {
            throw new JAXBException("Failed to parse nodeset file: " + filePath, e);
        }
    }

    public static void main(String[] args) throws JAXBException, IOException {
        NodesetParser parser = new NodesetParser();
        parser.parseNodesetFile("nodeset-files/test-model.xml");
    }

}

