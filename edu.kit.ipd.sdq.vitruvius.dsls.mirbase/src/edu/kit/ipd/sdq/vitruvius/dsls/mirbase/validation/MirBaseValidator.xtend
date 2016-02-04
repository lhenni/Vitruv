/*
 * generated by Xtext 2.10.0-SNAPSHOT
 */
package edu.kit.ipd.sdq.vitruvius.dsls.mirbase.validation

import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.validation.AbstractMirBaseValidator
import org.eclipse.xtext.validation.Check
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MetamodelImport
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MirBasePackage

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class MirBaseValidator extends AbstractMirBaseValidator {
	public static val METAMODEL_IMPORT_DEPENDENCY_MISSING = "metamodelImportDependencyMissing"
	
	@Check
	def checkMetamodelImportDependencyMissing(MetamodelImport metamodelImport) {
		warning('''Dependency to plug-in missing.''', metamodelImport, MirBasePackage.Literals.METAMODEL_IMPORT__PACKAGE, METAMODEL_IMPORT_DEPENDENCY_MISSING)
	}
}
