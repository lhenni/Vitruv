package edu.kit.ipd.sdq.vitruvius.tests.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FeatureInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import pcm_mockup.Interface;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Repository;
import uml_mockup.UPackage;

public class CorrespondenceTest extends VSUMTest {
    private static final String interfaceCRefName = "interfaces";

    @Override
    @Test
    public void testAll() {
        VSUMImpl vsum = testMetaRepositoryVSUMAndModelInstancesCreation();
        Repository repo = testLoadObject(vsum, PCM_INSTANCE_URI, Repository.class);
        UPackage pkg = testLoadObject(vsum, UML_INSTANCE_URI, UPackage.class);
        InternalCorrespondenceInstance corresp = testCorrespondenceInstanceCreation(vsum);
        // FIXME MK (deco): automatically decorate correspondence instance based on a new extension
        // point
        MappedCorrespondenceInstance mappedCorrespondenceInstance = new MappedCorrespondenceInstance(
                (CorrespondenceInstanceDecorator) corresp);
        vsum.decorateCorrespondenceInstance(VURI.getInstance(PCM_MM_URI), VURI.getInstance(UML_MM_URI), corresp,
                mappedCorrespondenceInstance);
        assertFalse(mappedCorrespondenceInstance.hasCorrespondences());
        EObjectCorrespondence repo2pkg = createRepo2PkgCorrespondence(repo, pkg, mappedCorrespondenceInstance);
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
        testAllClaimersAndGettersForEObjectCorrespondences(repo, pkg, mappedCorrespondenceInstance, repo2pkg);

        Pair<FeatureInstance, FeatureInstance> repoIfaceFIAndPkgIfaceFI = testAllClaimersAndGettersForFeatureCorrespondences(
                repo, pkg, mappedCorrespondenceInstance, repo2pkg);
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
        // 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ

        Interface repoInterface = testHasCorrespondences(repo, pkg, mappedCorrespondenceInstance);

        testSimpleRemove(pkg, mappedCorrespondenceInstance, repo2pkg, repoInterface);
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
        // 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ

        testRecursiveRemove(repo, pkg, mappedCorrespondenceInstance, repo2pkg, repoIfaceFIAndPkgIfaceFI);
        // now the correspondence instance should be empty

        // FIXME MK (tuid cache): reactivate testUpdate in CorrespondenceTest after TUID cache is
        // working
        // testUpdate(repo, pkg, mappedCorrespondenceInstance, repo2pkg);

        testCorrespondencePersistence(vsum, repo, pkg, mappedCorrespondenceInstance);
    }

    private void testCorrespondencePersistence(final VSUMImpl vsum, final Repository repo, final UPackage pkg,
            final CorrespondenceInstance corresp) {
        assertNotNull("Correspondence instance is null", corresp);
        // recreate the same correspondence as before
        EObjectCorrespondence repo2pkg = createRepo2PkgCorrespondence(repo, pkg, corresp);
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ

        // save instances in order to trigger saving for CorrespondenceInstance(s)
        VURI pcmVURI = VURI.getInstance(PCM_INSTANCE_URI);
        vsum.saveModelInstanceOriginal(pcmVURI);
        // create a new vsum from disk and load correspondence instance from disk
        VSUMImpl vsum2 = testMetaRepositoryVSUMAndModelInstancesCreation();
        Repository repo2 = testLoadObject(vsum2, PCM_INSTANCE_URI, Repository.class);
        UPackage pkg2 = testLoadObject(vsum2, UML_INSTANCE_URI, UPackage.class);
        InternalCorrespondenceInstance corresp2 = testCorrespondenceInstanceCreation(vsum2);
        MappedCorrespondenceInstance mappedCorrespondenceInstance2 = new MappedCorrespondenceInstance(
                (CorrespondenceInstanceDecorator) corresp2);
        vsum2.decorateCorrespondenceInstance(VURI.getInstance(PCM_MM_URI), VURI.getInstance(UML_MM_URI), corresp2,
                mappedCorrespondenceInstance2);
        // do not create correspondences they have to be restored from disk
        assertTrue(corresp2.hasCorrespondences());
        // obtain
        SameTypeCorrespondence repo2pkg2 = corresp2.claimUniqueSameTypeCorrespondence(repo2, pkg2);
        // test everything as if the correspondence would just have been created
        testAllClaimersAndGettersForEObjectCorrespondences(repo2, pkg2, corresp2, repo2pkg2);
    }

