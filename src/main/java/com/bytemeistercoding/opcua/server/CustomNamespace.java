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
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
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
            createObjectTypeNodes();
            createVariableTypeNodes();
            createObjectNodes();
            createVariableNodes();
            createMethodNodes();
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
        logger.info("Created Namespaces object node with NodeId {}", nodeId.toParseableString());
    }

    private boolean checkIfNodesExist(String nodeClass) {
        return !nodeParser.getNodes(nodeSet, nodeClass).isEmpty();
    }

    private static void logNodeNotFound(String nodeClass) {
        logger.info("No {} node found", nodeClass);
    }

    private void createObjectTypeNodes() {
        if (checkIfNodesExist("UAObjectType")) {
            logger.info("Creating {} nodes", "UAObjectType");
            for (UANode node : nodeParser.getNodes(nodeSet, "UAObjectType")) {
                UAObjectType objectType = (UAObjectType) node;
                UaObjectTypeNode objectTypeNode = new UaObjectTypeNode.UaObjectTypeNodeBuilder(getNodeContext())
                        .setNodeId(generateNodeId(objectType.getNodeId()))
                        .setBrowseName(generateBrowseName(objectType.getBrowseName()))
                        .setDisplayName(generateDisplayName(objectType.getDisplayNames()))
                        .setIsAbstract(objectType.isAbstract())
                        .build();
                addOptionalBaseAttributes(objectType, objectTypeNode);
                getServer().getObjectTypeManager().registerObjectType(
                        objectTypeNode.getNodeId(),
                        UaObjectNode.class,
                        UaObjectNode::new);

                getNodeManager().addNode(objectTypeNode);
            }
        } else logNodeNotFound("ObjectType");
    }

    private void createVariableTypeNodes() {
        for (UANode node : nodeParser.getNodes(nodeSet, "UAVariableType")) {
            UAVariableType variableType = (UAVariableType) node;
            NodeId nodeId = generateNodeId(variableType.getNodeId());
            QualifiedName browseName = generateBrowseName(variableType.getBrowseName());
            LocalizedText displayName = generateDisplayName(variableType.getDisplayNames());
            Boolean abstractFlag = variableType.isAbstract();
            LocalizedText description = generateDescription(variableType.getDescriptions());
            UInteger writeMask = UInteger.valueOf(variableType.getWriteMask());
            UInteger userWriteMask = UInteger.valueOf(variableType.getUserWriteMask());
            NodeId dataType = solveDataType(variableType.getDataType());
            DataValue value = new DataValue(new Variant(variableType.getValue()));
            Integer valueRank = variableType.getValueRank();
            UInteger[] arrayDimension = parseStringToUIntegerArray(variableType.getArrayDimensions());
            UaVariableTypeNode variableTypeNode = new UaVariableTypeNode(getNodeContext(), nodeId, browseName, displayName, description,
                    writeMask, userWriteMask, value, dataType, valueRank, arrayDimension, abstractFlag);
            getNodeManager().addNode(variableTypeNode);
        }
    }

    private void createObjectNodes() {
        if (checkIfNodesExist("UAObject")) {
            for (UANode node : nodeParser.getNodes(nodeSet, "UAObject")) {
                UAObject object = (UAObject) node;
                NodeId nodeId = generateNodeId(object.getNodeId());
                QualifiedName browseName = generateBrowseName(object.getBrowseName());
                LocalizedText displayName = generateDisplayName(object.getDisplayNames());
                String parentNodeId = findParentNodeId(object);
                //UaObjectNode objectNode = new UaObjectNode(getNodeContext(), nodeId, browseName, displayName);
                try {
                    UaObjectNode objectNode = (UaObjectNode) getNodeFactory().createNode(nodeId,
                            generateNodeId(parentNodeId));
                    objectNode.setBrowseName(browseName);
                    objectNode.setDisplayName(displayName);
                    objectNode.setEventNotifier(UByte.valueOf(object.getEventNotifier()));
                    addOptionalBaseAttributes(object, objectNode);
                    getNodeManager().addNode(objectNode);
                } catch (UaException e) {
                    logger.error("Error creating instance of ObjectType: {}", e.getMessage(), e);
                }
            }
        } else logNodeNotFound("Object");
    }
    
    private String findParentNodeId(UAInstance instanceNode) {
        return instanceNode.getReferences().getReference().stream()
                .filter(ref -> "HasTypeDefinition".equals(ref.getReferenceType()))
                .map(UAReference::getValue)
                .findFirst()
                .orElse("");
    }


    private void createVariableNodes() {
        if (checkIfNodesExist("UAVariable")) {
            for (UANode node : nodeParser.getNodes(nodeSet, "UAVariable")) {
                UAVariable variable = (UAVariable) node;
                NodeId nodeId = generateNodeId(variable.getNodeId());
                QualifiedName browseName = generateBrowseName(variable.getBrowseName());
                LocalizedText displayName = generateDisplayName(variable.getDisplayNames());
                UaVariableNode variableNode = new UaVariableNode(getNodeContext(), nodeId, browseName, displayName);
                variableNode.setValue(new DataValue(new Variant(variable.getValue())));
                variableNode.setDataType(solveDataType(variable.getDataType()));
                variableNode.setValueRank(variable.getValueRank());
                variableNode.setAccessLevel(UByte.valueOf(variable.getAccessLevel()));
                variableNode.setUserAccessLevel(UByte.valueOf(variable.getUserAccessLevel()));
                variableNode.setHistorizing(variable.isHistorizing());
                addOptionalBaseAttributes(variable, variableNode);
                variableNode.setArrayDimensions(parseStringToUIntegerArray(variable.getArrayDimensions()));
                getNodeManager().addNode(variableNode);
            }
        } else logNodeNotFound("Variable");
    }

    private void createMethodNodes() {
        if (checkIfNodesExist("UAMethod")) {
            for (UANode node : nodeParser.getNodes(nodeSet, "UAMethod")) {
                UAMethod method = (UAMethod) node;
                NodeId nodeId = generateNodeId(method.getNodeId());
                QualifiedName browseName = generateBrowseName(method.getBrowseName());
                LocalizedText displayName = generateDisplayName(method.getDisplayNames());
                LocalizedText description = generateDescription(method.getDescriptions());
                UaMethodNode methodNode = new UaMethodNode(getNodeContext(), nodeId, browseName, displayName, description,
                        UInteger.valueOf(method.getWriteMask()), UInteger.valueOf(method.getUserWriteMask()), method.isExecutable(),
                        method.isUserExecutable());
                getNodeManager().addNode(methodNode);
            }
        } else logNodeNotFound("Method");
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

    private void addOptionalBaseAttributes(UANode nodesetNode, UaNode serverNode) {
        serverNode.setDescription(generateDescription(nodesetNode.getDescriptions()));
        serverNode.setWriteMask(UInteger.valueOf(nodesetNode.getWriteMask()));
        serverNode.setUserWriteMask(UInteger.valueOf(nodesetNode.getUserWriteMask()));
    }

    private QualifiedName generateBrowseName(String browsenameString) {
        String[] parts = browsenameString.split(":");
        if (parts.length == 1) {
            return newQualifiedName(parts[0]);
        } else {
            return newQualifiedName(parts[1]);
        }
    }

    private LocalizedText generateDisplayName(List<UALocalizedText> displayNames) {
        String locale = displayNames.get(0).getLocale();
        String value = displayNames.get(0).getValue();
        if (locale == null || locale.isEmpty())
            return LocalizedText.english(value);
        else
            return new LocalizedText(locale, value);
    }

    private LocalizedText generateDescription(List<UALocalizedText> descriptions) {
        if (!descriptions.isEmpty()) {
            String locale = descriptions.get(0).getLocale();
            String value = descriptions.get(0).getValue();
            if (locale == null || locale.isEmpty())
                return LocalizedText.english(value);
            else return new LocalizedText(locale, value);
        }
        return new LocalizedText("");
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

    private NodeId solveDataType(String dataType) {
        if (!dataType.equals("i=24")) {
            String nodeIdString = metaDataParser.getNodeIdFromAliasTable(nodeSet, dataType);
            return generateNodeId(nodeIdString);
        } else
            return generateNodeId(dataType);
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
