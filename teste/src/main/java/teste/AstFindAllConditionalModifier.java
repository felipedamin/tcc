package teste;

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
import com.github.javaparser.printer.DotPrinter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AstFindAllConditionalModifier {
    public static Statement addBeforeThisStmt;
    private static Boolean saveAstDotFile = false;
    public static void main(String[] args) throws IOException {
        SourceRoot sourceRoot;
        CompilationUnit compilationUnit;

        // Para rodar o projeto agora precisa passar 2 args em -Dexec.args="<arquivo para parsear começando em src> <true/false para codigo de produçao ou nao>"
        // mvn compile exec:java -Dexec.mainClass=teste.AstFindAllConditionalModifier -Dexec.args="$parseFile true"
        if (args.length > 0) {
            Path projectRoot = Paths.get(args[0]).toAbsolutePath();
            // System.out.println(projectRoot);
            String projectRootString = projectRoot.normalize().toString();
            File parseFile = new File(projectRootString);
            // System.out.println(parseFile.getParent());
            sourceRoot = new SourceRoot(Paths.get(parseFile.getParent()));
            compilationUnit = sourceRoot.parse("", parseFile.getName().toString());
        } else {
            // // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
            // // In this case the root directory is found by taking the root from the current Maven module,
            // sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(AstFindAllConditionalModifier.class).resolve("src/test/java/teste"));
            // // Our sample is in the root of this directory, so no package name.
            // compilationUnit = sourceRoot.parse("", "Methods.java");
            
            sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(AstFindAllConditionalModifier.class).resolve("src/main/java/benchmark"));
            compilationUnit = sourceRoot.parse("", "Benchmark10.java");
        }

        if (saveAstDotFile) {
            DotPrinter printer = new DotPrinter(true);
            try (FileWriter fileWriter = new FileWriter("astBefore.dot")) {
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print(printer.output(compilationUnit));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            
        compilationUnit.addImport(new ImportDeclaration("xisnove.logFile.LogFile", false, false));

		compilationUnit.accept(new ModifierVisitor<String[]>() {
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

                Integer[] counter = {1};  // Used to identify when there are two identical conditions on the same method
                // Processa todos os if's:
                md.findAll(IfStmt.class).forEach(ifStmt -> {
                    Map<String, ArrayList<String>> flaggedConditions = new HashMap<String, ArrayList<String>>();;
                    Boolean productionCode = false;
                    if (args.length>0) {
                        productionCode = Boolean.parseBoolean(args[1]);
                    }

                    if (productionCode) {
                        try {
                            flaggedConditions = FlaggedConditions.getConditions();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    Integer ifStmtNumber = counter[0]++;
                    Expression condition = ifStmt.getCondition();
                    String conditionString = condition.toString();
                    String conditionId = this.names[0] + "#" + this.names[1] + "#ifStmt" + ifStmtNumber;

                    conditionString = conditionString.replaceAll("\\s", "");
                    Boolean shouldInstrumentThisCondition = flaggedConditions.containsKey(conditionId) && flaggedConditions.get(conditionId).contains(conditionString);
                    
                    if (!productionCode || (productionCode && shouldInstrumentThisCondition)) {
                        if (!md.isThrown(IOException.class)) {
                            md.addThrownException(IOException.class);
                        }
                        findAndAddLog(ifStmt, this.names, ifStmtNumber);
                    }
                });
                // Chama-se o super.visit() para garantir que todos os nós serão visitados
                return super.visit(md, this.names);
            }
        }, null); 

        // Salva todas as alterações
        // sourceRoot.saveAll();

        // Save the modified class with a new name
        String outputFilePath = "teste/src/main/java/benchmark/Benchmark10.output";
        File outputFile = new File(outputFilePath);

        // Ensure the output directory exists
        outputFile.getParentFile().mkdirs();

        // Write the modified compilation unit to the output file
        Files.write(outputFile.toPath(), compilationUnit.toString().getBytes(), StandardOpenOption.CREATE);

        if (saveAstDotFile) {
            // Salva a nova arvore
            DotPrinter printer = new DotPrinter(true);
            try (FileWriter fileWriter = new FileWriter("astAfter.dot")) {
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print(printer.output(compilationUnit));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static NodeList<Statement> findTopLevelIf(Node parent) {
        IfStmt parentAsIfStmt = ((IfStmt) parent);
        addBeforeThisStmt = parentAsIfStmt;
        NodeList<Statement> statements = new NodeList<>();
        
        if (parentAsIfStmt.getParentNode().isPresent()) {
            Node granpa = parentAsIfStmt.getParentNode().get();
            if (granpa.getClass().getName() == "com.github.javaparser.ast.stmt.IfStmt") {
                statements = findTopLevelIf(granpa);
            }
            else {
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
        
        // condition.childNodes
        ArrayList<String> tokenAsString = new ArrayList<String>();
        ArrayList<Expression> tokens = new ArrayList<Expression>();
        condition.getChildNodes().forEach(e -> {
            if (e.getClass() == BinaryExpr.class){
                Expression eAsExpression = ((Expression) e);
                tokens.add(eAsExpression);
                tokenAsString.add(e.toString());
            }
            else if (e.getClass() == UnaryExpr.class){
                Expression eAsExpression = ((Expression) e);
                tokens.add(eAsExpression);
                tokenAsString.add(e.toString());
            }
            else if (e.getClass() == NameExpr.class){
                Expression eAsExpression = ((Expression) e);
                tokens.add(eAsExpression);
                tokenAsString.add(e.toString());
            }
        });
        
        if (tokenAsString.size() > 0) {
            testExpr.addArgument(new StringLiteralExpr(tokenAsString.toString()));
        }

        Expression[] tokensAsArray = new Expression[tokens.size()];
        for (int i =0; i<tokens.size(); i++) {
            tokensAsArray[i] = tokens.get(i);
            testExpr.addArgument(new MethodCallExpr(
                "String.valueOf",
                tokensAsArray[i]
            ));
        }

        ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
        return exprStmt;
    }

    public static void findAndAddLog(IfStmt ifStmt, String[] names, Integer ifStmtNumber) {
        Expression condition = ifStmt.getCondition();
    
        ifStmt.getParentNode()
            // Se o código java é válido, sempre vai existir esse parent
            .map(parent -> {
                addBeforeThisStmt = ifStmt;
                NodeList<Statement> statements = new NodeList<>();
                if (parent.getClass() == IfStmt.class) {
                    statements = findTopLevelIf(parent);
                }
                if (parent.getClass().getName() == "com.github.javaparser.ast.stmt.SwitchEntry") {
                    SwitchEntry parentAsSwitchEntry = ((SwitchEntry) parent);
                    statements = parentAsSwitchEntry.getStatements();
                }
                if (parent.getClass().getName() == "com.github.javaparser.ast.stmt.BlockStmt") {
                    statements = ((BlockStmt) parent).getStatements();
                }

                return statements;
            })
            .ifPresent(statements -> {
                if (statements.size() > 0) {
                    ExpressionStmt exprStmt = createLogExpression(statements, names, condition);
                    // statements.addBefore(exprStmt, addBeforeThisStmt); 

                    // code below might cause inconsistencies with funcaoIfElseNested in Methods.java
                    Integer correctIndexForIfStmtNumber = 0;
                    Integer counter = 0;
                    for (int i = 0; i<statements.size(); i++) {
                        Statement statement = statements.get(i);
                        if (statement.getClass().getName() == "com.github.javaparser.ast.stmt.IfStmt") {
                            counter++;
                            if (counter == ifStmtNumber) {
                                correctIndexForIfStmtNumber = i;
                            }
                        }
                    }
                    statements.add(correctIndexForIfStmtNumber, exprStmt);
                }
            });
    }
}
