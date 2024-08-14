package org.acme

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class FruitRepository : PanacheRepositoryBase<Fruit, UUID>