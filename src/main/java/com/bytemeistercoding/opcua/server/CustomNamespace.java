/*
 * Copyright (c) 2025 Johannes Kepler University Linz
 * LIT Cyber-Physical Systems Lab
 *
 * Contributors: Mainak Majumder
 *
 */

package com.bytemeistercoding.opcua.server;

import com.bytemeistercoding.opcua.parser.MetaDataParser;
import com.bytemeistercoding.opcua.parser.NodeIdParser;
import com.bytemeistercoding.opcua.parser.UaNodeParser;
import com.bytemeistercoding.opcua.uanodeset.*;
import org.eclipse.milo.opcua.sdk.core.Reference;
import org.eclipse.milo.opcua.sdk.server.Lifecycle;
import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.api.DataItem;
import org.eclipse.milo.opcua.sdk.server.api.ManagedNamespaceWithLifecycle;
import org.eclipse.milo.opcua.sdk.server.api.MonitoredItem;
import org.eclipse.milo.opcua.sdk.server.nodes.*;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomNamespace extends ManagedNamespaceWithLifecycle {

    private static final Logger logger = LoggerFactory.getLogger(CustomNamespace.class);
    private final UANodeSet nodeSet;
    private final NodeIdParser nodeIdParser = new NodeIdParser();
    private final MetaDataParser metaDataParser = new MetaDataParser();
    private final UaNodeParser nodeParser = new UaNodeParser();

    CustomNamespace(OpcUaServer server, UANodeSet nodeSet) {
        super(server, nodeSet.getModels().getListOfModels().get(0).getModelUri());
        this.nodeSet = nodeSet;

        getLifecycleManager().addStartupTask(this::createAndAddNodes);
        getLifecycleManager().addLifecycle(new Lifecycle() {
            @Override
            public void startup() {
            }

            @Override
            public void shutdown() {
            }
        });
    }

    private void createAndAddNodes() {
        logger.info("Creating Namespaces object node");
        createNamespacesObject();
        logger.info("Checking pre-conditions");
        checkPreconditions();
        if (!nodeSet.getUaNodes().isEmpty()) {
            if (!nodeParser.getNodes(nodeSet, "UAObjectType").isEmpty()) {
                logger.info("Creating ObjectType nodes");
                createObjectTypeNodes();
            } else logger.info("No ObjectType node found");

            if (!nodeParser.getNodes(nodeSet, "UAVariableType").isEmpty()) {
                logger.info("Creating VariableType nodes");
                createVariableTypeNodes();
            } else logger.info("No VariableType node found");

            if (!nodeParser.getNodes(nodeSet, "UAObject").isEmpty()) {
                logger.info("Creating Object nodes");
                createObjectNodes();
            } else logger.info("No Object node found");

            if (!nodeParser.getNodes(nodeSet, "UAVariable").isEmpty()) {
                logger.info("Creating Variable nodes");
                createVariableNodes();
            } else logger.info("No Variable node found");

            if (!nodeParser.getNodes(nodeSet, "UAMethod").isEmpty()) {
                logger.info("Creating Method nodes");
                createMethodNodes();
            } else logger.info("No Method node found");
        } else throw new IllegalArgumentException("Nodeset is empty");

        logger.info("Adding references");
        addReferences();
    }

    private void checkPreconditions() {
        if (!metaDataParser.getRequiredModels(nodeSet).isEmpty()) {
            List<ModelTableEntry> requiredModels = metaDataParser.getRequiredModels(nodeSet);
            logger.info("Checking required information models");
            for (ModelTableEntry requiredModel : requiredModels) {
                if (!Arrays.asList(getNodeContext().getNamespaceTable().toArray()).contains(requiredModel.getModelUri()))
                    throw new IllegalArgumentException("Required model does not exists " + requiredModel.getModelUri());
            }
        } else throw new IllegalArgumentException("Required model list is empty");
    }

    private void createNamespacesObject() {
        NodeId nodeId = new NodeId(getNamespaceIndex(), 11715);
        QualifiedName browseName = newQualifiedName("Namespaces");
        LocalizedText displayName = LocalizedText.english("Namespaces");
        UaObjectNode namespaces = new UaObjectNode(getNodeContext(), nodeId, browseName, displayName);
        getNodeManager().addNode(namespaces);
        namespaces.addReference(new Reference(nodeId,
                Identifiers.HasComponent,
                Identifiers.Server.expanded(),
                false));
        namespaces.addReference(new Reference(nodeId,
                Identifiers.HasTypeDefinition,
                Identifiers.NamespacesType.expanded(),
                true));
        logger.info("Created Namespaces object node with NodeId {}", nodeId);
    }

    private void createObjectTypeNodes() {
        for (UANode node : nodeParser.getNodes(nodeSet, "UAObjectType")) {
            UAObjectType objectType = (UAObjectType) node;
            UaObjectTypeNode objectTypeNode = new UaObjectTypeNode.UaObjectTypeNodeBuilder(getNodeContext())
                    .setNodeId(generateNodeId(objectType.getNodeId()))
                    .setBrowseName(new QualifiedName(getNamespaceIndex(), objectType.getBrowseName().substring(
                            objectType.getBrowseName().indexOf(':') + 1)))
                    .setDisplayName(LocalizedText.english(objectType.getDisplayNames().get(0).getValue()))
                    .setIsAbstract(objectType.isAbstract())
                    .build();
            objectTypeNode.setDescription(getDescription(objectType));
            getNodeManager().addNode(objectTypeNode);
        }
    }

    private void createObjectNodes() {
        for (UANode node : nodeParser.getNodes(nodeSet, "UAObject")) {
            UAObject object = (UAObject) node;
            NodeId nodeId = generateNodeId(object.getNodeId());
            LocalizedText displayName = LocalizedText.english(object.getDisplayNames().get(0).getValue());
            QualifiedName browseName = newQualifiedName(object.getBrowseName().substring(
                    object.getBrowseName().indexOf(':') + 1));
            UaObjectNode objectNode = new UaObjectNode(getNodeContext(), nodeId, browseName, displayName);
            getNodeManager().addNode(objectNode);
        }
    }

    private void createVariableNodes() {
        for (UANode node : nodeParser.getNodes(nodeSet, "UAVariable")) {
            UAVariable variable = (UAVariable) node;
            NodeId nodeId = generateNodeId(variable.getNodeId());
            LocalizedText displayName = LocalizedText.english(variable.getDisplayNames().get(0).getValue());
            QualifiedName browseName = newQualifiedName(variable.getBrowseName().substring(
                    variable.getBrowseName().indexOf(':') + 1));
            UaVariableNode uaVariable = new UaVariableNode(getNodeContext(), nodeId, browseName, displayName);
            getNodeManager().addNode(uaVariable);
        }
    }

    private void createMethodNodes() {
        for (UANode node : nodeParser.getNodes(nodeSet, "UAMethod")) {
            UAMethod method = (UAMethod) node;
            NodeId nodeId = generateNodeId(method.getNodeId());
            LocalizedText displayName = LocalizedText.english(method.getDisplayNames().get(0).getValue());
            QualifiedName browseName = newQualifiedName(method.getBrowseName().substring(
                    method.getBrowseName().indexOf(':') + 1));
            LocalizedText description = getDescription(method);
            UaMethodNode methodNode = new UaMethodNode(getNodeContext(), nodeId, browseName, displayName, description,
                    UInteger.valueOf(method.getWriteMask()), UInteger.valueOf(method.getUserWriteMask()), method.isExecutable(), method.isUserExecutable());
            getNodeManager().addNode(methodNode);
        }
    }

    public void addReferences() {
        for (UANode node : nodeSet.getUaNodes()) {
            UaNode newNode = getNodeManager().get(generateNodeId(node.getNodeId()));
            for (UAReference reference : node.getReferences().getReference()) {
                String referenceTypenodeIdString = metaDataParser.getNodeIdFromAliasTable(nodeSet, reference.getReferenceType());
                NodeId referenceTypeNodeId = generateNodeId(referenceTypenodeIdString);
                if (newNode != null) {
                    newNode.addReference(new Reference(newNode.getNodeId(),
                            referenceTypeNodeId,
                            generateNodeId(reference.getValue()).expanded(),
                            reference.isIsForward()
                    ));
                }
            }
        }
    }

    private void createVariableTypeNodes() {
        for (UANode node : nodeParser.getNodes(nodeSet, "UAVariableType")) {
            UAVariableType variableType = (UAVariableType) node;
            NodeId nodeId = generateNodeId(variableType.getNodeId());
            QualifiedName browseName = newQualifiedName(variableType.getBrowseName().substring(
                    variableType.getBrowseName().indexOf(':') + 1));
            LocalizedText displayName = LocalizedText.english(variableType.getDisplayNames().get(0).getValue());
            Boolean abstractFlag = variableType.isAbstract();
            LocalizedText description = getDescription(variableType);
            UInteger writeMask = UInteger.valueOf(variableType.getWriteMask());
            UInteger userWriteMask = UInteger.valueOf(variableType.getUserWriteMask());
            NodeId dataType = solveDataType(nodeSet, variableType.getDataType());
            DataValue value = null;
            Integer valueRank = variableType.getValueRank();
            UInteger[] arrayDimension = parseStringToUIntegerArray(variableType.getArrayDimensions());
            UaVariableTypeNode variableTypeNode = new UaVariableTypeNode(getNodeContext(), nodeId, browseName, displayName, description,
                    writeMask, userWriteMask, value, dataType, valueRank, arrayDimension, abstractFlag);
            getNodeManager().addNode(variableTypeNode);
        }
    }

    /*private void addReferenceToParentTypeNode(List<UAReference> references, UaNode typeNode) {
        for (UAReference reference : references) {
            if (reference.getReferenceType().equals("HasSubtype")) {
                NodeId parentNodeId = generateNodeId(reference.getValue());
                typeNode.addReference(new Reference(
                        typeNode.getNodeId(),
                        Identifiers.HasSubtype,
                        parentNodeId.expanded(),
                        false
                ));
            }
        }
    }

    private void addReferenceToParentNode(UAInstance node, UaNode instanceNode) {
        if (node.getParentNodeId() != null) {
            NodeId parentNodeId = generateNodeId(node.getParentNodeId());
            for (UAReference reference : node.getReferences().getReference()) {
                if (reference.getValue().equals(parentNodeId.toParseableString())) {
                    String referenceTypenodeIdString = metaDataParser.getNodeIdFromAliasTable(nodeSet, reference.getReferenceType());
                    NodeId referenceTypeNodeId = generateNodeId(referenceTypenodeIdString);
                    instanceNode.addReference(new Reference(
                            instanceNode.getNodeId(),
                            referenceTypeNodeId,
                            parentNodeId.expanded(),
                            reference.isIsForward()
                    ));
                }
            }
        } else {
            for (UAReference reference : node.getReferences().getReference()) {
                if (reference.getReferenceType().equals("Organizes")) {
                    NodeId parentNodeId = generateNodeId(reference.getValue());
                    instanceNode.addReference(new Reference(
                            instanceNode.getNodeId(),
                            Identifiers.Organizes,
                            parentNodeId.expanded(),
                            reference.isIsForward()
                    ));
                    logger.info("Adding reference Organizes from {} to {}", parentNodeId.toParseableString(), instanceNode.getNodeId().toParseableString());
                } else if (reference.getReferenceType().equals("HasComponent")) {
                    NodeId parentNodeId = generateNodeId(reference.getValue());
                    instanceNode.addReference(new Reference(
                            instanceNode.getNodeId(),
                            Identifiers.HasComponent,
                            parentNodeId.expanded(),
                            reference.isIsForward()
                    ));
                    logger.info("Adding reference HasComponent from {} to {}", parentNodeId.toParseableString(), instanceNode.getNodeId().toParseableString());
                }
            }
        }
    }*/

    private LocalizedText getDescription(UANode node) {
        if (!node.getDescriptions().isEmpty())
            return LocalizedText.english(node.getDescriptions().get(0).getValue());
        else
            logger.info("No description found for node {}", generateNodeId(node.getNodeId()).toParseableString());
        return new LocalizedText(null);
    }

    public static UInteger[] parseStringToUIntegerArray(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new UInteger[0];
        }

        String[] parts = input.split(",");
        UInteger[] result = new UInteger[parts.length];

        for (int i = 0; i < parts.length; i++) {
            result[i] = UInteger.valueOf(Integer.parseInt(parts[i].trim()));
        }
        return result;
    }

    private NodeId generateNodeId(String nodeIdString) {
        UShort namespaceIndex = solveNamespaceIndex(nodeIdString);
        char identifierType = nodeIdParser.getIdentifierType(nodeIdString);
        String identifierValue = nodeIdParser.getIdentifierValue(nodeIdString);
        switch (identifierType) {
            case 'i':
                return new NodeId(namespaceIndex, Integer.parseInt(identifierValue));
            case 's':
                return new NodeId(namespaceIndex, identifierValue);
            case 'g':
                return new NodeId(namespaceIndex, UUID.fromString(identifierValue));
            case 'b':
                return new NodeId(namespaceIndex, new ByteString(identifierValue.getBytes()));
            default:
                throw new IllegalArgumentException("Unsupported NodeId format: " + nodeIdString);
        }
    }

    private UShort solveNamespaceIndex(String nodeIdString) {
        int namespaceIndex = nodeIdParser.getNamespaceIndex(nodeIdString);
        switch (namespaceIndex) {
            case 0:
                return UShort.valueOf(0);
            case 1:
                return getNamespaceIndex();
            default:
                return getServer().getNamespaceTable().getIndex(metaDataParser.getModelUriFromNamespaceTable(nodeSet, namespaceIndex - 1));
        }
    }

    private NodeId solveDataType(UANodeSet nodeSet, String dataType) {
        if (!dataType.equals("i=24")) {
            String nodeIdString = metaDataParser.getNodeIdFromAliasTable(nodeSet, dataType);
            return generateNodeId(nodeIdString);
        } else
            return generateNodeId(dataType);
    }

    private Optional<NodeId> getTypeDefinitionNode(UANode node) {
        return node.getReferences().getReference().stream()
                .filter(ref -> "HasTypeDefinition".equals(ref.getReferenceType()))
                .findFirst()
                .map(ref -> generateNodeId(ref.getValue()));
    }

    @Override
    public void onDataItemsCreated(List<DataItem> dataItems) {
    }

    @Override
    public void onDataItemsModified(List<DataItem> dataItems) {
    }

    @Override
    public void onDataItemsDeleted(List<DataItem> dataItems) {
    }

    @Override
    public void onMonitoringModeChanged(List<MonitoredItem> monitoredItems) {
    }

}
