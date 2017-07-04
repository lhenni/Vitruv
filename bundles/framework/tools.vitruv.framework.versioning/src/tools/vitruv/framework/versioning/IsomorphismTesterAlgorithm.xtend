package tools.vitruv.framework.versioning

import edu.ucla.sspace.graph.graphstream.isomorphism.IsomorphismTester
import org.graphstream.graph.Graph
import tools.vitruv.framework.versioning.extensions.EChangeNode
import java.util.Map
import java.util.Set

interface IsomorphismTesterAlgorithm extends IsomorphismTester {
	def void init(Graph g1, Graph g2)

	def void compute()

	def boolean isIsomorphic()

	def Graph getCombinedGraph()

	def Map<EChangeNode, EChangeNode> getIsomorphism()

	def Set<EChangeNode> getUnmatchedOfGraph1()

	def Set<EChangeNode> getUnmatchedOfGraph2()
}