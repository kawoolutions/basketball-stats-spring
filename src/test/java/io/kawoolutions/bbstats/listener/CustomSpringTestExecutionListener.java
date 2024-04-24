package io.kawoolutions.bbstats.listener;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class CustomSpringTestExecutionListener implements TestExecutionListener {

//    public String shorterStacktrace(Throwable ex) {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ex.printStackTrace(new PrintStream(os));
//        return os.toString(StandardCharsets.UTF_8).lines().limit(6).collect(Collectors.joining("\n")) + "\n\t...";
//    }
//
//    @Override
//    public void beforeTestExecution(TestContext testContext) {
//        log(">" + testContext.getTestMethod().getName());
//    }
//
//    @Override
//    public void afterTestExecution(TestContext testContext) {
//        Optional<Throwable> throwable = Optional.ofNullable(testContext.getTestException());
//        throwable.ifPresent(t -> log(shorterStacktrace(t)));
//        log("<" + testContext.getTestMethod() + "\n");
//    }
//
//    void log(String message) {
//        System.out.println("####################################################### " + message);
//    }
}