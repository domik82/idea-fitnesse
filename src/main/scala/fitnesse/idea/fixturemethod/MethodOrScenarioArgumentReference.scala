package fitnesse.idea.fixturemethod

import com.intellij.psi._
import fitnesse.idea.etc.Regracer
import fitnesse.idea.scenariotable.ScenarioName

class MethodOrScenarioArgumentReference(referer: FixtureMethod) extends MethodReference(referer) {

  override def getVariants = referer.fixtureClass match {
    case Some(fixtureClass) =>
      // FixtureClassReference knows when scenario's can be used along with (Java) fixtures.
      Option(fixtureClass.getReference) match {
        case Some(ref) => ref.resolve match {
          case c: PsiClass => c.getAllMethods.map(m => Regracer.regrace(m.getName))
          case s: ScenarioName => s.arguments.toArray
          case unknown =>
            println(s"FitNesse fixture class resolved to unknown type ${unknown}")
            Array.emptyObjectArray
        }
        case None => Array.emptyObjectArray
      }
    case None => Array.emptyObjectArray
  }
}
