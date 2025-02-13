package ir.vahid.framework.core.domain.specification

abstract class CompositeSpecification<T> : ISpecification<T> {
    abstract override fun isSatisfiedBy(candidate: T): Boolean
    fun and(
        left: ISpecification<T>,
        right: ISpecification<T>,
    ) = AndSpecification(left, right)

    fun or(
        left: ISpecification<T>,
        right: ISpecification<T>,
    ) = OrSpecification(left, right)

    fun not(
        target: ISpecification<T>,
    ) = NotSpecification(target)
}
