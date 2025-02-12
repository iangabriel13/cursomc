package com.naitech.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.domain.ClientOrder;

@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {

	@Transactional(readOnly = true)
	Page<ClientOrder> findByClient(Client client, Pageable pageable);
}
