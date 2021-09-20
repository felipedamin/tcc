package teste;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
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
                    // BlockStmt clone = i.clone();
                    String methodName = md.getNameAsString();
                    visitBlock(i, methodName);
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

    public static void visitBlock(BlockStmt i, String methodName) {
        BlockStmt clone = i.clone();
        i.getStatements().forEach(j -> {
            visitStmt(j, methodName, clone);
        });
        i.replace(clone);
    }

    public static void visitStmt(Statement j, String methodName, BlockStmt clone) {
        System.out.println("visitStmt");
        j.ifIfStmt(ifstmt -> {
            addLogToIfStatement(ifstmt, methodName, clone);
        });
        j.ifExpressionStmt(expr -> {
            // TODO: na vdd poderia primeiro ver se é ternario e só dps chamar a funcao
            addLogToTernaryExpr(expr, methodName, clone);
        });
        j.ifSwitchStmt(stmt -> {
            addLogToSwitchStatement(stmt, methodName, clone);
        });
        j.ifWhileStmt(stmt -> {
            addLogToWhileStatement(stmt, methodName, clone);
        });
        j.ifForStmt(stmt -> {
            addLogToForStatement(stmt, methodName, clone);
        });
        System.out.println("finished visitStmt");
    }

    // eles devem ser static???
    public static BlockStmt addLogToIfStatement(IfStmt stmt, String methodName, BlockStmt clone) {
        System.out.println("addLogToIfStatement");
        Expression condition = stmt.getCondition();
        List<Node> children = new ArrayList<>();

        condition.getChildNodes().forEach(child -> children.add(child));

        // else statements
        Optional<Statement> elseStmt = stmt.getElseStmt();
        if (!elseStmt.isEmpty()) {
            Statement elseStmtClone = elseStmt.get().clone();
            
            // elseStmt.get().ifBlockStmt(elseBlock -> {
            //     elseBlock.getStatements().forEach(nestedStmt -> {
            //         nestedStmt.ifIfStmt(nestedIfStmt -> {
            //             Expression elseIfCondition = nestedIfStmt.getCondition();
                    
            //             elseIfCondition.getChildNodes().forEach(child -> {
            //                 children.add(child);
            //             });

            //             String methodDetails = "method name: " + methodName +", if params: " + elseIfCondition;
            //             MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
            //             ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
            //             clone.getStatements().addBefore(exprStmt, stmt);
            //         });
            //     });
            // });
            elseStmt.get().ifBlockStmt(elseBlock -> {
                visitBlock(elseBlock, methodName);
            });

            elseStmt.get().ifIfStmt(elseIfStmt -> {
                Expression elseIfCondition = elseIfStmt.getCondition();
                
                elseIfCondition.getChildNodes().forEach(child -> {
                    children.add(child);
                });

                String methodDetails = "method name: " + methodName +", if params: " + elseIfCondition;
                MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                clone.getStatements().addBefore(exprStmt, stmt);
            });
        }

        children.forEach(child -> {
            // TODO: navegar recursivamente aqui para extrair todos os tokens
            // ExtractTokens.main(child);
        });
        String methodDetails = "method name: " + methodName +", if params: " + condition;
        MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
        ExpressionStmt exprStmt = new ExpressionStmt(testExpr);

        System.out.println(stmt);
        clone.getStatements().addBefore(exprStmt, stmt);
        return clone;
    }

    public static BlockStmt addLogToSwitchStatement(SwitchStmt stmt, String methodName, BlockStmt clone) {
        System.out.println("addLogToSwitchStatement");
        // NodeList<SwitchEntry> entries = expr.getEntries();
        Expression switchSelector = stmt.getSelector();

        // stmt.getEntries().forEach(i -> {
        //     //visitBlock(i, methodName);
        //     i.getStatements().forEach(j -> {
        //         visitStmt(j, methodName, clone);
        //     });
        // });

        String methodDetails = "method name: " + methodName +", switch param: " + switchSelector;
        MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
        ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
        clone.getStatements().addBefore(exprStmt, stmt);
        return clone;
    }

    public static BlockStmt addLogToForStatement(ForStmt stmt, String methodName, BlockStmt clone) {
        System.out.println("addLogToForStatement");
        Optional<Expression> compare = stmt.getCompare();
        // pega os nomes das variaveis inicializadas
        stmt.getInitialization().forEach(initialization -> {
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
        return clone;
    }

    public static BlockStmt addLogToWhileStatement(WhileStmt stmt, String methodName, BlockStmt clone) {
        System.out.println("addLogToWhileStatement");
        Expression condition = stmt.getCondition();
        System.out.println(condition);
        // TODO: extrair token (usar msm função dos outros)

        MethodCallExpr testExpr = new MethodCallExpr("System.out.println");
        testExpr.addArgument(condition);
        ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
        clone.getStatements().addBefore(exprStmt, stmt);
        return clone;
    }

    public static BlockStmt addLogToTernaryExpr(ExpressionStmt expr, String methodName, BlockStmt clone) {
        System.out.println("addLogToTernaryExpr");
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
                    String methodDetails = "method name: " + methodName +", ternary params: " + condition;
                    MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                    ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                    clone.getStatements().addBefore(exprStmt, expr);
                }));
            });
        });
        return clone;
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