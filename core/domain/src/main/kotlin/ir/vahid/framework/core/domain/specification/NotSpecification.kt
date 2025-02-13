package ir.vahid.framework.core.domain.specification

class NotSpecification<T> (
    private val target: ISpecification<T>
): ISpecification<T> {
    override fun isSatisfiedBy(candidate: T): Boolean {
        return !target.isSatisfiedBy(candidate)
    }
}
