package fitnesse.idea.fixtureclass

import com.intellij.openapi.module.{Module, ModuleUtilCore}
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi._
import com.intellij.psi.search.{GlobalSearchScope, PsiShortNamesCache}
import fitnesse.idea.decisiontable.DecisionTable
import fitnesse.idea.etc.Regracer
import fitnesse.idea.etc.SearchScope.searchScope
import fitnesse.idea.scenariotable.ScenarioNameIndex

import scala.collection.JavaConversions._

class FixtureClassReference(referer: FixtureClass) extends PsiPolyVariantReferenceBase[FixtureClass](referer, new TextRange(0, referer.getTextLength)) {

  val project = referer.getProject
  lazy val module = Option(ModuleUtilCore.findModuleForPsiElement(referer))

  private def table = referer match {
    case impl: FixtureClassImpl => impl.table
    case _ => new IllegalStateException("Expected a FixtureClassImpl referer")
  }

  // Return array of String, {@link PsiElement} and/or {@link LookupElement}
  override def getVariants = {
    val allClassNames: Array[String] = PsiShortNamesCache.getInstance(project).getAllClassNames
      .filter(name => FixtureClassResolver.isClassNameValid(name))
      .map(c => Regracer.regrace(FixtureClassResolver.strip(c)))
    table match {
      case _: DecisionTable =>
        val scenarioNames = ScenarioNameIndex.INSTANCE.getAllKeys(project).map(Regracer.regrace).toArray
        Array.concat(allClassNames, scenarioNames).asInstanceOf[Array[AnyRef]]
      case _ =>
        allClassNames.asInstanceOf[Array[AnyRef]]
    }
  }

  override def multiResolve(b: Boolean): Array[ResolveResult] = table match {
    case _: DecisionTable =>
      (getReferencedScenarios ++ getReferencedClasses).toArray
    case _ =>
      getReferencedClasses.toArray
  }

  private def fixtureClassName = referer.fixtureClassName

  protected def isQualifiedName: Boolean = fixtureClassName match {
    case Some(name) =>
      val dotIndex: Int = name.indexOf(".")
      dotIndex != -1 && dotIndex != name.length - 1
    case None => false
  }

  protected def shortName: Option[String] = fixtureClassName match {
    case Some(name) => name.split('.').toList.reverse match {
      case "" :: n :: _ => Some(n)
      case n :: _ => Some(n)
      case _ => Some(name)
    }
    case None => None
  }

  private def createReference(element: PsiElement): ResolveResult = new PsiElementResolveResult(element)

  protected def getReferencedClasses: Seq[ResolveResult] = fixtureClassName match {
    case Some(className) if isQualifiedName =>
      JavaPsiFacade.getInstance(project).findClasses(resolveClass(className), FixtureClassReference.moduleWithDependenciesScope(module)).map(createReference)
    case Some(className) =>
      PsiShortNamesCache.getInstance(project).getClassesByName(resolveClass(shortName.get), FixtureClassReference.moduleWithDependenciesScope(module)).map(createReference)
    case None => Seq()
  }

  protected def getReferencedScenarios: Seq[ResolveResult] = referer.fixtureClassName match {
    case Some(className) if isQualifiedName => Seq()
    case Some(className) =>
      ScenarioNameIndex.INSTANCE.get(resolveClass(className), project, FixtureClassReference.projectScope(project)).map(createReference).toSeq
    case None => Seq()
  }

  private def resolveClass(className: String) = {
    FixtureClassResolver.resolve(className)
  }

  override def handleElementRename(newElementName: String): PsiElement = referer.setName(newElementName)
}

// This is a work-around for testing:

object FixtureClassReference {
  /**
    * Override `scopeForTesting` for testing.
    */
  var scopeForTesting: Option[GlobalSearchScope] = None

  def moduleWithDependenciesScope(module: Option[Module]): GlobalSearchScope = scopeForTesting match {
    case Some(scope) => scope
    case None => module match {
      case Some(m) => searchScope(m.getProject)
      case _ => GlobalSearchScope.EMPTY_SCOPE
    }
  }

  def projectScope(project: Project): GlobalSearchScope = scopeForTesting match {
    case Some(scope) => scope
    case None => searchScope(project)
  }
}
