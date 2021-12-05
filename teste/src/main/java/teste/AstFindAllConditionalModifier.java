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
import java.util.List;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AstFindAllConditionalModifier {
    public static Statement addBeforeThisStmt;
    public static void main(String[] args) throws IOException {
        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        // SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(AstFindAllConditionalModifier.class).resolve("src/test/java/teste"));
        // Our sample is in the root of this directory, so no package name.
        // CompilationUnit cu = sourceRoot.parse("", "Methods.java");

        // Para rodar o projeto agora precisa passar 2 args em -Dexec.args="<arquivo para parsear começando em src> <true/false para codigo de produçao ou nao>"
        // mvn compile exec:java -Dexec.mainClass=teste.AstFindAllConditionalModifier -Dexec.args="$parseFile true"
        Path projectRoot = Paths.get(args[0]).toAbsolutePath();
        // System.out.println(projectRoot);
        String projectRootString = projectRoot.normalize().toString();
        File parseFile = new File(projectRootString);
        // System.out.println(parseFile.getParent());
        SourceRoot sourceRoot = new SourceRoot(Paths.get(parseFile.getParent()));
        CompilationUnit cu = sourceRoot.parse("", parseFile.getName().toString());

        // TODO: o import eh diferente dependendo do package
        cu.addImport(new ImportDeclaration("teste.logFile.LogFile", false, false));

        cu.accept(new ModifierVisitor<String[]>() {
            String[] names = {"", ""};
            Boolean productionCode = Boolean.parseBoolean(args[1]);
            
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

                // Process all if's:
                md.findAll(IfStmt.class).forEach(ifStmt -> {
                    Map<String, ArrayList<String>> flaggedConditions = new HashMap<String, ArrayList<String>>();;
                    if (productionCode) {
                        try {
                            flaggedConditions = FlaggedConditions.getConditions();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    Expression condition = ifStmt.getCondition();
                    String conditionString = condition.toString();
                    String conditionId = this.names[0] + "#" + this.names[1] + "#ifStmt";
                    conditionString = conditionString.replaceAll("\\s", "");
                    Boolean shouldInstrumentThisCondition = flaggedConditions.containsKey(conditionId) && flaggedConditions.get(conditionId).contains(conditionString);
                    
                    if (!productionCode || (productionCode && shouldInstrumentThisCondition)) {
                        if (!md.isThrown(IOException.class)) {
                            md.addThrownException(IOException.class);
                        }
                        findAndAddLog(ifStmt, this.names, productionCode);
                    }
                });
                // "we make a call to super to ensure child nodes of the current node are also visited"
                return super.visit(md, this.names);
            }
        }, null); 
        // This saves back the file we read with the changes we made. Easy!
        sourceRoot.saveAll();
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

    public static void findAndAddLog(IfStmt ifStmt, String[] names, Boolean productionCode) {
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
                    String classAndMethodName = names[0] + "#" + names[1];
                    String conditionType = "ifStmt";
                    List<NameExpr> conditionParams = new ArrayList<NameExpr>();
                    
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
                    // TODO: remover duplicatas do conditionParams e terminar paramValue
                    if (condition.isBinaryExpr()) {
                        conditionParams = condition.asBinaryExpr().findAll(NameExpr.class);
                        // paramValue = condition.asBinaryExpr().findAll(NameExpr.class);
                    } else {
                        conditionParams.add(condition.asNameExpr());
                        // paramValue.add(condition.asNameExpr());
                    }

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

                    // String classAndMethodName, String conditionType, String condition, String[] conditionParams, Object[] paramValue, boolean finalValue
                    MethodCallExpr testExpr = new MethodCallExpr(
                        "LogFile.write",
                        new StringLiteralExpr(classAndMethodName),
                        new StringLiteralExpr(conditionType),
                        new StringLiteralExpr(condition.toString())
                        //new StringLiteralExpr(conditionParams.toString())  // ex: "[a, b]"
                        );
                    // testExpr.addArgument(paramValue); // Object[] paramValue
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
                    });
                    
                    if (tokenAsString.size() > 0) {
                        System.out.println(tokenAsString);
                        System.out.println(tokenAsString.toString());
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
                    statements.addBefore(exprStmt, addBeforeThisStmt); 
                }

                // Copy the statements in the then-block next to the if statement.
                // thenBlock.getStatements().forEach(thenStmt ->
                //         // Use addBefore to get them in the right order (try addAfter to see why)
                //         // Clone the statement we're copying to avoid touching the existing AST.
                //         statements.addBefore(thenStmt.clone(), ifStmt));
            });
    }
}
