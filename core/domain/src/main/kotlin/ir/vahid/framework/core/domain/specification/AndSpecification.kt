package ir.vahid.framework.core.domain.specification

class AndSpecification<T>(
    private val left: ISpecification<T>,
    private val right: ISpecification<T>,
) : ISpecification<T> {
    override fun isSatisfiedBy(candidate: T): Boolean {
        return left.isSatisfiedBy(candidate) && right.isSatisfiedBy(candidate)
    }
}
