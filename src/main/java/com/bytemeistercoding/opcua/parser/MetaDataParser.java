/*
 * Copyright (c) 2025 Johannes Kepler University Linz
 * LIT Cyber-Physical Systems Lab
 *
 * Contributors: Mainak Majumder
 *
 */

package com.bytemeistercoding.opcua.parser;

import com.bytemeistercoding.opcua.uanodeset.ModelTableEntry;
import com.bytemeistercoding.opcua.uanodeset.NodeIdAlias;
import com.bytemeistercoding.opcua.uanodeset.UANodeSet;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

public class MetaDataParser {

    // get the namespace uri, publication date, version and required models from the nodeset
    public String getNamespaceUri(UANodeSet nodeset) {
        if (!nodeset.getModels().getListOfModels().isEmpty())
            return nodeset.getModels().getListOfModels().get(0).getModelUri();
        else throw new IllegalArgumentException("No model found in nodeset");
    }

    public String getVersion(UANodeSet nodeset) {
        if (!nodeset.getModels().getListOfModels().isEmpty())
            return nodeset.getModels().getListOfModels().get(0).getVersion();
        else throw new IllegalArgumentException("No model found in nodeset");
    }

    public LocalDateTime getPublicationDate(UANodeSet nodeset) {
        if (!nodeset.getModels().getListOfModels().isEmpty()) {
            ModelTableEntry nodesetModel = nodeset.getModels().getListOfModels().get(0);
            GregorianCalendar calendar = nodesetModel.getPublicationDate().toGregorianCalendar();
            return calendar.toZonedDateTime().toLocalDateTime();
        } else throw new IllegalArgumentException("No model found in nodeset");
    }

    public List<ModelTableEntry> getRequiredModels(UANodeSet nodeset) {
        if (!nodeset.getModels().getListOfModels().isEmpty())
            return nodeset.getModels().getListOfModels().get(0).getRequiredModel();
        else throw new IllegalArgumentException("No model found in nodeset");
    }

    public String getModelUriFromNamespaceTable(UANodeSet nodeset, int index) {
        return nodeset.getNamespaceUris().getUri().get(index);
    }

    public String getNodeIdFromAliasTable(UANodeSet nodeset, String alias) {
        Optional<NodeIdAlias> optionalAlias = nodeset.getAliases().getAliases().stream()
                .filter(a -> a.getAlias().equals(alias))
                .findFirst();

        if (optionalAlias.isPresent()) {
            return optionalAlias.get().getValue();
        } else {
            throw new RuntimeException("Alias not found: " + alias);
        }
    }
}