    private <T extends EObject> T testLoadObject(final VSUMImpl vsum, final String uri, final Class<T> clazz) {
        VURI vURI = VURI.getInstance(uri);
        ModelInstance instance = vsum.getAndLoadModelInstanceOriginal(vURI);
        T obj = instance.getUniqueRootEObjectIfCorrectlyTyped(clazz);
        assertNotNull(obj);
        return obj;
    }

    private InternalCorrespondenceInstance testCorrespondenceInstanceCreation(final VSUMImpl vsum) {
        VURI pcmMMVURI = VURI.getInstance(PCM_MM_URI);
        VURI umlMMVURI = VURI.getInstance(UML_MM_URI);
        InternalCorrespondenceInstance corresp = vsum.getCorrespondenceInstanceOriginal(pcmMMVURI, umlMMVURI);
        assertNotNull(corresp);
        return corresp;
    }

    private EObjectCorrespondence createRepo2PkgCorrespondence(final Repository repo, final UPackage pkg,
            final CorrespondenceInstance corresp) {
        // until this point the correspondence instance is empty
        EObjectCorrespondence repo2pkg = corresp.createAndAddEObjectCorrespondence(repo, pkg);
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
        return repo2pkg;
    }

    private void testAllClaimersAndGettersForEObjectCorrespondences(final Repository repo, final UPackage pkg,
            final CorrespondenceInstance corresp, final Correspondence repo2pkg) {
        // claimAllCorrespondence is already indirectly tested via claimUniqueCorrespondence
        Correspondence uniqueRepoCorrespondence = corresp.claimUniqueCorrespondence(repo);
        assertEquals(uniqueRepoCorrespondence, repo2pkg);
        Correspondence uniquePkgCorrespondence = corresp.claimUniqueCorrespondence(pkg);
        assertEquals(uniquePkgCorrespondence, repo2pkg);

        // claimCorrespondingEObject is already indirectly tested via
        // claimUniqueCorrespondingEObject
        EObject correspForRepo = corresp.claimUniqueCorrespondingEObject(repo);
        assertEquals(correspForRepo, pkg);
        EObject correspForPkg = corresp.claimUniqueCorrespondingEObject(pkg);
        assertEquals(correspForPkg, repo);

        List<Interface> interfaces = repo.getInterfaces();
        assertEquals(interfaces.size(), 1);
        Interface iface = interfaces.get(0);
        Correspondence correspForIface = corresp.claimUniqueOrNullCorrespondenceForEObject(iface);
        assertNull(correspForIface);
        // TODO test exception throwing of claimUniqueOrNullCorrespondenceForEObject

        Set<Correspondence> allRepoCorrespondences = corresp.getAllCorrespondences(repo);
        assertEquals(allRepoCorrespondences.size(), 1);
        assertTrue(allRepoCorrespondences.contains(repo2pkg));
        Set<Correspondence> allPkgCorrespondences = corresp.getAllCorrespondences(pkg);
        assertEquals(allPkgCorrespondences.size(), 1);
        assertTrue(allPkgCorrespondences.contains(repo2pkg));

        Set<Repository> allRepoTypeCorresp = corresp.getAllEObjectsInCorrespondencesWithType(Repository.class);
        assertTrue(allRepoTypeCorresp.contains(repo));
        Set<UPackage> allPkgTypeCorresp = corresp.getAllEObjectsInCorrespondencesWithType(UPackage.class);
        assertTrue(allPkgTypeCorresp.contains(pkg));

        Set<EObject> allCorrespForRepo = corresp.getAllCorrespondingEObjects(repo);
        assertEquals(allCorrespForRepo.size(), 1);
        assertTrue(allCorrespForRepo.contains(pkg));
        Set<EObject> allCorrespForPkg = corresp.getAllCorrespondingEObjects(pkg);
        assertEquals(allCorrespForPkg.size(), 1);
        assertTrue(allCorrespForPkg.contains(repo));
    }

