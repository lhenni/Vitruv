package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmStringAnnotationValue
import tools.vitruv.dsls.commonalities.language.ParticipationConditionOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ContainmentOperator

@Utility
package class ParticipationConditionOperatorExtension {

	static val ANNOTATION = tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator
	static val ANNOTATION_NAME = ANNOTATION.name

	def private static getParticipationConditionOperatorAnnotation(JvmDeclaredType operatorType) {
		return operatorType.annotations
			.filter[annotation.qualifiedName == ANNOTATION_NAME]
			.head
	}

	def static getParticipationConditionOperatorName(JvmDeclaredType operatorType) {
		val annotation = operatorType.participationConditionOperatorAnnotation
		if (annotation === null) return null

		return annotation.explicitValues
			.filter[valueName == 'name']
			.filter(JvmStringAnnotationValue).head
			?.values?.head
	}

	def static getName(ParticipationConditionOperator operator) {
		return operator.jvmType.participationConditionOperatorName
	}

	// TODO support other structural operators
	def static isContainment(ParticipationConditionOperator operator) {
		return (operator.jvmType.qualifiedName == ContainmentOperator.name)
	}
}