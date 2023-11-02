package com.baseblocks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

public class ColumnAnnotationChecker extends AbstractCheck {

    private static final String MESSAGE = "Fields annotated with @Column must have a columnDefinition attribute.";
    public static final String COLUMN_ANNOTATION = "Column";
    public static final String COLUMN_DEFINITION_ATTR = "columnDefinition";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.VARIABLE_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[]{TokenTypes.VARIABLE_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST parent = ast.getParent();

        if (isClassMember(parent)) {
            DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);

            List<DetailAST> annotations = getAnnotations(modifiers);
            annotations.stream()
                    .filter(this::isColumnAnnotation)
                    .filter(annotation -> !hasColumnDefinition(annotation))
                    .forEach(annotation -> log(ast.getLineNo(), MESSAGE));
        }
    }

    private boolean isClassMember(DetailAST parent) {
        return parent != null && parent.getType() == TokenTypes.OBJBLOCK;
    }

    private List<DetailAST> getAnnotations(DetailAST modifiers) {
        List<DetailAST> annotations = new ArrayList<>();

        for (DetailAST child = modifiers.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.ANNOTATION) {
                annotations.add(child);
            }
        }

        return annotations;
    }

    private boolean isColumnAnnotation(DetailAST annotation) {
        DetailAST annotationIdent = annotation.findFirstToken(TokenTypes.IDENT);
        return annotationIdent != null && COLUMN_ANNOTATION.equals(annotationIdent.getText());
    }

    private boolean hasColumnDefinition(DetailAST annotation) {
        for (DetailAST annotationChild = annotation.getFirstChild(); annotationChild != null; annotationChild = annotationChild.getNextSibling()) {
            if (annotationChild.getType() == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR) {
                DetailAST attributeName = annotationChild.findFirstToken(TokenTypes.IDENT);
                if (attributeName != null && COLUMN_DEFINITION_ATTR.equals(attributeName.getText())) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public int[] getRequiredTokens() {
        return new int[]{TokenTypes.VARIABLE_DEF};
    }
}