    private Pair<FeatureInstance, FeatureInstance> testAllClaimersAndGettersForFeatureCorrespondences(
            final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp,
            final EObjectCorrespondence repo2pkg) {
        EStructuralFeature repoIfaceCFeat = repo.eClass().getEStructuralFeature(interfaceCRefName);
        assertTrue(repoIfaceCFeat instanceof EReference);
        EReference repoIfaceCRef = (EReference) repoIfaceCFeat;
        EStructuralFeature pkgIfaceCFeat = pkg.eClass().getEStructuralFeature(interfaceCRefName);
        assertTrue(pkgIfaceCFeat instanceof EReference);
        EReference pkgIfaceCRef = (EReference) pkgIfaceCFeat;
        // repoIfaceCRef2PkgIfaceCRef.setType(CorrespondenceType.BIJECTION);
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
        EContainmentReferenceCorrespondence repoIfaceCRef2PkgIfaceCRef = corresp
                .createAndAddEContainmentReferenceCorrespondence(repo, pkg, repoIfaceCRef, pkgIfaceCRef);
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
        // 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ

        FeatureInstance repoIfaceFI = FeatureInstance.getInstance(repo, repoIfaceCRef);
        FeatureInstance pkgIfaceFI = FeatureInstance.getInstance(pkg, pkgIfaceCRef);

        FeatureInstance correspForRepoIfaceFI = corresp.claimUniqueCorrespondingFeatureInstance(repoIfaceFI);
        assertEquals(correspForRepoIfaceFI, pkgIfaceFI);
        FeatureInstance correspForPkgIfaceFI = corresp.claimUniqueCorrespondingFeatureInstance(pkgIfaceFI);
        assertEquals(correspForPkgIfaceFI, repoIfaceFI);

        Set<FeatureInstance> allCorrespForRepoIfaceFI = corresp.getAllCorrespondingFeatureInstances(repoIfaceFI);
        assertEquals(allCorrespForRepoIfaceFI.size(), 1);
        assertTrue(allCorrespForRepoIfaceFI.contains(pkgIfaceFI));
        Set<FeatureInstance> allCorrespForPkgIfaceFI = corresp.getAllCorrespondingFeatureInstances(pkgIfaceFI);
        assertEquals(allCorrespForPkgIfaceFI.size(), 1);
        assertTrue(allCorrespForPkgIfaceFI.contains(repoIfaceFI));

        return new Pair<FeatureInstance, FeatureInstance>(repoIfaceFI, pkgIfaceFI);
    }

    private Interface testHasCorrespondences(final Repository repo, final UPackage pkg,
            final CorrespondenceInstance corresp) {
        assertTrue(corresp.hasCorrespondences(repo));
        assertTrue(corresp.hasCorrespondences(pkg));
        List<Interface> repoInterfaces = repo.getInterfaces();
        assertEquals(repoInterfaces.size(), 1);
        Interface repoInterface = repoInterfaces.get(0);
        assertTrue(!corresp.hasCorrespondences(repoInterface));
        return repoInterface;
    }

    private void testSimpleRemove(final UPackage pkg, final CorrespondenceInstance corresp,
            final EObjectCorrespondence repo2pkg, final Interface repoInterface) {
        List<uml_mockup.Interface> pkgInterfaces = pkg.getInterfaces();
        assertEquals(pkgInterfaces.size(), 1);
        uml_mockup.Interface pkgInterface = pkgInterfaces.get(0);
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
        // 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
        // add correspondence
        corresp.createAndAddEObjectCorrespondence(repoInterface, pkgInterface);
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
        // 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
        // 3. EOC: pcmIfac _tAgfwPxjEeOD3p0i_uuRbQ <=> umlIface _vWjxIPxjEeOD3p0i_uuRbQ

        // remove correspondence
        corresp.removeDirectAndChildrenCorrespondencesOnBothSides(repoInterface);
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
        // 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
        // check whether it is removed
        Set<Correspondence> repoInterfaceCorresp = corresp.getAllCorrespondences(repoInterface);
        assertTrue(repoInterfaceCorresp.isEmpty());
        Set<Correspondence> pkgInterfaceCorresp = corresp.getAllCorrespondences(pkgInterface);
        assertTrue(pkgInterfaceCorresp.isEmpty());
        Set<EObject> correspForRepoInterface = corresp.getAllCorrespondingEObjects(repoInterface);
        assertTrue(correspForRepoInterface.isEmpty());
        Set<EObject> correspForPkgInterface = corresp.getAllCorrespondingEObjects(pkgInterface);
        assertTrue(correspForPkgInterface.isEmpty());
        Set<Interface> correspForRepoInterfaceType = corresp.getAllEObjectsInCorrespondencesWithType(Interface.class);
        assertTrue(correspForRepoInterfaceType.isEmpty());
        Set<uml_mockup.Interface> correspForPkgInterfaceType = corresp
                .getAllEObjectsInCorrespondencesWithType(uml_mockup.Interface.class);
        assertTrue(correspForPkgInterfaceType.isEmpty());
    }

