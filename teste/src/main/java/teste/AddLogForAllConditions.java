package teste;

import com.github.javaparser.Position;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.Pair;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.util.Optional;
import java.util.ArrayList;

public class AddLogForAllConditions {
    public static void main(String[] args) throws IOException {
        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogForEachMethod.class).resolve("src/main/java/teste"));
        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", "Methods.java");

        // Pair<ArrayList<ExpressionStmt>, ArrayList<Integer>> pair = GetAllConditionsAndTheirPositions.main(args);
        // System.out.println(pair.a);
        // System.out.println(pair.b);

        cu.accept(new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(MethodDeclaration md, Void arg) {
                // Navigate the AST by looking at it in the debugger!

                // String methodDetails = "method name: " + md.getName() +", method params: " + md.getParameters();
                // MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                // ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                
                // for item in list:
                //      get position (+ index?? para corrigir o fato de que agora o numero da linha será diferente)
                //      add expr in that position
                
                // md.getBody().ifPresent(i -> 
                //     i.ifBlockStmt(block ->{ 
                //         block.getStatements().add(0, exprStmt);
                //         block.addStatement(index, expr);
                //         block.getStatements().addBefore(node, beforeThisNode)
                //     })
                // );

                md.getBody().ifPresent(i -> {
                    BlockStmt clone = i.clone();
                    i.getStatements().forEach(j -> {
                        j.ifIfStmt(ifstmt -> {
                            // if statements

                            Expression condition = ifstmt.getCondition();
                            System.out.println(condition);

                            String methodDetails = "method name: " + md.getName() +", condition params: " + condition;
                            MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                            ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                            clone.getStatements().addBefore(exprStmt, j);
                            
                            System.out.println(i);
                            System.out.println(clone);
                        });
                        j.ifExpressionStmt(expr -> {
                            // searchs for ternary statements
                            // on the AST: a ternary statement is a expression, that declares a variable and... 
                            // ...has a conditional expression inside its declaration
                            expr.getExpression().ifVariableDeclarationExpr(declaration -> {
                                System.out.println(declaration);
                                declaration.getVariables().forEach(v -> {
                                    v.getInitializer().ifPresent(variable -> variable.ifConditionalExpr(ternary -> {
                                        System.out.println(ternary.getCondition());
                                    }));
                                });
                            });
                        });
                        // TODO:
                        // j. switch
                        // j. while
                        // j. for
                    });
                    i.replace(clone);
                });

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
                
                return super.visit(md, arg);
            }
        }, null);
        
        // This saves back the file we read with the changes we made. Easy!
        sourceRoot.saveAll();
    }
}