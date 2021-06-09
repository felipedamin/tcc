package teste;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;

// transforma:
// if (a != b) {x} else {y}
// em
// if (a == b) {y} else {x}

public class invertingIFs {
    public static void main(String[] args) throws IOException {
        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(invertingIFs.class).resolve("src/main/java/teste"));
        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", "Blabla.java");

        cu.accept(new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(IfStmt n, Void arg) {
                System.out.println(n);
                // I figured out what to get and what to cast simply by looking at the AST in the debugger! 
                Expression condExpr = n.getCondition();
                
                System.out.println(condExpr);
                if (condExpr instanceof BinaryExpr) {
                    BinaryExpr cond = (BinaryExpr) condExpr;
                    BinaryExpr.Operator operator = cond.getOperator();
                    System.out.println(operator);
                    if (cond.getOperator() == BinaryExpr.Operator.NOT_EQUALS && n.getElseStmt().isPresent()) {
                        /* It's a good idea to clone nodes that you move around.
                            JavaParser (or you) might get confused about who their parent is!
                        */
                        Statement thenStmt = n.getThenStmt().clone();
                        Statement elseStmt = n.getElseStmt().get().clone();
                        n.setThenStmt(elseStmt);
                        n.setElseStmt(thenStmt);
                        cond.setOperator(BinaryExpr.Operator.EQUALS);
                    }
                }
                return super.visit(n, arg);
            }
        }, null);

        // This saves back the file we read with the changes we made. Easy!
        sourceRoot.saveAll();
    }
}