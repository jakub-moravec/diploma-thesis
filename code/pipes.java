GremlinPipeline<Vertex, Vertex> pipeline = new GremlinPipeline(resource);

for (NodeQualifiedName.QualifiedNamePart qnPart : qn.getParts()) {
   pipeline = pipeline
      .inE(HAS_PARENT.l())
         .has(TRAN_START.p(), lte, revisionInterval.getRevisionStart())
         .has(TRAN_END.p(), gte, revisionInterval.getRevisionEnd())
         .cast(Edge.class)
       .outV()
          .has(NODE_NAME.p(), qnPart.getNodeName())
          .has(NODE_TYPE.p(), qnPart.getNodeType().getId())
          .cast(Vertex.class);
}

Node node = nodeMapper.map((Vertex) pipeline.next());
