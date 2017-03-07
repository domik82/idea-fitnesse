package fitnesse.idea.fixtureclass

import com.intellij.psi.stubs.Stub
import fitnesse.idea.parser.FitnesseElementType
import fitnesse.idea.psi.{MockIndexSink, PsiSuite}
import org.mockito.Mockito.when

class FixtureClassTest extends PsiSuite {

  test("can create stubs for fixture class") {
    val deserialized: Stub = createFileAndSerializeAndDeserialize("| script | table name |")

    assertResult("[FixtureClassStubImpl]") {
      deserialized.getChildrenStubs.toString
    }

    val fixtureClassStub = deserialized.getChildrenStubs.get(0).asInstanceOf[FixtureClassStub]
    val fixtureClassPsi = FitnesseElementType.FIXTURE_CLASS.createPsi(fixtureClassStub)

    assertResult("table name") { fixtureClassPsi.name }
  }

  test("index fixture class") {
    val fixtureClass = createTable("| table name |").fixtureClass.get
    val stub = FixtureClassElementType.INSTANCE.createStub(fixtureClass, null)
    val indexSink = new MockIndexSink()
    FixtureClassElementType.INSTANCE.indexStub(stub, indexSink)
    assertResult("TableName") {
      indexSink.value
    }
  }

  test("index fixture class with suffix") {
    FixtureClassResolver.setSuffix("Fixture")
    val fixtureClass = createTable("| table name |").fixtureClass.get
    val stub = FixtureClassElementType.INSTANCE.createStub(fixtureClass, null)
    val indexSink = new MockIndexSink()
    FixtureClassElementType.INSTANCE.indexStub(stub, indexSink)
    assertResult("TableNameFixture") {
      indexSink.value
    }
    FixtureClassResolver.clearSuffix()
  }
}
