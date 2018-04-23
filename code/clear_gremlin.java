// get id
String edgeId = ((RelationIdentifier) edge.getId()).toString();

// get flow type
FlowType flowType = ((EdgeLabel.getLabel(edge.getLabel()).getFlowType()));

// find & map start node
Node startNode = nodeMapper.map(edge.getVertex(Direction.OUT));
// find & map end node
Node endNode = nodeMapper.map(edge.getVertex(Direction.IN));

// get predefined and custom properties
Double validFrom = null;
Double validTo = null;
String callId = null;
Map<String, String> properties = new HashMap<String, String>();
for (String key : edge.getPropertyKeys()) {
   if (TRAN_START.p().equals(key)) {
       validFrom = edge.getProperty(key);
   } else if (TRAN_END.p().equals(key)) {
       validTo = edge.getProperty(key);
   } else if (CALL_ID.p().equals(key)) {
       callId = edge.getProperty(key);
   } else {
       properties.put(key, (String) edge.getProperty(key));
   }
}

RevisionInterval revisionInterval = new RevisionInterval(validFrom, validTo);

// create flow
Flow flow = new Flow(edgeId, startNode, endNode, revisionInterval, flowType, callId, properties);
