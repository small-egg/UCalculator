package my_calculator.translator.expressions;

/**
 * Класс, описывающий функцию математического выражения
 */
public class Function implements Computational
{
    enum FunctionType {NONE, SIN, COS, TAN, COTAN, POW}

    private FunctionType    type = FunctionType.NONE;
    private double[]        parametres = null;

    Function(String literal)
    {
        type = ExpressionsSet.getFunctionTypeByLiteral(literal);
    }

    @Override
    public void setParametres(double[] parametres)
    {
        if( isCorrectParametresCount(parametres.length) )
        {
            this.parametres = parametres;
        }
    }

    @Override
    public TypeOfExpression getType()
    {
        return TypeOfExpression.FUNCTION;
    }

    @Override
    public Double getValue()
    {
        if( parametres != null )
            return calculateResult();
        else
        {
            System.err.println("Параметры для операции не были установлены до расчета");
            return 0.0;
        }
    }

    /**
     * Проверяет корректное ли количество параметров count
     * передано для данной операции
     * @return true, если количество параметров коректно.
     * В противном случае возвращает false
     */
    private boolean isCorrectParametresCount(int count)
    {
        if( type == FunctionType.SIN ||
                type == FunctionType.COS ||
                type == FunctionType.TAN ||
                type == FunctionType.COTAN )
        {
            return count == 1; // 1 параметр
        }
        else if(type == FunctionType.POW)
        {
            return count == 2;
        }

        return false;
    }

    /**
     * Рассчитывает результат выражение
     * @return результат
     */
    private double calculateResult()
    {
        switch (type)
        {
            case SIN:
                return StrictMath.sin( parametres[0] );
            case COS:
                return StrictMath.cos( parametres[0] );
            case TAN:
                return StrictMath.tan( parametres[0] );
            case COTAN:
                return 1 / StrictMath.tan( parametres[0] );
            case POW:
                return StrictMath.pow( parametres[1], parametres[0] );
        }

        return 0.0;
    }

}