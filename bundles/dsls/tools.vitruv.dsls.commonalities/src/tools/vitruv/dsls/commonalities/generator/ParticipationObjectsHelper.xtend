package tools.vitruv.dsls.commonalities.generator

import java.util.Map
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.ParticipationMatcher.ParticipationObjects

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

/**
 * Helper methods related to interfacing with the {@link ParticipationObjects}
 * during runtime.
 */
class ParticipationObjectsHelper extends ReactionsGenerationHelper {

	package new() {
	}

	def getParticipationObject(ParticipationClass participationClass, XFeatureCall participationObjects,
		TypeProvider typeProvider) {
		participationObjects.memberFeatureCall => [
			feature = typeProvider.findDeclaredType(ParticipationObjects).findMethod("getObject", String)
			memberCallArguments += stringLiteral(participationClass.name)
			explicitOperationCall = true
		]
	}

	def Map<ParticipationClass, XVariableDeclaration> getParticipationObjectVars(Participation participation,
		XFeatureCall participationObjects, extension TypeProvider typeProvider) {
		return participation.classes.toInvertedMap [ participationClass |
			XbaseFactory.eINSTANCE.createXVariableDeclaration => [
				type = jvmTypeReferenceBuilder.typeRef(findTypeByName(participationClass.changeClass.javaClassName))
				name = participationClass.correspondingVariableName
				right = participationClass.getParticipationObject(participationObjects.copy, typeProvider)
			]
		]
	}
}