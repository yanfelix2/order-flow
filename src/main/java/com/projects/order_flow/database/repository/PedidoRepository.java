package com.projects.order_flow.database.repository;

import com.projects.order_flow.database.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
