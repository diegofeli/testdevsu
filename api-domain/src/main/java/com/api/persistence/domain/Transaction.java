package com.api.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends AbstractAuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Column(name = "total_amount", nullable = false)
	private BigDecimal totalAmount;

	@Column(name = "description")
	private String description;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_type_id", nullable = false)
	private TransactionType transactionType;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@NotNull
	@Column(name = "enabled", nullable = false)
	private Boolean enabled = true;
}
