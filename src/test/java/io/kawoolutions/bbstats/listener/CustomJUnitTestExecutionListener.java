package io.kawoolutions.bbstats.listener;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

/*
    https://marian-caikovski.medium.com/print-names-of-test-methods-executed-by-junit-5-f337b4ba1264
 */
public class CustomJUnitTestExecutionListener implements TestExecutionListener {

    public String shorterStacktrace(Throwable throwable) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        throwable.printStackTrace(new PrintStream(os));
        return os.toString(StandardCharsets.UTF_8).lines().limit(6).collect(Collectors.joining("\n")) + "\n\t...";
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        TestSource testSource = testIdentifier.getSource().orElse(null);
        if (testSource instanceof MethodSource methodSource) {
            log("> ENTERING", methodSource, false);
        }
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        TestSource testSource = testIdentifier.getSource().orElse(null);
        if (testSource instanceof MethodSource methodSource) {
            Optional<Throwable> throwable = testExecutionResult.getThrowable();
            throwable.ifPresent(t -> log(shorterStacktrace(t)));
            log("< LEAVING", methodSource, true);
        }
    }

    private void log(String prefix, MethodSource methodSource, boolean useNewline) {
//        String methodName = methodSource.getClassName() + "." + methodSource.getMethodName();
        String methodName = methodSource.getMethodName();
        log(prefix + " " + methodName + "()" + (useNewline ? "\n": ""));
    }

    private void log(String message) {
        System.out.println("*** " + message);
    }
}