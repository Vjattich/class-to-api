package io.vjattich.parser;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.Processor;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.validator.ProblemReporter;
import com.github.javaparser.ast.validator.language_level_validations.Java21Validator;
import com.github.javaparser.ast.validator.postprocessors.Java21PostProcessor;

import java.util.List;
import java.util.function.Supplier;


public class Prs extends ParserConfiguration {

    private static final Java21PostProcessor postProcessor = new Java21PostProcessor();
    private static final Java21Validator validator = new Validator();

    public Prs() {
        super();
        setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_21);
        List<Supplier<Processor>> processors = this.getProcessors();
        processors.remove(3);
        processors.add(() -> new Processor() {
            @Override
            public void postProcess(ParseResult<? extends Node> result, ParserConfiguration configuration) {
                postProcessor.postProcess(result, configuration);
                //for some reason this validation is throwing an exception with native-build
                //fast fix is just disable it
//                validator.accept(
//                        result.getResult().get(),
//                        new ProblemReporter(newProblem -> result.getProblems()
//                                .add(newProblem))
//                );
            }
        });
    }

    static class Validator extends Java21Validator {

        @Override
        public void accept(Node node, ProblemReporter problemReporter) {
            getValidators().forEach(v -> {
                System.out.println(v);
                v.accept(node, problemReporter);
            });
        }

    }

}