    private void testRecursiveRemove(final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp,
            final EObjectCorrespondence repo2pkg,
            final Pair<FeatureInstance, FeatureInstance> repoIfaceFIAndPkgIfaceFI) {
        // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
        // 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
        corresp.removeNeighborAndChildrenCorrespondencesOnBothSides(repo2pkg);
        // now the correspondence instance should be empty
        Set<Correspondence> repoCorresp = corresp.getAllCorrespondences(repo);
        assertTrue(repoCorresp.isEmpty());
        Set<Correspondence> pkgCorresp = corresp.getAllCorrespondences(pkg);
        assertTrue(pkgCorresp.isEmpty());
        Set<EObject> correspForRepo = corresp.getAllCorrespondingEObjects(repo);
        assertTrue(correspForRepo.isEmpty());
        Set<EObject> correspForPkg = corresp.getAllCorrespondingEObjects(pkg);
        assertTrue(correspForPkg.isEmpty());
        Set<Repository> correspForRepoType = corresp.getAllEObjectsInCorrespondencesWithType(Repository.class);
        assertTrue(correspForRepoType.isEmpty());
        Set<UPackage> correspForPkgType = corresp.getAllEObjectsInCorrespondencesWithType(UPackage.class);
        assertTrue(correspForPkgType.isEmpty());

        FeatureInstance repoIfaceFI = repoIfaceFIAndPkgIfaceFI.getFirst();
        FeatureInstance pkgIfaceFI = repoIfaceFIAndPkgIfaceFI.getSecond();
        Set<FeatureInstance> correspForRepoIfaceFI = corresp.getAllCorrespondingFeatureInstances(repoIfaceFI);
        assertTrue(correspForRepoIfaceFI.isEmpty());
        Set<FeatureInstance> correspForPkgIfaceFI = corresp.getAllCorrespondingFeatureInstances(pkgIfaceFI);
        assertTrue(correspForPkgIfaceFI.isEmpty());
        assertFalse(corresp.hasCorrespondences());
    }

    private void testUpdate(final Repository repo, final UPackage pkg, final CorrespondenceInstance corresp,
            final EObjectCorrespondence repo2pkg) {
        Repository newRepo = Pcm_mockupFactory.eINSTANCE.createRepository();
        // FIXME MK (cache): currently no correspondences are existing, therefore a new one has to
        // be created
        // before it can be updated!
        // create new corresp
        // store repo before updating the correspondence
        corresp.update(repo, newRepo);
        Set<Correspondence> repoCorresp = corresp.getAllCorrespondences(repo);
        assertTrue(repoCorresp.isEmpty());
        Correspondence uniqueNewRepoCorrespondence = corresp.claimUniqueCorrespondence(newRepo);
        assertEquals(uniqueNewRepoCorrespondence, repo2pkg);
        Correspondence uniquePkgCorrespondence = corresp.claimUniqueCorrespondence(pkg);
        assertEquals(uniquePkgCorrespondence, repo2pkg);
        EObject correspForNewRepo = corresp.claimUniqueCorrespondingEObject(newRepo);
        assertEquals(correspForNewRepo, pkg);
        EObject correspForPkg = corresp.claimUniqueCorrespondingEObject(pkg);
        assertEquals(correspForPkg, newRepo);
        // TODO is this really enough update testing?
    }
}
