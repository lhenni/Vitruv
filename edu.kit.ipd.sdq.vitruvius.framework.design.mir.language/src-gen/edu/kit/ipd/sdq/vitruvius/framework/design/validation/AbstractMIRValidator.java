/*
 * generated by Xtext
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.validation;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;

public class AbstractMIRValidator extends org.eclipse.xtext.xbase.validation.XbaseValidator {

	@Override
	protected List<EPackage> getEPackages() {
	    List<EPackage> result = new ArrayList<EPackage>(super.getEPackages());
	    result.add(edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage.eINSTANCE);
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/xtext/xbase/Xbase"));
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/xtext/common/JavaVMTypes"));
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/xtext/xbase/Xtype"));
		return result;
	}
}
