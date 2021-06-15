package teste;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.util.Optional;

// transforma:
// if (a != b) {x} else {y}
// em
// if (a == b) {y} else {x}

public class LogForEachMethod {
    public static void main(String[] args) throws IOException {
        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogForEachMethod.class).resolve("src/main/java/teste"));
        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", "Methods.java");

        cu.accept(new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(MethodDeclaration n, Void arg) {
                // System.out.println(n);
                // I figured out what to get and what to cast simply by looking at the AST in the debugger! 
                // System.out.println(n.getName());
                // System.out.println(n.getParameters());

                String methodDetails = "method name: " + n.getName() +"; method params: " + n.getParameters();
                String logCall = "System.out.println(\"" + methodDetails + "\");";
                // System.out.println(logCall);

                MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));

                // MethodCallExpr logCallExpr = new MethodCallExpr(logCall);
                // System.out.println(logCallExpr);
                ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                System.out.println(exprStmt);
                n.getBody().ifPresent(i -> 
                    i.ifBlockStmt(block -> 
                        block.getStatements().add(0, exprStmt)
                    )
                );
                
                // MethodCallExpr logCallExpr = new MethodCallExpr(System.out.println(), methodDetails);
                // ExpressionStmt ExprStmt = new ExpressionStmt(logCallExpr);
                
                // Node teste = StaticJavaParser.parse(logCall);
                // System.out.println(n.getBody());
                // Node logNode = StaticJavaParser.parseExpression(logCall);


                // if (condExpr instanceof BinaryExpr) {
                //     BinaryExpr cond = (BinaryExpr) condExpr;
                //     BinaryExpr.Operator operator = cond.getOperator();
                //     System.out.println(operator);
                //     if (cond.getOperator() == BinaryExpr.Operator.NOT_EQUALS && n.getElseStmt().isPresent()) {
                //         /* It's a good idea to clone nodes that you move around.
                //             JavaParser (or you) might get confused about who their parent is!
                //         */
                //         Statement thenStmt = n.getThenStmt().clone();
                //         Statement elseStmt = n.getElseStmt().get().clone();
                //         n.setThenStmt(elseStmt);
                //         n.setElseStmt(thenStmt);
                //         cond.setOperator(BinaryExpr.Operator.EQUALS);
                //     }
                // }
                return super.visit(n, arg);
            }
        }, null);

        // This saves back the file we read with the changes we made. Easy!
        sourceRoot.saveAll();
    }
}