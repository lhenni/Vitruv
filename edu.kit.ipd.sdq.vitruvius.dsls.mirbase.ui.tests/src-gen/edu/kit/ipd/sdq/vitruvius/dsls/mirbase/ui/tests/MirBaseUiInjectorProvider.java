/*
 * generated by Xtext 2.10.0-SNAPSHOT
 */
package edu.kit.ipd.sdq.vitruvius.dsls.mirbase.ui.tests;

import com.google.inject.Injector;
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.ui.internal.MirbaseActivator;
import org.eclipse.xtext.junit4.IInjectorProvider;

public class MirBaseUiInjectorProvider implements IInjectorProvider {

	@Override
	public Injector getInjector() {
		return MirbaseActivator.getInstance().getInjector("edu.kit.ipd.sdq.vitruvius.dsls.mirbase.MirBase");
	}

}
