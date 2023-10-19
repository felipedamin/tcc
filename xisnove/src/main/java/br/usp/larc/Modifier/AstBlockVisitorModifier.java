package br.usp.larc.Modifier;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

public class AstBlockVisitorModifier {
    public static Statement addBeforeThisStmt;
    private static Boolean isRunningBenchmark = true;
    private SourceRoot sourceRoot;
    private CompilationUnit compilationUnit;

    // Para rodar o projeto agora precisa passar 2 args em -Dexec.args="<arquivo para parsear começando em src> <true/false para codigo de produçao ou nao>"
    // mvn compile exec:java -Dexec.mainClass=teste.AstBlockVisitorModifier -Dexec.args="$parseFile true"
    public static void main(String[] args) throws IOException {
        AstBlockVisitorModifier astModifier = new AstBlockVisitorModifier();
        Boolean instrumentAllFiles = true;
        // if (args.length > 0) {
        if (instrumentAllFiles) {
            try {
                // Path rootDirectory = Paths.get(args[0]).toAbsolutePath();
                Path rootDirectory = Paths.get("./kafka");
                Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (file.toString().endsWith(".java")) {
                            astModifier.sourceRoot = new SourceRoot(file.getParent());
                            System.out.println("Parsing: " + file.toString());
                            astModifier.compilationUnit = astModifier.sourceRoot.parse("", file.getFileName().toString());
                            
                            // Perform your instrumentation on the Java file

                            Boolean productionCode = false;
                            if (args.length>0) {
                                productionCode = Boolean.parseBoolean(args[1]);
                            }
                            astModifier.visitAndModify(productionCode);
                            astModifier.sourceRoot.saveAll();
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        } else {
            astModifier.sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(AstBlockVisitorModifier.class).resolve("src/main/java/br/usp/larc/Benchmark"));
            
            if (isRunningBenchmark) {
                astModifier.compilationUnit = astModifier.sourceRoot.parse("PasswordHashing", "BCrypt.java");
            } else {
                astModifier.compilationUnit = astModifier.sourceRoot.parse("", "Methods2.java");
            }
            Boolean productionCode = false;
            if (args.length>0) {
                productionCode = Boolean.parseBoolean(args[1]);
            }
            astModifier.visitAndModify(productionCode);
        }


        if (!isRunningBenchmark) {
            // Overwrite original class
            astModifier.sourceRoot.saveAll();
        } else {
            // When executing the benchmark do not overwrite the original class
            String outputFilePath = "xisnove/src/main/java/br/usp/larc/Benchmark/benchmark.output";
            File outputFile = new File(outputFilePath);
    
            // Ensure the output directory exists
            outputFile.getParentFile().mkdirs();
    
            // Write the modified compilation unit to the output file
            Files.write(outputFile.toPath(), astModifier.compilationUnit.toString().getBytes(), StandardOpenOption.CREATE);
        }
    }

    public void visitAndModify(Boolean productionCode) {
        this.compilationUnit.addImport(new ImportDeclaration("org.apache.kafka.modifier.LogFile", false, false));
        // this.compilationUnit.addImport(new ImportDeclaration("br.usp.larc.Modifier.LogFile", false, false));

		this.compilationUnit.accept(new ModifierVisitor<String[]>() {
            String[] names = {"", ""};
            
            @Override
            public Visitable visit(ClassOrInterfaceDeclaration classDeclaration, String[] arg) {
                String className = classDeclaration.getNameAsString();
                this.names[0] = className;
                
                return super.visit(classDeclaration, this.names);
            }
            
            @Override
            public Visitable visit(MethodDeclaration md, String[] arg) {
                String methodName = md.getNameAsString();
                this.names[1] = methodName;

                return super.visit(md, this.names);
            }

            public Visitable visit(BlockStmt block, String[] arg) {
                Integer[] counter = {1};  // Used to identify when there are two identical conditions on the same method

                // Processa todos os if's:
                block.findAll(IfStmt.class).forEach(ifStmt -> {
                    Boolean isCascasdingIf = false;
                    if (ifStmt.getParentNode().get().getClass() == IfStmt.class) {
                        isCascasdingIf = true;
                    }
                    if (ifStmt.getParentNode().orElse(null).equals(block) || isCascasdingIf) { 
                        Map<String, ArrayList<String>> flaggedConditions = new HashMap<String, ArrayList<String>>();;
    
                        if (productionCode) {
                            try {
                                flaggedConditions = FlaggedConditions.getConditions();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
    
                        int ifStmtNumber = counter[0]++;
                        Expression condition = ifStmt.getCondition();
                        String conditionString = condition.toString();
                        String conditionId = this.names[0] + "#" + this.names[1] + "#ifStmt" + ifStmtNumber;
    
                        conditionString = conditionString.replaceAll("\\s", "");
                        Boolean shouldInstrumentThisCondition = flaggedConditions.containsKey(conditionId) && flaggedConditions.get(conditionId).contains(conditionString);
                        
                        if (!productionCode || (productionCode && shouldInstrumentThisCondition)) {
                            addLogBeforeIfStmt(ifStmt, this.names, ifStmtNumber);
                        }
                    }
                });
                // Chama-se o super.visit() para garantir que todos os nós serão visitados
                return super.visit(block, this.names);
            }
        }, null); 
    }

    // Used when there's an "else if"
    public static NodeList<Statement> findTopLevelIf(Node parent) {
        IfStmt parentAsIfStmt = ((IfStmt) parent);
        addBeforeThisStmt = parentAsIfStmt;
        NodeList<Statement> statements = new NodeList<>();
        
        if (parentAsIfStmt.getParentNode().isPresent()) {
            Node granpa = parentAsIfStmt.getParentNode().get();
            if (granpa.getClass().getName() == "com.github.javaparser.ast.stmt.IfStmt") {
                statements = findTopLevelIf(granpa);
            }
            else if((granpa.getClass().getName() != "com.github.javaparser.ast.stmt.SwitchEntry")){
                statements = ((BlockStmt) parentAsIfStmt.getParentNode().get()).getStatements();
            } 
        }
        return statements;
    }

    public static ExpressionStmt createLogExpression(NodeList<Statement> statements, String[] names, Expression condition) {
        String classAndMethodName = names[0] + "#" + names[1];
        String conditionType = "ifStmt";
        // List<NameExpr> conditionParams = new ArrayList<NameExpr>();
        
        // ??ifstmt.childNodes?
        // BinaryExpr conditionLeftToken = condition.asBinaryExpr();
        // BinaryExpr conditionRightToken = condition.asBinaryExpr();
        // while (conditionLeftToken.asBinaryExpr().getLeft().isBinaryExpr()) {
        //     conditionLeftToken = (BinaryExpr) conditionLeftToken.asBinaryExpr().getLeft();
        // }
        // while (conditionRightToken.asBinaryExpr().getRight().isBinaryExpr()) {
        //     conditionRightToken = (BinaryExpr) conditionRightToken.asBinaryExpr().getRight();
        // }
        
        // ArrayList<Object> paramValue;
        // TODO: remover duplicatas do conditionParams
        // if (condition.isBinaryExpr()) {
        //     conditionParams = condition.asBinaryExpr().findAll(NameExpr.class);
        // } else if (condition.isNameExpr()){
        //     conditionParams.add(condition.asNameExpr());
        // } else if (condition.isMethodCallExpr()) {
        //     System.out.println("aqui");
        //     System.out.println(condition);
        //     System.out.println(condition.asMethodCallExpr().findAll(NameExpr.class));
        //     // conditionParams.add(condition.asMethodCallExpr().findAll(NameExpr.class));
        // }
        // System.out.println(conditionParams);

        // OUTRA FORMA
        // add a statement to the method body
        // NameExpr clazz = new NameExpr("System");
        // FieldAccessExpr field = new FieldAccessExpr(clazz, "out");
        // MethodCallExpr call = new MethodCallExpr(field, "println");
        // call.addArgument(new StringLiteralExpr("Hello World!"));
        // block.addStatement(call);

        // OUTRA FORMA 2
        // new MethodCallExpr()
        //  .setScope(new NameExpr(Float.class.getName()))
        //  .setName("parseFloat")
        //  .addArgument(new StringLiteralExpr(value));

        MethodCallExpr testExpr = new MethodCallExpr(
            "LogFile.write",
            new StringLiteralExpr(classAndMethodName),
            new StringLiteralExpr(conditionType),
            new StringLiteralExpr(condition.toString().replace("\"", "\'"))
            );
        testExpr.addArgument(condition); // boolean finalValue
        
        // // condition.childNodes
        // ArrayList<String> tokenAsString = new ArrayList<String>();
        // ArrayList<Expression> tokens = new ArrayList<Expression>();
        // condition.getChildNodes().forEach(e -> {
        //     if (e.getClass() == BinaryExpr.class){
        //         Expression eAsExpression = ((Expression) e);
        //         tokens.add(eAsExpression);
        //         // tokenAsString.add(e.toString().replace("\"", "\'"));
        //     }
        //     else if (e.getClass() == UnaryExpr.class){
        //         Expression eAsExpression = ((Expression) e);
        //         tokens.add(eAsExpression);
        //         // tokenAsString.add(e.toString().replace("\"", "\'"));
        //     }
        //     else if (e.getClass() == NameExpr.class){
        //         Expression eAsExpression = ((Expression) e);
        //         tokens.add(eAsExpression);
        //     }
        //     tokenAsString.add(String.valueOf(e).replace("\"", "\'"));
        // });
        // if (tokenAsString.size() > 0) {
        //     System.out.println(tokenAsString.toString());

        //     testExpr.addArgument(new StringLiteralExpr(String.valueOf(tokenAsString).replace("\"", "\'")));
        // }

        // Expression[] tokensAsArray = new Expression[tokens.size()];
        // for (int i =0; i<tokens.size(); i++) {
        //     tokensAsArray[i] = tokens.get(i);
        //     testExpr.addArgument(new MethodCallExpr(
        //         "String.valueOf",
        //         tokensAsArray[i]
        //     ));
        // }

        ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
        return exprStmt;
    }

    public static void addLogBeforeIfStmt(IfStmt ifStmt, String[] names, int ifStmtNumber) {
        Expression condition = ifStmt.getCondition();
        ifStmt.getParentNode()
            // Se o código java é válido, sempre vai existir esse parent
            .map(parent -> {
                addBeforeThisStmt = ifStmt;
                NodeList<Statement> statements = new NodeList<>();
                if (parent.getClass() == IfStmt.class) {
                    statements = findTopLevelIf(parent);
                }
                if (parent.getClass().getName() == "com.github.javaparser.ast.stmt.BlockStmt") {
                    statements = ((BlockStmt) parent).getStatements();
                }

                return statements;
            })
            .ifPresent(statements -> {
                if (statements.size() > 0) {
                    ExpressionStmt exprStmt = createLogExpression(statements, names, condition);
                    
                    // The problem with "addBefore" is that it finds the first node that matches "addBeforeThisStmt"
                    // Therefore if we have two identical conditions (including the thenStatement)
                    // it will add twice before the first node, and will not add before the second node
                    // statements.addBefore(exprStmt, addBeforeThisStmt);

                    Integer correctIndexForIfStmtNumber = 0;
                    Integer counter = 0;
                    for (int i = 0; i<statements.size(); i++) {
                        Statement statement = statements.get(i);
                        if (statement.getClass().getName() == "com.github.javaparser.ast.stmt.IfStmt") {
                            counter++;
                            if (counter <= ifStmtNumber) {
                                correctIndexForIfStmtNumber = i;
                            }
                        }
                    }

                    statements.add(correctIndexForIfStmtNumber, exprStmt);
                }
            });
    }
}
