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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AstFindAllConditionalModifierAllClasses {
    public static Statement addBeforeThisStmt;
    public static void main(String[] args) throws IOException {
        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        // Class<?> searchClass = Class.forName("com.mycompany.app.CalendarLogic");
        Path projectRoot = Paths.get(args[0]).toAbsolutePath();
        System.out.println(projectRoot);
        String projectRootString = projectRoot.toString();
        File searchDir = new File(projectRootString);
        List<String> filesToParse = new ArrayList<>();
        // listOfFiles(searchDir, filesToParse);
        ListFilesToParse.listFiles(searchDir, filesToParse);
        // FileWriter myWriter = new FileWriter("target/logFile.out");
        System.out.println(filesToParse);
        for (String fileToParse : filesToParse) {
            String file = fileToParse.replace(projectRootString + "/", "");
            System.out.println(file);
            SourceRoot sourceRoot = new SourceRoot(projectRoot);
            // Our sample is in the root of this directory, so no package name.
            CompilationUnit cu = sourceRoot.parse("", file);

            cu.addImport(new ImportDeclaration("teste.LogFile", false, true));

            cu.accept(new ModifierVisitor<String[]>() {
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
                    md.addThrownException(IOException.class);

                    // Process all if's:
                    md.findAll(IfStmt.class).forEach(ifStmt -> {
                        findAndAddLog(ifStmt, this.names);
                    });
                    // "we make a call to super to ensure child nodes of the current node are also visited"
                    return super.visit(md, this.names);
                }
            }, null); 
            // This saves back the file we read with the changes we made. Easy!
            sourceRoot.saveAll();
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

    public static void findAndAddLog(IfStmt ifStmt, String[] names) {
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
                                String classAndMethodName = names[0] + "#" + names[1] + "#";
                                String conditionType = "ifStmt";
                                List<NameExpr> conditionParams = new ArrayList<NameExpr>();
                                
                                // ArrayList<Object> paramValue;
                                // TODO: remover duplicatas do conditionParams e terminar paramValue
                                if (condition.isBinaryExpr()) {
                                    conditionParams = condition.asBinaryExpr().findAll(NameExpr.class);
                                    // paramValue = condition.asBinaryExpr().findAll(NameExpr.class);
                                } else {
                                    conditionParams.add(condition.asNameExpr());
                                    // paramValue.add(condition.asNameExpr());
                                }
                                // String classAndMethodName, String conditionType, String condition, String[] conditionParams, Object[] paramValue, boolean finalValue
                                MethodCallExpr testExpr = new MethodCallExpr(
                                    "LogFile.write",
                                    new StringLiteralExpr(classAndMethodName),
                                    new StringLiteralExpr(conditionType),
                                    new StringLiteralExpr(condition.toString())
                                    // new StringLiteralExpr(conditionParams.toString())
                                    );
                                // testExpr.addArgument(paramValue); // Object[] paramValue
                                testExpr.addArgument(condition); // boolean finalValue
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
