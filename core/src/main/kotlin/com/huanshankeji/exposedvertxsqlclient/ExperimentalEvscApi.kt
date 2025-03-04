package com.huanshankeji.exposedvertxsqlclient

import kotlin.annotation.AnnotationTarget.*

@RequiresOptIn("This API is experimental. It may be changed in the future without notice.", RequiresOptIn.Level.WARNING)
@Retention(AnnotationRetention.BINARY)
// The ones commented out are what I think may be used in very few use cases.
@Target(
    CLASS,
    //ANNOTATION_CLASS,
    PROPERTY,
    FIELD,
    VALUE_PARAMETER,
    CONSTRUCTOR,
    FUNCTION,
    PROPERTY_GETTER,
    PROPERTY_SETTER,
    TYPEALIAS
)
annotation class ExperimentalEvscApi