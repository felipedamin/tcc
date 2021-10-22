package teste;

// mvn compile exec:java -Dexec.mainClass="com.parser.AddLogForAllConditions2" -Dexec.args="src/main/java/teste"
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
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.ParserCollectionStrategy;
import com.github.javaparser.ParseResult;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.nio.file.Path;
import java.nio.file.Paths;
import teste.LogFile;
import teste.ListFilesToParse;

public class AddLogForAllConditionsAndClasses {

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
            cu.accept(new ModifierVisitor<Void>() {
                private BlockStmt clone;

                @Override
                public Visitable visit(MethodDeclaration md, Void arg) {
                    // Navigate the AST by looking at it in the debugger!
                    // TODO: documentar opçoes (nao)-viaveis q encontrei - e outras similares (ex: a de loggar o return)
                    
                    // Tirar duvida sobre se devemos explicar sobre AST no TCC ou se só mostrar os resultados ja basta
                    // RESPOSTA: sim, vai agregar na qualidade do trabalho. Nao precisa entrar em detalhes sobre a AST,
                    //           e nem mostrar um trecho de cod mto grande.
                    md.getBody().ifPresent(i -> {
                        String methodName = md.getNameAsString();
                        this.clone = i.clone();
                        visitBlock(i, methodName);
                        i.replace(clone);
                    });
                    
                    return super.visit(md, arg);
                }

                public void visitBlock(BlockStmt i, String methodName) {
                    i.getStatements().forEach(j -> {
                        visitStmt(j, methodName);
                    });
                }

                public void visitBlock(BlockStmt i, String methodName, Statement addBeforeThisStmt) {
                    i.getStatements().forEach(j -> {
                        visitStmt(j, methodName, addBeforeThisStmt);
                    });
                }

                public void visitStmt(Statement j, String methodName) {
                    System.out.println("visitStmt");
                    j.ifIfStmt(ifstmt -> {
                        addLogToIfStatement(ifstmt, methodName, ifstmt);
                    });
                    j.ifExpressionStmt(expr -> {
                        // TODO: na vdd poderia primeiro ver se é ternario e só dps chamar a funcao
                        addLogToTernaryExpr(expr, methodName, expr);
                    });
                    j.ifSwitchStmt(stmt -> {
                        addLogToSwitchStatement(stmt, methodName, stmt);
                    });
                    j.ifWhileStmt(stmt -> {
                        addLogToWhileStatement(stmt, methodName, stmt);
                    });
                    j.ifForStmt(stmt -> {
                        addLogToForStatement(stmt, methodName, stmt);
                    });
                    j.ifTryStmt(stmt -> {
                        visitBlock(stmt.getTryBlock(), methodName, stmt);
                    });
                    System.out.println("finished visitStmt");
                }

                public void visitStmt(Statement j, String methodName, Statement addBeforeThisStmt) {
                    System.out.println("visitStmt");
                    j.ifIfStmt(ifstmt -> {
                        addLogToIfStatement(ifstmt, methodName, addBeforeThisStmt);
                    });
                    j.ifExpressionStmt(expr -> {
                        // TODO: na vdd poderia primeiro ver se é ternario e só dps chamar a funcao
                        addLogToTernaryExpr(expr, methodName, addBeforeThisStmt);
                    });
                    j.ifSwitchStmt(stmt -> {
                        addLogToSwitchStatement(stmt, methodName, addBeforeThisStmt);
                    });
                    j.ifWhileStmt(stmt -> {
                        addLogToWhileStatement(stmt, methodName, addBeforeThisStmt);
                    });
                    j.ifForStmt(stmt -> {
                        addLogToForStatement(stmt, methodName, addBeforeThisStmt);
                    });
                    j.ifTryStmt(stmt -> {
                        visitBlock(stmt.getTryBlock(), methodName, addBeforeThisStmt);
                    });
                    System.out.println("finished visitStmt");
                }

                public BlockStmt addLogToIfStatement(IfStmt stmt, String methodName, Statement addBeforeThisStmt) {
                    System.out.println("addLogToIfStatement");
                    System.out.println(stmt);
                    Expression condition = stmt.getCondition();
                    List<Node> children = new ArrayList<>();
            
                    condition.getChildNodes().forEach(child -> children.add(child));
            
                    // else statements
                    Optional<Statement> elseStmt = stmt.getElseStmt();
                    if (!elseStmt.isEmpty()) {
                        // Statement elseStmtClone = elseStmt.get().clone();
    
                        elseStmt.get().ifBlockStmt(elseBlock -> {
                            visitBlock(elseBlock, methodName, addBeforeThisStmt);
                        });
    
                        elseStmt.get().ifIfStmt(elseIfStmt -> {
                            System.out.println("é pra ter proximo elif:");
                            System.out.println(elseIfStmt);
                            addLogToIfStatement(elseIfStmt, methodName, addBeforeThisStmt);
                        });
                    }
    
                    Statement thenStmt = stmt.getThenStmt();
                    thenStmt.ifBlockStmt( thenBlock -> {
                        visitBlock(thenBlock, methodName, addBeforeThisStmt);
                    });
            
                    children.forEach(child -> {
                        // TODO: navegar recursivamente aqui para extrair todos os tokens
                        // ExtractTokens.main(child);
                    });
                    String methodDetails = "method name: " + methodName +", if params: " + condition;
                    MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                    ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                    
                    System.out.println(exprStmt);
                    clone.getStatements().addBefore(exprStmt, addBeforeThisStmt);
                    return clone;
                }

                public BlockStmt addLogToSwitchStatement(SwitchStmt stmt, String methodName, Statement addBeforeThisStmt) {
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
                    clone.getStatements().addBefore(exprStmt, addBeforeThisStmt);
                    return clone;
                }

                public BlockStmt addLogToForStatement(ForStmt stmt, String methodName, Statement addBeforeThisStmt) {
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
                    // clone.getStatements().addBefore(exprStmt, addBeforeThisStmt);
                    return clone;
                }

                public BlockStmt addLogToWhileStatement(WhileStmt stmt, String methodName, Statement addBeforeThisStmt) {
                    System.out.println("addLogToWhileStatement");
                    Expression condition = stmt.getCondition();
                    System.out.println(condition);
                    // TODO: extrair token (usar msm função dos outros)
            
                    MethodCallExpr testExpr = new MethodCallExpr("System.out.println");
                    testExpr.addArgument(condition);
                    ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                    clone.getStatements().addBefore(exprStmt, addBeforeThisStmt);
                    return clone;
                }

                public BlockStmt addLogToTernaryExpr(ExpressionStmt expr, String methodName, Statement addBeforeThisStmt) {
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
                                clone.getStatements().addBefore(exprStmt, addBeforeThisStmt);
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
            }, null);
            // Path savePath = Paths.get("target/parsed-classes");
            // This saves back the file we read with the changes we made. Easy!
            sourceRoot.saveAll();
        }
    }
}
