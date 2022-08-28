package cn.bobdeng.rbac.security;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@Slf4j
public class AspectUtils {
    private AspectUtils() {
    }

    public static Object getExpressValue(JoinPoint joinPoint, String express) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        String[] names = signature.getParameterNames();
        for (int i = 0; i < names.length; i++) {
            context.setVariable(names[i], joinPoint.getArgs()[i]);
        }
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(express).getValue(context);
    }
}
