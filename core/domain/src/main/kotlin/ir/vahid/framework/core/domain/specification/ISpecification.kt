package ir.vahid.framework.core.domain.specification

interface ISpecification<T> {
    fun isSatisfiedBy(candidate: T): Boolean
}
