package tools.vitruv.dsls.mappings.generator.reactions

import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext

class DeletedReactionGenerator extends AbstractReactionTriggerGenerator {

	new(MetaclassReference element) {
		super(element.metaclass, ReactionTriggerType.DELETE)
	}

	override generateTrigger(MappingGeneratorContext context) {
		this.reactionName = '''«metaclass.parameterName»Deleted'''
		context.create.reaction(reactionName()).afterElement(metaclass).deleted
	}

	override toString() '''
	«metaclass.parameterName» deleted'''

	override equals(Object obj) {
		if (obj instanceof DeletedReactionGenerator) {
			return metaclass == obj.metaclass
		}
		false
	}

}