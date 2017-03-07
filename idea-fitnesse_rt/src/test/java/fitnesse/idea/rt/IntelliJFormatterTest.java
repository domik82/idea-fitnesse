package fitnesse.idea.rt;

import fitnesse.testrunner.WikiTestPage;
import fitnesse.testsystems.ExceptionResult;
import fitnesse.testsystems.ExecutionResult;
import fitnesse.testsystems.TestSummary;
import fitnesse.testsystems.fit.CommandRunningFitClient;
import fitnesse.testsystems.fit.FitTestSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static fitnesse.idea.rt.IntelliJFormatter.NEWLINE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntelliJFormatterTest {

    private ByteArrayOutputStream out;
    private IntelliJFormatter formatter;

    @Before
    public void before() {
        out = new ByteArrayOutputStream();
        formatter = new IntelliJFormatter(new PrintStream(out));
    }

    @Test
    public void tellsNumberOfTestsToRun() {
        formatter.announceNumberTestsToRun(3);

        assertThat(out.toString(), is("##teamcity[testCount count='3']" + NEWLINE));
    }

    @Test
    public void testSystemStarted() throws IOException {
        formatter.testSystemStarted(new FitTestSystem("test system name", new CommandRunningFitClient(null)));

        assertThat(out.toString(), is("##teamcity[testSuiteStarted name='test system name' locationHint='' captureStandardOutput='true']" + NEWLINE));
    }

    @Test
    public void testSystemStopped() throws IOException {
        formatter.testSystemStopped(new FitTestSystem("test system name", new CommandRunningFitClient(null)), null);

        assertThat(out.toString(), is("##teamcity[testSuiteFinished name='test system name']" + NEWLINE));
    }

    @Test
    public void testStarted() throws IOException {
        formatter.testStarted(new WikiTestPage(null) {
            @Override
            public String getFullPath() {
                return "FullPath";
            }
        });

        assertThat(out.toString(), is("##teamcity[testStarted name='FullPath' locationHint='' captureStandardOutput='true']" + NEWLINE));
    }

    @Test
    public void testCompleteSuccessful() throws IOException {
        formatter.testComplete(new WikiTestPage(null) {
            @Override
            public String getFullPath() {
                return "FullPath";
            }
        }, new TestSummary(1, 0, 0, 0));

        assertThat(out.toString(), is("##teamcity[testFinished name='FullPath']" + NEWLINE));
    }

    @Test
    public void testCompleteWithWrong() throws IOException {
        formatter.testComplete(new WikiTestPage(null) {
            @Override
            public String getFullPath() {
                return "FullPath";
            }
        }, new TestSummary(1, 1, 0, 0));

        assertThat(out.toString(), is("##teamcity[testFailed name='FullPath' message='Test failed: R:1 W:1 I:0 E:0']" + NEWLINE));
    }

    @Test
    public void testCompleteWithExceptions() throws IOException {
        formatter.testComplete(new WikiTestPage(null) {
            @Override
            public String getFullPath() {
                return "FullPath";
            }
        }, new TestSummary(1, 0, 0, 1));

        assertThat(out.toString(), is("##teamcity[testFailed name='FullPath' message='Test failed: R:1 W:0 I:0 E:1']" + NEWLINE));
    }

    @Test
    public void testCompleteWithOccurredExceptions() throws IOException {
        formatter.testExceptionOccurred(null, new ExceptionResult() {
            @Override
            public ExecutionResult getExecutionResult() {
                return null;
            }

            @Override
            public String getMessage() {
                return "*message*";
            }
        });

        formatter.testComplete(new WikiTestPage(null) {
            @Override
            public String getFullPath() {
                return "FullPath";
            }
        }, new TestSummary(1, 0, 0, 1));

        assertThat(out.toString(), is("##teamcity[testFailed name='FullPath' message='*message*' error='true']" + NEWLINE));
    }

    @Test
    public void testOutputChunkWithNewline() throws IOException {
        formatter.testOutputChunk("<br/><br/>Simple example (no namespacing)<br/><br/>");

        assertThat(out.toString(), is(NEWLINE + NEWLINE + "Simple example (no namespacing)" + NEWLINE + NEWLINE));
    }

    @Test
    public void testOutputChunkWithDivTags() throws IOException {
        formatter.testOutputChunk("<div>Verify the text is shown as text.</div>");

        assertThat(out.toString(), is("Verify the text is shown as text."));
    }

    @Test
    public void testOutputChunkWithInlineTags() throws IOException {
        formatter.testOutputChunk("Verify <i>the text</i> is shown as text.");

        assertThat(out.toString(), is("Verify the text is shown as text."));
    }

    @Test
    public void testOutputChunkWithTable() throws IOException {
        formatter.testOutputChunk("<table>" + NEWLINE +
                "\t<tr class=\"slimRowTitle\">" + NEWLINE +
                "\t\t<td>import</td>" + NEWLINE +
                "\t</tr>" + NEWLINE +
                "\t<tr class=\"slimRowColor0\">" + NEWLINE +
                "\t\t<td><span class=\"pass\">fixtures</span></td>" + NEWLINE +
                "\t</tr>" + NEWLINE +
                "</table>");

        System.out.println(out.toString());
        assertThat(out.toString(), is(
                "|import  |" + NEWLINE +
                        "|\u001B[30;42mfixtures\u001B[0m|" + NEWLINE));
    }

    @Test
    public void testOutputChunkWithList() throws IOException {
        formatter.testOutputChunk("<br/><ul>" + NEWLINE +
                "\t<li>list item 1</li>" + NEWLINE +
                "\t<li>list item 2</li>" + NEWLINE +
                "</ul>" + NEWLINE);

        System.out.println(out.toString());
        assertThat(out.toString(), is(NEWLINE + NEWLINE + "\tlist item 1" + NEWLINE + "\tlist item 2" + NEWLINE + NEWLINE));
    }

    @Test
    public void resultStates() throws IOException {
        formatter.testOutputChunk("<table>" + NEWLINE +
                "\t<tr>" + NEWLINE +
                "\t\t<td><span class=\"pass\">pass me</span></td>" + NEWLINE +
                "\t\t<td><span class=\"fail\">fail me</span></td>" + NEWLINE +
                "\t\t<td><span class=\"error\">error me</span></td>" + NEWLINE +
                "\t\t<td><span class=\"ignore\">ignore me</span></td>" + NEWLINE +
                "\t</tr>" + NEWLINE +
                "</table>");

        System.out.println(out.toString());
        assertThat(out.toString().replace('\u001B', '^'), is("|^[30;42mpass me^[0m|^[30;41mfail me^[0m|^[30;43merror me^[0m|^[30;46mignore me^[0m|" + NEWLINE));
    }

    @Test
    public void layoutTable() throws IOException {
        formatter.testOutputChunk("<table>" + NEWLINE +
                "\t<tr>" + NEWLINE +
                "\t\t<td colspan=\"2\">Foo</td>" + NEWLINE +
                "\t</tr>" + NEWLINE +
                "\t<tr>" + NEWLINE +
                "\t\t<td>one</td>" + NEWLINE +
                "\t\t<td>longer cell</td>" + NEWLINE +
                "\t</tr>" + NEWLINE +
                "\t<tr>" + NEWLINE +
                "\t\t<td>three</td>" + NEWLINE +
                "\t\t<td>four</td>" + NEWLINE +
                "\t</tr>" + NEWLINE +
                "</table>");

        System.out.println(out.toString());
        assertThat(out.toString().replace('\u001B', '^'), is(
                "|Foo              |" + NEWLINE +
                        "|one  |longer cell|" + NEWLINE +
                        "|three|four       |" + NEWLINE));
    }

    @Test
    public void layoutTableWithLongFixtureName() throws IOException {
        formatter.testOutputChunk("<table>" + NEWLINE +
                "\t<tr>" + NEWLINE +
                "\t\t<td colspan=\"2\">Foo bar baz</td>" + NEWLINE +
                "\t</tr>" + NEWLINE +
                "\t<tr>" + NEWLINE +
                "\t\t<td>one</td>" + NEWLINE +
                "\t\t<td>longer cell</td>" + NEWLINE +
                "\t</tr>" + NEWLINE +
                "\t<tr>" + NEWLINE +
                "\t\t<td>three</td>" + NEWLINE +
                "\t\t<td>four</td>" + NEWLINE +
                "\t</tr>" + NEWLINE +
                "</table>");

        System.out.println(out.toString());
        assertThat(out.toString().replace('\u001B', '^'), is(
                "|Foo bar baz      |" + NEWLINE +
                        "|one  |longer cell|" + NEWLINE +
                        "|three|four       |" + NEWLINE));
    }

}
