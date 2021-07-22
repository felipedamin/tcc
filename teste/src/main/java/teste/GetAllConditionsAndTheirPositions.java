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

public class GetAllConditionsAndTheirPositions {
    public static Pair<ArrayList<ExpressionStmt>, ArrayList<Integer>> main(String[] args) throws IOException {
        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogForEachMethod.class).resolve("src/main/java/teste"));
        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", "Methods.java");

        ArrayList<ExpressionStmt> expressionsArray = new ArrayList<ExpressionStmt>();
        ArrayList<Integer> positionArray = new ArrayList<Integer>();
        cu.accept(new ModifierVisitor<Void>() {
            public String methodDetails = "";
            @Override
            public Visitable visit(MethodDeclaration md, Void arg) {
                // Navigate the AST by looking at it in the debugger!

                NodeList<Parameter> params = md.getParameters();
                this.methodDetails = "method name: " + md.getName();
                params.forEach(param -> {
                    // System.out.println((param));
                    // System.out.println((param.getNameAsExpression()));
                    // System.out.println((param.getName()));
                    this.methodDetails = methodDetails + ", method params: " + param.getNameAsString();
                });

                MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(this.methodDetails));
                // can I use this when trying to pass extra arguments??
                //testExpr.addArgument(new StringLiteralExpr("Hello World!"));
                ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                System.out.println(exprStmt);
                md.getBody().ifPresent(i -> 
                    i.getStatements().forEach(j -> {
                        j.ifIfStmt(ifstmt -> {
                            // if statements
                            System.out.println(ifstmt.getCondition());
                            Optional<Position> begin = ifstmt.getBegin();
                            System.out.println(begin.get().line);
                            expressionsArray.add(exprStmt);
                            positionArray.add(begin.get().line);
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
                    })
                );
                
                return super.visit(md, arg);
            }
        }, null);
        
        // System.out.println(expressionsArray);
        // System.out.println(positionArray);
        return new Pair<ArrayList<ExpressionStmt>, ArrayList<Integer>>(expressionsArray, positionArray);
    }
}