package my_calculator.translator.expressions;

/**
 * Математическое выражение
 */
public interface Expression
{
    enum TypeOfExpression {NONE, FUNCTION, OPERATION, OPERAND, CONSTANT, OPEN_BRACKET, CLOSE_BRACKET}

    /**
     * Возвращает тип данного математического выражения
     * @return тип выражения
     */
    TypeOfExpression getType();

    /**
     * Вычисляет значение математического выражения
     * @return результат вычисления
     */
    Double  getValue();


}