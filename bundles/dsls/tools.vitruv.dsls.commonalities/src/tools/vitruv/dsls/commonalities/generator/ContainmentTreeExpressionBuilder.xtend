package tools.vitruv.dsls.commonalities.generator

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.ParticipationMatcher.ContainmentTree

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

package class ContainmentTreeExpressionBuilder {

	val extension TypeProvider typeProvider
	val JvmDeclaredType containmentTreeType
	val JvmOperation addNodeMethod
	val JvmOperation addEdgeMethod

	var XVariableDeclaration containmentTreeVar

	new(TypeProvider typeProvider) {
		this.typeProvider = typeProvider
		this.containmentTreeType = typeProvider.findDeclaredType(ContainmentTree).imported
		this.addNodeMethod = containmentTreeType.findMethod("addNode", String, EClass)
		this.addEdgeMethod = containmentTreeType.findMethod("addEdge", String, String, EReference)
	}

	def XVariableDeclaration newContainmentTree(String variableName) {
		checkState(containmentTreeVar === null, "ContainmentTreeExpressionBuilder can only be used once!")
		containmentTreeVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = variableName
			writeable = false
			type = jvmTypeReferenceBuilder.typeRef(containmentTreeType)
			right = XbaseFactory.eINSTANCE.createXConstructorCall => [
				it.constructor = containmentTreeType.findNoArgsConstructor
			]
		]
		return containmentTreeVar
	}

	private def checkHasContainmentTree() {
		checkState(containmentTreeVar !== null, "The ContainmentTree has not yet been created!")
	}

	def XExpression setRootIntermediateType(EClass rootIntermediateType) {
		checkHasContainmentTree()
		val setRootIntermediateTypeMethod = containmentTreeType.findMethod("setRootIntermediateType", EClass)
		return containmentTreeVar.featureCall.memberFeatureCall(setRootIntermediateTypeMethod) => [
			explicitOperationCall = true
			memberCallArguments += getEClass(typeProvider, rootIntermediateType)
		]
	}

	def XExpression addNode(String nodeName, EClass nodeType) {
		checkHasContainmentTree()
		return containmentTreeVar.featureCall.memberFeatureCall(addNodeMethod) => [
			memberCallArguments += expressions(
				stringLiteral(nodeName),
				getEClass(typeProvider, nodeType)
			)
		]
	}

	def XExpression addEdge(String containedNodeName, String containerNodeName, EReference containmentEReference) {
		checkHasContainmentTree()
		return containmentTreeVar.featureCall.memberFeatureCall(addEdgeMethod) => [
			memberCallArguments += expressions(
				stringLiteral(containedNodeName),
				stringLiteral(containerNodeName),
				getEReference(typeProvider, containmentEReference)
			)
		]
	}
}