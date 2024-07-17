package domain.model

sealed class ResultWrapper<out SuccessResultType, out CancelOrFailureResultType : Throwable> {

    data class Success<out SuccessResultType>(val resultValue: SuccessResultType): ResultWrapper<SuccessResultType, Nothing>()

    data class CancelOrFailure<out CancelOrFailureResultType : Throwable>(val errorMessage: String, val resultValue: CancelOrFailureResultType): ResultWrapper<Nothing, CancelOrFailureResultType>()

}