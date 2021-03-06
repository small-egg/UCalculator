package my_calculator.model;

import my_calculator.model.translator.Translator;
import my_calculator.model.translator.expressions.Expression;
import my_calculator.model.translator.expressions.Function;
import my_calculator.model.translator.expressions.Operand;
import my_calculator.model.translator.expressions.Operation;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Вычисляет значение входного выражения
 */
public class Calculator
{
    private String infixExpression;
    private ArrayList<Expression> postfixExpression = new ArrayList<>();
    private Stack<Expression> operands = new Stack<>();
    private double result = 0.0;

    public Calculator()
    {
        infixExpression = "";
    }

    public Calculator(String expression)
    {
        infixExpression = expression.toLowerCase();
    }

    /**
     * Возвращает исходное выражение в
     * инфиксной форме записи
     * @return выражение
     */
    public String getInfixExpression()
    {
        return infixExpression;
    }

    /**
     * Устанавливает исходное выражение
     * инфиксной форме записи
     * @param infixExpression устанавливаемое выражение
     */
    public void setInfixExpression(String infixExpression)
    {
        this.infixExpression = infixExpression.toLowerCase();
    }

    /**
     * Возвращает исходное выражение в постфиксной форме записи
     * @return исходное выражение
     */
    public String getPostfixExpression()
    {
        return postfixExpression.toString();
    }

    /**
     * Возвращает результат вычислений
     * @return результат
     */
    public Double getResult()
    {
        return result;
    }

    /**
     * Начинает вычисление
     */
    public void calculate()
    {
        if(infixExpression.isEmpty()) return;

        Translator translator = new Translator(infixExpression);
        postfixExpression = translator.translateToPostfixNotation();

        for (Expression token : postfixExpression)
            processToken(token);

        result = operands.peek().getValue(); //Результат вычислений лежит на вершине стека
    }

    /**
     * В зависимости от значения токена,
     * добавляет его на стек или в выходное выражение
     * @param token Обрабатываемый токен
     */
    private void processToken(Expression token)
    {
        Expression.Type tokenType = token.getType();
        if (tokenType == Expression.Type.OPERAND)
        {
            operands.push(token);
        }
        else if (tokenType == Expression.Type.OPERATION)
        {
            executeOperation(token);
        }
        else if (tokenType == Expression.Type.FUNCTION)
        {
            executeFunction(token);
        }
    }

    /**
     * Выполняет операцию и кладет результат на вершину стека
     * @param expr литерал операции или имя функции
     */
    private void executeOperation(Expression expr)
    {
        if (expr.getType() == Expression.Type.OPERATION)
        {
            Operation o = (Operation) expr;
            int parametrCount = o.getParametresCount();

            o.setParametres(getParametres(parametrCount));

            operands.push(new Operand(o.getValue()));

        }
        else
        {
            System.err.println("Входное выражение " + infixExpression + " некорректно");
            System.exit(1);
        }
    }

    /**
     * Вычисляет значение функции и кладет результат
     * на вержину стека
     * @param expr функция
     */
    private void executeFunction(Expression expr)
    {
        if (expr.getType() == Expression.Type.FUNCTION)
        {
            Function o = (Function) expr;
            int parametrCount = o.getParametresCount();

            o.setParametres(getParametres(parametrCount));

            operands.push(new Operand(o.getValue()));
        }
    }

    /**
     * Возвращает массив параметров для функции/операции
     * @param count нужное количество парамтров
     * @return массив вещественных чисел
     */
    private Double[] getParametres(int count)
    {
        ArrayList<Double> parametres = new ArrayList<>();
        for (int i = 0; i < count; ++i)
        {
            if (!operands.empty())
            {
                Double value = operands.pop().getValue();
                parametres.add(value);
            }
        }

        Double[] array = new Double[count];
        parametres.toArray(array);

        return array;
    }

    /**
     * Проверяет наличие данного количества операндов на стеке
     *
     * @param count количество операндов;
     * @return true если содержится нужное количество операндов.
     * В противном случае возвращает false;
     */
    private boolean isContainsAsManyOperands(int count)
    {
        return count > 0 && operands.size() >= count;
    }
}