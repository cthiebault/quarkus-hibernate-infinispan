package org.acme

import jakarta.persistence.Cacheable
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class Fruit(

  @Id
  @JdbcTypeCode(SqlTypes.VARCHAR)
  val id: UUID,

  var name: String,

  )