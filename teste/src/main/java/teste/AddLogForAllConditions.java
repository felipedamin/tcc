package teste;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class AddLogForAllConditions {
    public static void main(String[] args) throws IOException {
        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogForEachMethod.class).resolve("src/main/java/teste"));
        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", "Methods.java");

        cu.accept(new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(MethodDeclaration md, Void arg) {
                // Navigate the AST by looking at it in the debugger!
                // TODO: documentar opçoes (nao)-viaveis q encontrei - e outras similares (ex: a de loggar o return)
                
                // Tirar duvida sobre se devemos explicar sobre AST no TCC ou se só mostrar os resultados ja basta
                // RESPOSTA: sim, vai agregar na qualidade do trabalho. Nao precisa entrar em detalhes sobre a AST, 
                //           e nem mostrar um trecho de cod mto grande.
                md.getBody().ifPresent(i -> {
                    BlockStmt clone = i.clone();
                    i.getStatements().forEach(j -> {
                        j.ifIfStmt(ifstmt -> {
                            // if statements
                            Expression condition = ifstmt.getCondition();
                            List<Node> children = new ArrayList<>();
                            
                            condition.getChildNodes().forEach(child -> children.add(child));

                            // else statements
                            Optional<Statement> elseStmt = ifstmt.getElseStmt();
                            if (!elseStmt.isEmpty()) {
                                Statement elseStmtClone = elseStmt.get().clone();
                                
                                elseStmt.get().ifIfStmt(elseIfStmt -> {
                                    Expression elseIfCondition = elseIfStmt.getCondition();
                                    
                                    elseIfCondition.getChildNodes().forEach(child -> {
                                        children.add(child);
                                    });

                                    String methodDetails = "method name: " + md.getName() +", if params: " + elseIfCondition;
                                    MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                                    ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                                    clone.getStatements().addBefore(exprStmt, j);
                                });
                            }

                            children.forEach(child -> {
                                // TODO: navegar recursivamente aqui para extrair todos os tokens
                                // ExtractTokens.main(child);
                            });
                            String methodDetails = "method name: " + md.getName() +", if params: " + condition;
                            MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                            ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                            clone.getStatements().addBefore(exprStmt, j);
                        });
                        j.ifExpressionStmt(expr -> {
                            // searchs for ternary statements
                            // on the AST: a ternary statement is a expression, that declares a variable and... 
                            // ...has a conditional expression inside its declaration
                            expr.getExpression().ifVariableDeclarationExpr(declaration -> {
                                declaration.getVariables().forEach(v -> {
                                    v.getInitializer().ifPresent(variable -> variable.ifConditionalExpr(ternary -> {
                                        Expression condition = ternary.getCondition();
                                        // tokens que serão avaliados:
                                        condition.getChildNodes().forEach(child -> {
                                            // TODO: depois q resolver esse problema la no ifStmt, usar a mesma soluçao aqui
                                            System.out.println(child);
                                        });
                                        String methodDetails = "method name: " + md.getName() +", ternary params: " + condition;
                                        MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                                        ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                                        clone.getStatements().addBefore(exprStmt, j);
                                    }));
                                });
                            });
                        });
                        j.ifSwitchStmt(expr -> {
                            // NodeList<SwitchEntry> entries = expr.getEntries();
                            Expression switchSelector = expr.getSelector();

                            String methodDetails = "method name: " + md.getName() +", switch param: " + switchSelector;
                            MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                            ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                            clone.getStatements().addBefore(exprStmt, j);
                        });
                        // j. while
                        j.ifWhileStmt(expr -> {
                            Expression condition = expr.getCondition();
                            System.out.println(condition);
                            // TODO: extrair token (usar msm função dos outros)

                            MethodCallExpr testExpr = new MethodCallExpr("System.out.println");
                            testExpr.addArgument(condition);
                            ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                            clone.getStatements().addBefore(exprStmt, j);
                        });
                        // j. for
                        j.ifForStmt(expr -> {
                            Optional<Expression> compare = expr.getCompare();
                            
                            // pega os nomes das variaveis inicializadas
                            expr.getInitialization().forEach(initialization -> {
                                initialization.ifVariableDeclarationExpr(variableExpr -> {
                                    variableExpr.getVariables().forEach(v -> {
                                        v.getNameAsString();
                                    });
                                });
                            });

                            System.out.println(compare.get());
                            // TODO: extrair token do compare (usar msm função dos outros)
                            
                            // TODO: nao adicionar a variavel, que foi inicializada no for, no logger
                            // MethodCallExpr testExpr = new MethodCallExpr("System.out.println");
                            // testExpr.addArgument(compare.get().toString());
                            // ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                            // clone.getStatements().addBefore(exprStmt, j);
                        });
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

    public List<Node> extractTokensFromNode(Node node) {
        System.out.println("extractTokensFromNode");
        List<Node> allTokens = new ArrayList<>();
        
        if (node.getChildNodes().size() == 0) {
            System.out.println("caso base");
            System.out.println(node);
            List<Node> nodeList = new ArrayList<>();
            nodeList.add(node);
            return nodeList;
        }
        
        if (node.getChildNodes().size() > 0) {
            System.out.println("chamar recursao e adicionar token à lista");
        }

        return allTokens;
    }
}